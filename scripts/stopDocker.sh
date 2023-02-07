#!/bin/bash

read -p "Do you wish to stop or remove services? s/r: " yn
case $yn in
  [Ss]* ) cd ../docker; docker-compose -f docker-compose-base.yml -f docker-compose-message.yml stop;;
  [Rr]* ) cd ../docker; docker-compose -f docker-compose-base.yml -f docker-compose-message.yml down;;
  * ) echo "End.";;
esac