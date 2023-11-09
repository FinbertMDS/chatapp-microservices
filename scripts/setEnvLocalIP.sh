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
    LOCAL_IP=${LOCAL_IP:-`ipconfig getifaddr en0`}
elif [[ ${machine} == "Linux" ]]; then
    LOCAL_IP=${LOCAL_IP:-`ifconfig | sed -En 's/127.0.0.1//;s/.*inet (addr:)?(([0-9]*\.){3}[0-9]*).*/\2/p'`}
else
    LOCAL_IP=`ipconfig | grep -im1 'IPv4 Address' | cut -d ':' -f2`
fi
echo "LOCAL_IP: ${LOCAL_IP}"

echo -e "REACT_APP_API_BASE_URL=http://$LOCAL_IP:8080\nREACT_APP_SOCKET_BASE_URL=http://$LOCAL_IP:8079/ws" > ../client/web/chatapp/.env
echo -e "API_BASE_URL=http://$LOCAL_IP:8080\nSOCKET_BASE_URL=http://$LOCAL_IP:8079/ws" > ../client/mobile/chatapp/.env
