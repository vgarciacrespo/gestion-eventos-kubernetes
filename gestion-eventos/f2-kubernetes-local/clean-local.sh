#!/bin/bash

echo " Iniciando limpieza total del clúster local (Fase 2)..."

echo " 1. Borrando los componentes lógicos (Microservicios)..."
kubectl delete -f f2-kubernetes-local/ws-reservas.yaml
kubectl delete -f f2-kubernetes-local/ws-catalogo.yaml

echo " 2. Borrando las bases de datos y configuración..."
kubectl delete -f f2-kubernetes-local/reservas-db.yaml
kubectl delete -f f2-kubernetes-local/reservas-db-configmap.yaml
kubectl delete -f f2-kubernetes-local/catalogo-db.yaml
kubectl delete -f f2-kubernetes-local/catalogo-db-configmap.yaml

echo " 3. Destruyendo los discos duros virtuales (PVCs)..."
kubectl delete pvc db-data-reservas-db-0 --ignore-not-found
kubectl delete pvc db-data-eventos-db-0 --ignore-not-found

echo " ¡Entorno 100% limpio y listo para un nuevo despliegue!"