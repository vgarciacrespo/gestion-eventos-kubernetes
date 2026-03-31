#!/bin/bash

echo "🚀 Iniciando proceso de compilación y despliegue local..."

echo "📦 1. Compilando el proyecto multi-módulo (Domain + ws-catalogo)..."
# Ejecutamos Maven desde la RAÍZ. El Reactor compilará primero 'domain' y luego 'ws-catalogo'
mvn clean package

echo "🐳 2. Construyendo la imagen Docker localmente..."
# Construimos la imagen usando la subcarpeta como contexto para que encuentre el 'target'
docker build -f ws-catalogo/src/main/docker/Dockerfile.jvm -t gestion-eventos-ws-catalogo:latest ./ws-catalogo

echo "☸️ 3. Desplegando la Base de Datos en Kubernetes..."
kubectl apply -f postgres-configmap.yaml
kubectl apply -f postgres-db.yaml

echo "⏳ Esperando 10 segundos para asegurar que PostgreSQL inicializa..."
sleep 10

echo "☸️ 4. Desplegando el microservicio ws-catalogo en Kubernetes..."
kubectl apply -f ws-catalogo.yaml

echo "✅ ¡Despliegue completado! Estado actual de los contenedores (Pods):"
kubectl get pods