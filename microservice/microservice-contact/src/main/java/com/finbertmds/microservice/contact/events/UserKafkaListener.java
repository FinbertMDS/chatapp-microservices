package com.finbertmds.microservice.contact.events;

import java.util.Date;

import com.finbertmds.microservice.contact.models.Contact;
import com.finbertmds.microservice.contact.models.ContactUser;
import com.finbertmds.microservice.contact.services.ContactService;
import com.finbertmds.microservice.contact.services.ContactUserService;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserKafkaListener {

	private final Logger log = LoggerFactory.getLogger(UserKafkaListener.class);

	@Autowired
	private ContactUserService userService;

	@Autowired
	private ContactService contactService;

	@KafkaListener(topics = "user", containerFactory = "userKafkaListenerContainerFactory")
	public void order(ContactUser user/* , Acknowledgment acknowledgment */) {
		log.info("Received user " + user.getId());
		user.setCreatedAt(new Date());
		user.setUpdatedAt(new Date());
		if (StringUtils.isEmpty(user.getFirstName())) {
			user.setFirstName(user.getUsername());
		}
		ContactUser createdUser = userService.save(user);

		contactService.save(new Contact(createdUser));
		// acknowledgment.acknowledge();
	}

}
