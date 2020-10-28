#! /bin/bash

done=false

host=127.0.0.1
port=8080
serviceName=$1

main() {
	declare -a validate
	while [[ "$done" = false ]]; do
		healthPath="actuator/health"
		if [[ "$serviceName" = "message" ]]; then
			healthPath="health"
		fi
		urlCheck=http://${host}:${port}/${serviceName}/${healthPath}
		if [[ "$serviceName" = "zuul" ]]; then
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
		sleep 1
	done
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