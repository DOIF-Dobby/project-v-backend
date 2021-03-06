#!/bin/bash

CURRENT_PORT=$(cat /home/ec2-user/service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "> Current port of running WAS is ${CURRENT_PORT}"

if [ "${CURRENT_PORT}" -eq 8081 ]; then
  TARGET_PORT=8082
elif [ "${CURRENT_PORT}" -eq 8082 ]; then
  TARGET_PORT=8081
else
  echo "> No WAS is connected to nginx"
fi

TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

if [ -n "${TARGET_PID}" ]; then
  echo "> Kill WAS running at ${TARGET_PORT}"
  sudo kill "${TARGET_PID}"
fi

sleep 5

nohup java -jar -Dcom.amazonaws.sdk.disableEc2Metadata=true -Dspring.profiles.active=dev -Dserver.port=${TARGET_PORT} -Dspring.config.location=file:/home/ec2-user/config/projectv/application.yml /home/ec2-user/projectv/build/libs/*.jar > /home/ec2-user/nohup.out 2>&1 &





