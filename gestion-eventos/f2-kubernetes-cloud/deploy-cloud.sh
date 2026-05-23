#gcloud container clusters resize cluster-gestion-eventos --num-nodes=1 --zone europe-southwest1-a

mvn clean package

docker build -f ws-catalogo/src/main/docker/Dockerfile.jvm -t gestion-eventos-ws-catalogo:latest ws-catalogo
docker build -f ws-reservas/src/main/docker/Dockerfile.jvm -t gestion-eventos-ws-reservas:latest ws-reservas

docker tag gestion-eventos-ws-catalogo:latest europe-southwest1-docker.pkg.dev/gestionreservaseventos/repo-eventos/ws-catalogo:v1
docker tag gestion-eventos-ws-reservas:latest europe-southwest1-docker.pkg.dev/gestionreservaseventos/repo-eventos/ws-reservas:v1

docker push europe-southwest1-docker.pkg.dev/gestionreservaseventos/repo-eventos/ws-catalogo:v1
docker push europe-southwest1-docker.pkg.dev/gestionreservaseventos/repo-eventos/ws-reservas:v1

gcloud container clusters get-credentials cluster-gestion-eventos --region europe-southwest1-a

kubectl apply -f f2-kubernetes-cloud/ws-catalogo.yaml
kubectl apply -f f2-kubernetes-cloud/ws-reservas.yaml
kubectl apply -f f2-kubernetes-cloud/ingress.yaml
kubectl apply -f f2-kubernetes-cloud/ws-catalogo-hpa.yaml



#kubectl get pods -w


#obtener IPs publicas
#kubectl get svc

#kubectl get svc ws-catalogo-service
#kubectl get ingress api-gateway-ingress -w

#monitorear estado HPA
#kubectl get hpa -w

#kubectl scale deployment ws-catalogo --replicas=1

#atacar ws-catalogo
#ab -n 100000 -c 15 http://8.232.193.87/eventos/