
echo "Iniciando proceso de compilación y despliegue local"

mvn clean package

docker build -f ws-catalogo/src/main/docker/Dockerfile.jvm -t gestion-eventos-ws-catalogo:latest ws-catalogo

kubectl apply -f f1-kubernetes-local/postgres-configmap.yaml

kubectl apply -f f1-kubernetes-local/postgres-db.yaml

kubectl rollout status statefulset/eventos-db

kubectl apply -f f1-kubernetes-local/ws-catalogo.yaml

kubectl get pods -w