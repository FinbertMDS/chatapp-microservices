- Change config at service:
  1. Push change to github: config-server will auto change. You can view at url: http://localhost:8080/config/storage/default

  2. To refresh config of service, run this command at every service:

    ```
    curl -H "Content-Type: application/json" -d {} http://localhost:8080/{service-name}/actuator/refresh
    ```