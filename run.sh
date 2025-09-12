#!/bin/bash

back(){
  echo 'executando somente a modalidade do back end'
  docker stop app-hub-api
  rm app-hub-api
  docker-compose up -d --build backend
}

front(){
  echo 'executando somente a modalidade do front'
  docker stop app-hub-front
  docker rm app-hub-front
  docker-compose up -d --build
}

full(){
  echo 'executando somente a modalidade do full'
  docker stop app-hub-api
  docker stop app-hub-front

  docker rm app-hub-api
  docker rm app-hub-front

  docker-compose up -d --build
}

while true; do
    read -p $'Escolha \n FRONT/BACK (1) \n BACK (2) \n FRONT (3) \n CANCELAR (0) \n > ' escolha
    case $escolha in
        1) full; break ;;
        2) back; break ;;
        3) front; break ;;
        0) echo "Você escolheu CANCELAR"; break ;;
        *) echo "Opção inválida, tente novamente.";;
    esac
done