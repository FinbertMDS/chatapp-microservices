#!/bin/bash
docker tag chatapp-chatapp ngovanhuy0241/chatapp-chatapp
docker tag chatapp-contact ngovanhuy0241/chatapp-contact
docker tag chatapp-eureka-server ngovanhuy0241/chatapp-eureka-server
docker tag chatapp-storage ngovanhuy0241/chatapp-storage
docker tag chatapp-message ngovanhuy0241/chatapp-message
docker tag chatapp-security ngovanhuy0241/chatapp-security
docker tag chatapp-api-gateway ngovanhuy0241/chatapp-api-gateway
docker tag chatapp-config-server ngovanhuy0241/chatapp-config-server

docker push ngovanhuy0241/chatapp-chatapp
docker push ngovanhuy0241/chatapp-contact
docker push ngovanhuy0241/chatapp-eureka-server
docker push ngovanhuy0241/chatapp-storage
docker push ngovanhuy0241/chatapp-message
docker push ngovanhuy0241/chatapp-security
docker push ngovanhuy0241/chatapp-api-gateway
docker push ngovanhuy0241/chatapp-config-server