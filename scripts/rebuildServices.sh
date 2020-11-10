#!/bin/sh
services=("storage" "contact" "message" "security" "user" "config-server" "eureka-server" "turbine-server" "zuul-server" "chatapp")
servicesNeedWaitWhenStart=("storage" "contact" "message" "security" "user" "config" "zuul")
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

declare -a servicesWillBeRebuild

main() {
  printListService
  getInputFromUser
  read -p "Do you wish to rebuild services? y/n: " ynService
  read -p "Do you wish to build and start docker? y/n: " ynDocker
  case $ynService in
    [Yy]* ) buildService;;
  esac
  if [[ "$?" -ne 0 ]] ; then
    echo 'Build service has error. Please check your code.'; exit $rc
  fi
  case $ynDocker in
    [Yy]* ) buildAndStartDocker;;
  esac
}

printListService() {
  echo "List service:"
  for i in "${!services[@]}"; do 
    printf "%s\t%s\n" "$i" "${services[$i]}"
  done
}

getInputFromUser() {
  echo "Choice service that you want to rebuild, default do not rebuild any services."
  read -p "Enter service index (that will be rebuild) separated by 'space' : " input
  for i in ${input[@]}
  do
    servicesWillBeRebuild+=($i)
  done
}

buildService() {
  cd ../microservice
  local executeCommand="./mvnw -am clean package -Dmaven.test.skip=true "
  local needBuildServiceJava=0
  for index in "${!servicesWillBeRebuild[@]}"; do
    local serviceIndex="${servicesWillBeRebuild[$index]}"
    local serviceName="microservice-${services[$serviceIndex]}"
    if [[ ${serviceName} != *"chatapp"* ]]; then
      needBuildServiceJava=1
      executeCommand+="-pl $serviceName "
    else
      cd $DIR/../client/web/chatapp && yarn build
      cd $DIR/../microservice
    fi
  done
  if [[ "${needBuildServiceJava}" = "1" ]]; then
    eval $executeCommand
  fi
}

buildAndStartDocker() {
  cd ../docker
  local dockerBuildCommand="docker-compose build "
  local dockerUpCommand="docker-compose up -d "
  for index in "${!servicesWillBeRebuild[@]}"; do
    local serviceIndex="${servicesWillBeRebuild[$index]}"
    local serviceName="${services[$serviceIndex]//-}"
    serviceName="${serviceName//server}"
    dockerBuildCommand+="$serviceName "
    dockerUpCommand+="$serviceName "
  done
  eval $dockerBuildCommand
  
  docker-compose up -d zuul config
  $DIR/wait-for-services.sh config
  
  eval $dockerUpCommand

  for index in "${!servicesWillBeRebuild[@]}"; do
    local serviceIndex="${servicesWillBeRebuild[$index]}"
    local serviceName="${services[$serviceIndex]//-}"
    serviceName="${serviceName//server}"
    if [[ " ${servicesNeedWaitWhenStart[@]} " =~ " ${serviceName} " ]]; then
      $DIR/wait-for-services.sh $serviceName
    fi
  done
}

main $@
