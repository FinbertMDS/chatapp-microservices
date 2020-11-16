package com.finbertmds.microservice.message.configuration;

import java.util.List;

import com.finbertmds.microservice.message.security.jwt.JwtUtils;
import com.finbertmds.microservice.message.security.services.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.session.Session;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableScheduling
@EnableWebSocketMessageBroker
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class WebSocketConfigurationSpringSession extends AbstractSessionWebSocketMessageBrokerConfigurer<Session> {

	@Value("${chatapp.relay.host}")
	private String relayHost;

	@Value("${chatapp.relay.port}")
	private Integer relayPort;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	protected void configureStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
	}

	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableStompBrokerRelay("/queue/", "/topic/").setUserDestinationBroadcast("/topic/unresolved.user.dest")
				.setUserRegistryBroadcast("/topic/registry.broadcast").setRelayHost(relayHost).setRelayPort(relayPort);

		registry.setApplicationDestinationPrefixes("/chatroom");
	}

	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(new ChannelInterceptor() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
				if (StompCommand.CONNECT.equals(accessor.getCommand())) {
					List<String> tokenList = accessor.getNativeHeader("Authorization");
					String token = null;
					if (tokenList == null || tokenList.size() < 1) {
						return message;
					} else {
						token = tokenList.get(0);
						if (token == null) {
							return message;
						}
					}
					String jwt = parseJwt(token);
					if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
						String username = jwtUtils.getUserNameFromJwtToken(jwt);

						UserDetails userDetails = userDetailsService.loadUserByUsername(username);
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
								null, userDetails.getAuthorities());
						accessor.setUser(authentication);
					}
				}
				return message;
			}
		});
	}

	private String parseJwt(String headerAuth) {
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}
}
