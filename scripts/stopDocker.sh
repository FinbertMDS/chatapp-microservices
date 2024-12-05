#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

read -p "Do you wish to stop or remove services? s/r: " yn
case $yn in
  [Ss]* ) cd $DIR/../docker; docker-compose -f docker-compose-base.yml stop;;
  [Rr]* ) cd $DIR/../docker; docker-compose -f docker-compose-base.yml down;;
  * ) echo "End.";;
esac