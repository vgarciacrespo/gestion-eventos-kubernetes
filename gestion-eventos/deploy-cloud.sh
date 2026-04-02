# Arrancar el cluster en google cloud
# gcloud container clusters resize cluster-gestion-eventos --num-nodes=1 --zone europe-southwest1-a


mvn clean package

#imagen docker
docker build -f ws-catalogo/src/main/docker/Dockerfile.jvm -t gestion-eventos-ws-catalogo:latest ./ws-catalogo

#tagear imagen
docker tag gestion-eventos-ws-catalogo:latest europe-southwest1-docker.pkg.dev/gestionreservaseventos/repo-eventos/ws-catalogo:v2

#subir imagen
docker push europe-southwest1-docker.pkg.dev/gestionreservaseventos/repo-eventos/ws-catalogo:v2

# Arrancar el cluster en google cloud
#gcloud container clusters resize cluster-gestion-eventos --num-nodes=1 --zone europe-southwest1-a

#Conectar terminal a la nube
gcloud container clusters get-credentials cluster-gestion-eventos --region europe-southwest1-a

#Desplegar GKE
kubectl apply -f ws-catalogo-cloud.yaml

# ver si ha funcionado
kubectl get pods -w



