#!/bin/sh
services=("storage" "contact" "message" "security" "user" "config-server" "eureka-server" "turbine-server" "zuul-server" "chatapp")
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

declare -a servicesInput
declare -a servicesWillBeRebuild

main() {
  initGitAtFolderConfig
  servicesInput+=($@)
  validateServicesName $@
  buildService
  if [[ "$?" -ne 0 ]]; then
    echo 'Build service has error. Please check your code.'
    exit $rc
  fi
  buildDocker
}

initGitAtFolderConfig() {
  if [ ! -d "${DIR}/../config/.git" ]; then
    cd ../config
    git init
    git commit -m "init"
    cd $DIR
  fi
}

validateServicesName() {
  for index in "${!servicesInput[@]}"; do
    if [[ ${services[@]} =~ ${servicesInput[$index]} ]]; then
      for element in "${services[@]}"; do
        if [[ $element == "${servicesInput[$index]}" ]]; then
          servicesWillBeRebuild+=(${servicesInput[$index]})
        fi
      done
    fi
  done
}

buildService() {
  cd ../microservice
  local executeCommand="./mvnw -am clean package "
  local needBuildServiceJava=0
  for index in "${!servicesWillBeRebuild[@]}"; do
    local serviceName="microservice-${servicesWillBeRebuild[$index]}"
    if [[ ${serviceName} != *"chatapp"* ]]; then
      needBuildServiceJava=1
      executeCommand+="-pl $serviceName "
    else
      cd $DIR/../client/web/chatapp && npm run build --if-present
      cd $DIR/../microservice
    fi
  done
  if [[ "${needBuildServiceJava}" = "1" ]]; then
    eval $executeCommand
  fi
}

buildDocker() {
  cd ../docker
  local dockerBuildCommand="docker-compose build "
  for index in "${!servicesWillBeRebuild[@]}"; do
    local serviceName="${servicesWillBeRebuild[$index]//-/}"
    serviceName="${serviceName//server/}"
    dockerBuildCommand+="$serviceName "
  done
  eval $dockerBuildCommand
}

main $@
