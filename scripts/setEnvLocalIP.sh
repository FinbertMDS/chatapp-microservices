#!/bin/bash
IP=$(ipconfig getifaddr en0)
echo -e "REACT_APP_API_BASE_URL=http://$IP:8080\nREACT_APP_SOCKET_BASE_URL=http://$IP:8079/ws" > ../client/web/chatapp/.env
echo -e "API_BASE_URL=http://$IP:8080\nSOCKET_BASE_URL=http://$IP:8079/ws" > ../client/mobile/chatapp/.env