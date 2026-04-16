#!/bin/bash

echo "Iniciando proceso de compilación y despliegue local"

echo " 1. Compilando el proyecto multi-módulo (Domain + ws-catalogo)"
mvn clean package

echo "2. Construyendo la imagen Docker localmente"
# Construimos la imagen usando la subcarpeta como contexto para que encuentre el 'target'
docker build -f ws-catalogo/src/main/docker/Dockerfile.jvm -t gestion-eventos-ws-catalogo:latest ./ws-catalogo

echo " 3. Desplegando la Base de Datos en Kubernetes"
kubectl apply -f postgres-configmap.yaml
kubectl apply -f postgres-db.yaml

echo " Esperando a postgreSQL"
kubectl rollout status statefulset/eventos-db

echo "4. Despliegue ws-catalogo"
kubectl apply -f ws-catalogo.yaml

# verificar estado pods
kubectl get pods -w