#!/bin/sh
services=("file-management" "file-storage" "group" "message" "security" "user" "eureka-server" "turbine-server" "zuul-server")

declare -a servicesWillBeRebuild

main() {
  printListService
  getInputFromUser
  buildService
  if [[ "$?" -ne 0 ]] ; then
    echo 'Build service has error. Please check your code.'; exit $rc
  fi
  buildDocker
}

printListService() {
  echo "List service:"
  for i in "${!services[@]}"; do 
    printf "%s\t%s\n" "$i" "${services[$i]}"
  done
}

getInputFromUser() {
  echo "Choice service that you want to rebuild, default will rebuild all service."
  read -p "Enter service index (that will be rebuild) separated by 'space' : " input
  for i in ${input[@]}
  do
    servicesWillBeRebuild+=($i)
  done
}

containsElement() {
  local e match="$1"
  shift
  for e; do [[ "$e" == "$match" ]] && return 0; done
  return 1
}

buildService() {
  cd ../microservice
  local executeCommand="./mvnw -am clean package -Dmaven.test.skip=true "
  for index in "${!servicesWillBeRebuild[@]}"; do
    local serviceIndex="${servicesWillBeRebuild[$index]}"
    local serviceName="microservice-${services[$serviceIndex]}"
    executeCommand+="-pl $serviceName "
  done
  eval $executeCommand
}

buildDocker() {
  cd ../docker
  local dockerBuildCommand="docker-compose build "
  for index in "${!servicesWillBeRebuild[@]}"; do
    local serviceIndex="${servicesWillBeRebuild[$index]}"
    local serviceName="${services[$serviceIndex]//-}"
    dockerBuildCommand+="$serviceName "
  done
  eval $dockerBuildCommand
  docker-compose up -d
}

main $@
