#!/bin/bash

echo " Iniciando limpieza total del clúster local..."

echo " 1. Borrando los componentes lógicos (Manifiestos YAML)..."
kubectl delete -f f1-kubernetes-local/ws-catalogo.yaml
kubectl delete -f f1-kubernetes-local/postgres-db.yaml
kubectl delete -f f1-kubernetes-local/postgres-configmap.yaml

echo " 2. Destruyendo el disco duro virtual (PVC)..."
# Usamos --ignore-not-found para que no dé error si ya estaba borrado
kubectl delete pvc db-data-eventos-db-0 --ignore-not-found

echo " ¡Entorno 100% limpio y listo para un nuevo despliegue!"