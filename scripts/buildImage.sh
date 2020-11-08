#!/bin/sh
docker tag chatapp_chatapp ngovanhuy0241/chatapp_chatapp
docker tag chatapp_contact ngovanhuy0241/chatapp_contact
# docker tag chatapp_eureka ngovanhuy0241/chatapp_eureka
docker tag chatapp_storage ngovanhuy0241/chatapp_storage
docker tag chatapp_message ngovanhuy0241/chatapp_message
docker tag chatapp_mysql ngovanhuy0241/chatapp_mysql
docker tag chatapp_cassandra ngovanhuy0241/chatapp_cassandra
docker tag chatapp_rabbitmq ngovanhuy0241/chatapp_rabbitmq
docker tag chatapp_security ngovanhuy0241/chatapp_security
# docker tag chatapp_turbine ngovanhuy0241/chatapp_turbine
docker tag chatapp_zuul ngovanhuy0241/chatapp_zuul

docker push ngovanhuy0241/chatapp_chatapp
docker push ngovanhuy0241/chatapp_contact
# docker push ngovanhuy0241/chatapp_eureka
docker push ngovanhuy0241/chatapp_storage
docker push ngovanhuy0241/chatapp_message
docker push ngovanhuy0241/chatapp_mysql
docker push ngovanhuy0241/chatapp_cassandra
docker push ngovanhuy0241/chatapp_rabbitmq
docker push ngovanhuy0241/chatapp_security
# docker push ngovanhuy0241/chatapp_turbine
docker push ngovanhuy0241/chatapp_zuul