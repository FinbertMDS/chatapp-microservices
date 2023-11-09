#!/bin/bash
services=("contact" "message" "security" "user" "config-server" "eureka-server" "zuul-server" "chatapp")
servicesNeedWaitWhenStart=("contact" "message" "security" "user" "config" "zuul")
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

declare -a servicesWillBeRebuild

main() {
  initGitAtFolderConfig
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

initGitAtFolderConfig() {
  if [ ! -d "${DIR}/../config/.git" ]; then
    cd ${DIR}/../config
    git init
    git add .
    git commit -m "init"
    cd $DIR
  fi
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
  cd ${DIR}/../microservice
  local executeCommand="./mvnw -am clean package -Dmaven.test.skip=true "
  local needBuildServiceJava=0
  for index in "${!servicesWillBeRebuild[@]}"; do
    local serviceIndex="${servicesWillBeRebuild[$index]}"
    local serviceName="microservice-${services[$serviceIndex]}"
    if [[ ${serviceName} != *"chatapp"* ]]; then
      needBuildServiceJava=1
      executeCommand+="-pl $serviceName "
    else
      cd $DIR/../client/web/chatapp && yarn && yarn build
      cd $DIR/../microservice
    fi
  done
  if [[ "${needBuildServiceJava}" = "1" ]]; then
    eval $executeCommand
  fi
}

buildAndStartDocker() {
  cd ${DIR}/../docker
  local dockerBuildCommand="docker-compose -f docker-compose-base.yml -f docker-compose-message.yml build "
  local dockerUpCommand="docker-compose -f docker-compose-base.yml -f docker-compose-message.yml up -d "
  for index in "${!servicesWillBeRebuild[@]}"; do
    local serviceIndex="${servicesWillBeRebuild[$index]}"
    local serviceName="${services[$serviceIndex]//-}"
    serviceName="${serviceName//server}"
    if [[ "$serviceName" == "message" ]]; then
      eval "docker-compose -f docker-compose-base.yml -f docker-compose-message.yml build --no-cache cassandra sample-data-cassandra"
    else
      dockerBuildCommand+="$serviceName "
      dockerUpCommand+="$serviceName "
    fi
  done
  eval $dockerBuildCommand
  
  docker-compose -f docker-compose-base.yml -f docker-compose-message.yml up -d zuul config
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
