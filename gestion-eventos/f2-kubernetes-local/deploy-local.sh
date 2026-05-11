mvn clean package

docker build -f ws-catalogo/src/main/docker/Dockerfile.jvm -t gestion-eventos-ws-catalogo:latest ws-catalogo
docker build -f ws-reservas/src/main/docker/Dockerfile.jvm -t gestion-eventos-ws-reservas:latest ws-reservas

kubectl apply -f f2-kubernetes-local/catalogo-db-configmap.yaml
kubectl apply -f f2-kubernetes-local/catalogo-db.yaml

kubectl apply -f f2-kubernetes-local/reservas-db-configmap.yaml
kubectl apply -f f2-kubernetes-local/reservas-db.yaml

kubectl rollout status statefulset/eventos-db
kubectl rollout status statefulset/reservas-db

kubectl apply -f f2-kubernetes-local/ws-catalogo.yaml
kubectl apply -f f2-kubernetes-local/ws-reservas.yaml

kubectl get pods -w