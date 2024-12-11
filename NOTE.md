- Change config at service:
  1. Push change to github: config-server will auto change. You can view at url: http://localhost:8080/config/storage/default

  2. To refresh config of service, run this command at every service:

    ```
    curl -H "Content-Type: application/json" -d {} http://localhost:8080/{service-name}/actuator/refresh
    ```

- Fix spring cloud gateway CORS
  1. Add global cors in application.yml
https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway/cors-configuration.html
  
  2. Add config DedupeResponseHeader in application.yml
https://docs.spring.io/spring-cloud-gateway/reference/spring-cloud-gateway/gatewayfilter-factories/deduperesponseheader-factory.html

  3. Update @Configuration and change setAllowedOrigins to setAllowedOriginPatterns in Java
https://stackoverflow.com/a/77257605
https://github.com/spring-cloud/spring-cloud-gateway/issues/2946#issuecomment-2296630938

 4. Fix spring cloud gateway CORS websocket
https://stackoverflow.com/a/73776455
https://www.springcloud.io/post/2022-03/load-balanced-websockets-with-spring-cloud-gateway/#gsc.tab=0