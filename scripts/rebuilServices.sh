#!/bin/sh
services=("file-management" "file-storage" "group" "message" "security" "user" "eureka-server" "turbine-server" "zuul-server")

main() {
  for ARG in $@; do
    buildService "$ARG"
  done
  buildDocker
}

containsElement() {
  local e match="$1"
  shift
  for e; do [[ "$e" == "$match" ]] && return 0; done
  return 1
}

buildService() {
  cd ../microservice
  if [ -z "$1" ]; then
    echo "Please choice service to rebuild."
    return
  fi
  containsElement "$1" "${services[@]}"
  if [ "$?" == "1" ]; then
    echo "Not found service."
    return
  fi
  echo "Rebuilding service $1"
  local serviceName="microservice-$1"
  ./mvnw -am clean package -Dmaven.test.skip=true -pl $serviceName 
}

buildDocker() {
  cd ../docker
  docker-compose build
  docker-compose up -d
}

main $@
