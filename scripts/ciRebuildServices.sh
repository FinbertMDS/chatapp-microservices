#!/bin/bash
services=("storage" "contact" "message" "security" "config-server" "eureka-server" "api-gateway" "chatapp")
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

declare -a servicesInput
declare -a servicesWillBeRebuild

main() {
  initGitAtFolderConfig
  servicesInput+=($@)
  validateServicesName $@
  buildDocker
}

initGitAtFolderConfig() {
  if [ ! -d "${DIR}/../config/.git" ]; then
    cd ../config
    git init
    git add .
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

buildDocker() {
  cd $DIR/../docker
  docker compose build --no-cache
  docker compose -f docker-compose-service.yml build --no-cache
}

main $@
