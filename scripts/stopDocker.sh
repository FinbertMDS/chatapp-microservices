#!/bin/sh

read -p "Do you wish to stop or remove services? s/r: " yn
case $yn in
  [Ss]* ) cd ../docker; docker-compose stop;;
  [Rr]* ) cd ../docker; docker-compose down;;
  * ) echo "End.";;
esac