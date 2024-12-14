#!/bin/bash
servicesNeedWaitWhenStart=("contact" "message" "security" "storage" "api-gateway")
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

declare -a servicesWillBeRebuild

main() {
  initGitAtFolderConfig
  read -p "Do you wish to build docker? y/n: " ynBuildDocker
  read -p "Do you wish to start docker? y/n: " ynStartDocker
  if [[ "$?" -ne 0 ]] ; then
    echo 'Build service has error. Please check your code.'; exit $rc
  fi
  case $ynBuildDocker in
    [Yy]* ) buildDocker;;
  esac
  case $ynStartDocker in
    [Yy]* ) startDocker;;
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

buildDocker() {
  cd $DIR/../docker
  docker compose build --no-cache
  docker compose -f docker-compose-service.yml build --no-cache
  cd $DIR
}
  
startDocker() {
  cd ${DIR}/../docker
  docker compose up -d
  $DIR/wait-for-services.sh kafka
  
  docker-compose -f docker-compose-service.yml up -d config-server eureka-server
  $DIR/wait-for-services.sh config-server
  $DIR/wait-for-services.sh eureka-server
  
  docker-compose -f docker-compose-service.yml up -d
  $DIR/wait-for-services.sh api-gateway

  # for index in "${!servicesNeedWaitWhenStart[@]}"; do
  #   local serviceName="${servicesNeedWaitWhenStart[$index]//-}"
  #   for i in {1..5}; do
  #     $DIR/wait-for-services.sh $serviceName
  #     if [ $? == 1 ]; then
  #       sleep 5;
  #     else
  #       break;
  #     fi
  #   done
  #   fi
  # done
  cd $DIR
}

main $@
