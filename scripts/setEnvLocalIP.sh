#!/bin/bash
LOCAL_IP=192.168.1.2 # change local ip address here

unameOut="$(uname -s)"
case "${unameOut}" in
    Linux*)     machine=Linux;;
    Darwin*)    machine=Mac;;
    CYGWIN*)    machine=Cygwin;;
    MINGW*)     machine=MinGw;;
    MSYS_NT*)   machine=Git;;
    *)          machine="UNKNOWN:${unameOut}"
esac
echo "OS: ${machine}"

if [[ ${machine} == "Mac" ]]; then
    network_device=$(scutil --dns |awk -F'[()]' '$1~/if_index/ {print $2;exit;}')
    echo "Network device: $network_device"
    LOCAL_IP=$(ipconfig getifaddr "$network_device")
elif [[ ${machine} == "Linux" ]]; then
    LOCAL_IP=${LOCAL_IP:-`ifconfig | sed -En 's/127.0.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p'`}
else
    # -im10: maximum of ipv4 is 10
    temp=`ipconfig | grep -im10 'IPv4' | cut -d ':' -f2`
    echo "IPv4 temp: ${temp}"
    readarray -t <<<$temp
    for (( i=0; i<${#MAPFILE[@]}; i++ ))
    do
        echo "IPv4 - ${i}: ${MAPFILE[$i]}"
        if [[ " ${MAPFILE[$i]} " =~ "192.168" ]]; then
            LOCAL_IP=`echo ${MAPFILE[$i]} | sed 's/ *$//g'`
        fi
    done
fi
echo "LOCAL_IP: ${LOCAL_IP}"

echo -e "REACT_APP_API_BASE_URL=http://$LOCAL_IP:8080\nREACT_APP_SOCKET_BASE_URL=http://$LOCAL_IP:8080/ws" > ../client/web/chatapp/.env
echo -e "API_BASE_URL=http://$LOCAL_IP:8080\nSOCKET_BASE_URL=http://$LOCAL_IP:8080/ws\nAPI_BASE_URL_ANDROID=http://10.0.2.2:8080\nSOCKET_BASE_URL_ANDROID=http://10.0.2.2:8080/ws" > ../client/mobile/chatapp/.env
