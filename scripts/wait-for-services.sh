#! /bin/bash

done=false

host=127.0.0.1
port=8080
serviceName=$1
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

main() {
	echo "${serviceName} is checking"
	declare -a validate
	while [[ "$done" = false ]]; do
		# check service started by actuator health
		if [[ "$serviceName" = "kafka" ]]; then
			local statusContainer=$(checkContainerIsRunning "sample-data-cassandra")
			local statusContainer1=$(checkContainerIsRunning $serviceName)
			if [ "${statusContainer}" == "0" ] && [ "${statusContainer1}" == "1" ]; then
				echo "${serviceName} is started"
				return 0;
			else
				echo -n .
				sleep 5
				continue;
			fi
		fi

		# check service started by actuator health
		healthPath="actuator/health"
		urlCheck=http://${host}:${port}/${serviceName}/${healthPath}
		if [[ "$serviceName" = "api-gateway" ]]; then
			port=8080
			urlCheck=http://${host}:${port}/${healthPath}
		fi
		if [[ "$serviceName" = "eureka-server" ]]; then
			port=8761
			urlCheck=http://${host}:${port}/${healthPath}
		fi
		if [[ "$serviceName" = "config-server" ]]; then
			port=9191
			urlCheck=http://${host}:${port}/${healthPath}
		fi
		local statusCode=$(checkStatus $urlCheck)
		if [ "${statusCode}" == "200" ]; then
			done=true
		else
			done=false
		fi
		if [[ "$done" = true ]]; then
			echo "${serviceName} connected"
			break;
		fi
		#curl -q http://${1?}:8080/health >& /dev/null && curl -q http://${1?}:8081/health >& /dev/null && curl -q http://${1?}:8082/health >& /dev/null
		echo -n .
		sleep 5
		local statusContainer=$(checkContainerIsRunning $serviceName)
		if [ "${statusContainer}" == "0" ]; then
			echo "${serviceName} is stopped"
			return 1;
			# break;
		fi
	done
}

checkContainerIsRunning() {
	cd $DIR/../docker
	if [ -z `docker compose ps -q ${1}` ] || [ -z `docker ps -q --no-trunc | grep $(docker compose ps -q ${1})` ]; then
		echo 0
	else
		echo 1
	fi
	cd $DIR
}

checkStatus() {
	local status="$(curl -Is ${1} | head -1)"
	declare -a validate
	for i in ${status[@]}
	do
		validate+=($i)
	done
	echo ${validate[1]}
}

main