# Plataforma Cloud-Native de Gestión de Eventos y Reservas

Este repositorio contiene la arquitectura distribuida, el código fuente y los manifiestos declarativos de infraestructura correspondientes al Trabajo Fin de Grado titulado **"Estudio sobre Google Kubernetes Engine (GKE): diseño, implementación y validación de arquitecturas cloud-native"** presentado en la Facultad de Informática de la Universidad Pontificia de Salamanca (UPSA) en junio de 2026.

La plataforma ha sido diseñada y desplegada bajo los estándares del paradigma *Cloud-Native*, transitando desde un entorno monolítico local hasta un ecosistema distribuido, elástico y resiliente en la nube pública de Google Cloud Platform (GCP). Todo el diseño estructural ha sido sometido a un riguroso plan de pruebas de estrés y tolerancia a fallos, validando empíricamente su comportamiento en producción.

## 📊 Arquitectura del Sistema

La topología técnica de la solución se despliega sobre un clúster administrado de **Google Kubernetes Engine (GKE)** y está estructurada en tres capas operativas desacopladas:

```text
                             [ Petición HTTP / Cliente Externo ]
                                              │
──────────────────────────────────────────────▼──────────────────────────────────────────────
CAPA DE ACCESO           ┌─────────────────────────────────────────┐
                         │   Cloud Load Balancer IP: 34.120.13.33  │ (Google Cloud Load Balancing)
                         └────────────────────┬────────────────────┘
                                              │
                                              ▼
                         ┌─────────────────────────────────────────┐
                         │       GKE Ingress Controller (L7)       │ (Rutas: /eventos y /reservas)
                         └──────────────┬────────────┬─────────────┘
                                        │            │
                                /eventos│            │/reservas
                                        ▼            ▼
──────────────────────────────────────────────────────────────────────────────────────────────
CAPA DE CÓMPUTO          ┌──────────────────────┐    ┌──────────────────────┐
(Clúster GKE)            │  ws-catalogo-service │    │  ws-reservas-service │ (Kubernetes Services)
                         │     (ClusterIP)      │    │     (ClusterIP)      │
                         └──────────┬───────────┘    └──────────┬───────────┘
                                    │                           │
                                    ▼                           ▼
                         ┌──────────────────────┐    ┌──────────────────────┐
                         │ Pod: ws-catalogo     │    │ Pod: ws-reservas     │ (Escalable vía HPA)
                         │ ├─ Contenedor Java   │    │ ├─ Contenedor Java   │ (Quarkus Framework)
                         │ └─ Sidecar Auth Proxy│    │ └─ Sidecar Auth Proxy│ (Cloud SQL Auth Proxy)
                         └──────────┬───────────┘    └──────────┬───────────┘
                                    │ localhost:5432            │ localhost:5432
                                    └───────────┐    ┌──────────┘
                                      Túnel TLS │    │ Túnel TLS (Backbone Privado GCP)
                                                ▼    ▼
──────────────────────────────────────────────────────────────────────────────────────────────
CAPA DE PERSISTENCIA     ┌──────────────────────────────────────────────────┐
                         │          Instancia Google Cloud SQL              │
                         │  ┌──────────────────────┐ ┌───────────────────┐  │ (PostgreSQL Relacional)
                         │  │      eventos-db      │ │    reservas-db    │  │ (Pattern: Database-per-Service)
                         │  └──────────────────────┘ └───────────────────┘  │
                         └──────────────────────────────────────────────────┘
```
Capa de Acceso y Enrutamiento (Capa 7 HTTP): Centraliza la entrada perimetral bajo una única dirección IP pública global y un único puerto. Utiliza un recurso Ingress Controller nativo de Google Cloud que actúa como API Gateway, evaluando las rutas de las URLs entrantes para derivar el tráfico hacia los servicios internos según el prefijo configurado (/eventos o /reservas).  

Capa de Lógica de Negocio (Clúster GKE): Compuesta por microservicios desacoplados desarrollados en Java sobre el framework Quarkus. Esta elección tecnológica optimiza la huella de memoria en contenedores, permitiendo arranques rápidos indispensables para absorber picos repentinos de tráfico. Los Pods se encuentran aislados de la red exterior y se interconectan internamente mediante recursos Service de tipo ClusterIP, resolviendo la comunicación inter-servicio de forma estática a través del CoreDNS del clúster.  
  
Capa de Persistencia (Database-per-Service): Para evitar cuellos de botella transaccionales y acoplamientos lógicos en los esquemas de datos, el microservicio de Catálogo y el de Reservas disponen de bases de datos lógicas independientes que coexisten de manera aislada dentro de una misma instancia relacional administrada de Google Cloud SQL.  

## 🛡️ Hardening y Seguridad Perimetral
El blindaje de la infraestructura en producción se ha articulado bajo dos principios fundamentales de la ingeniería de sistemas moderna:  

Enfoque Secretless con Workload Identity: Se ha eliminado por completo el almacenamiento estático de contraseñas de producción o cadenas de conexión en archivos de propiedades o variables de entorno expuestas. GKE asocia de forma criptográfica la cuenta de servicio de Kubernetes (KSA) con la cuenta de IAM de Google Cloud (GSA). Al arrancar, el orquestador inyecta un token dinámico temporal de un solo uso que valida los accesos de forma nativa contra la base de datos.  

Aislamiento Perimetral mediante Patrón Sidecar: La instancia de Cloud SQL no expone puertos públicos hacia internet, protegiéndose tras el firewall de Google Cloud. La conexión segura se delega en el componente Cloud SQL Auth Proxy, que se inyecta como un contenedor secundario (sidecar) dentro del mismo Pod de la aplicación. El código Java de Quarkus se comunica de forma transparente contra el bucle de retorno local (localhost:5432), y el proxy sidecar se encarga de empaquetar, encriptar y transportar ese flujo de datos bajo un túnel TLS privado a través de la red interna de Google.  
  

## ⚙️ Especificaciones del Banco de Pruebas
Para garantizar que los resultados de las comprobaciones reflejen un entorno de producción real, las pruebas empíricas se ejecutaron directamente sobre la infraestructura cloud desplegada en los centros de datos de Google:  
  
Infraestructura Cloud: Clúster Standard regional zonal de GKE instanciado en la región de Madrid (europe-southwest1-a). Los nodos de trabajo están compuestos por máquinas virtuales optimizadas configuradas bajo el modelo de costes reducidos Spot Nodes de Google.  

Entorno de Ejecución de Contenedores: Los nodos ejecutan un sistema operativo inmutable diseñado por Google y optimizado específicamente para garantizar el aislamiento y velocidad de los procesos de los contenedores.
Gestión de Conexiones de Persistencia: Para soportar el impacto transaccional simultáneo sin saturar la capacidad de hilos de PostgreSQL, Quarkus gestiona el pool de conexiones de forma nativa a través de la extensión Agroal, acotando el tamaño del pool por Pod de forma proporcional al escalado del clúster.  
  
## 📊 Escenarios de Validación y Resultados Empíricos
1. Prueba de Resiliencia y Alta Disponibilidad (Self-Healing)
Estímulo: Inyección de un fallo crítico mediante la destrucción forzosa de la instancia activa del catálogo (kubectl delete pod) en pleno procesamiento de tráfico.

Comportamiento Declarativo: El controlador detecta instantáneamente la pérdida de la cuota de disponibilidad configurada e inicia la reprogramación asíncrona del Pod de reemplazo.  

Métricas de Recuperación: El clúster restaura el servicio y alcanza el estado estable 2/2 READY en exactamente 16 segundos. El balanceador de carga corta automáticamente el flujo de red hacia el contenedor en reconstrucción, redirigiéndolo al nuevo Pod solo cuando este supera con éxito las Readiness Probes perimetrales de Quarkus.  
  
2. Prueba de Actualización Progresiva (Rolling Updates)
Estímulo: Lanzamiento de una nueva versión del software en producción o forzado de recarga de configuración base (kubectl rollout restart deployment).

Comportamiento Declarativo: Kubernetes orquestó una transición incremental levantando un nuevo ReplicaSet en paralelo. La versión antigua y la nueva convivieron de forma segura compartiendo el tráfico.  
  
Métricas de Recuperación: Se registró un Uptime continuo del 100% (Zero Downtime). El Ingress perimetral asimiló el cambio de enrutamiento de manera progresiva, eliminando los Pods obsoletos exclusivamente después de que las nuevas instancias notificaran un estado saludable contra la base de datos, evitando la pérdida de transacciones de reservas activas.  
  
3. Prueba de Elasticidad y Autoescalado Horizontal (HPA)
Estímulo: Simulación de una avalancha imprevista de tráfico masivo mediante la inyección de 100.000 peticiones HTTP concurrentes dirigidas a la API Gateway utilizando la herramienta de estrés ApacheBench (ab).  
  
Comportamiento Declarativo: El componente Metrics Server del Plano de Control registró picos de saturación de CPU sobre la asignación mínima. El recurso Horizontal Pod Autoscaler reaccionar ante la sobrecarga ordenando la clonación inmediata de los Pods.  
  
Métricas de Recuperación: El clúster escaló de forma secuencial y transparente de 1 a 5 réplicas concurrentes en menos de 40 segundos, logrando absorber el impacto completo del ataque. ApacheBench finalizó el experimento registrando un 100% de éxito en las solicitudes con código de estado HTTP 200. Tras finalizar la inyección de carga y enfriarse las métricas de cómputo, el HPA destruirá de forma automática las réplicas sobrantes para reducir los costes operativos a la mínima expresión.  
  
## 🔮 Líneas de Evolución Futura
AIOps asistido mediante Model Context Protocol (MCP): Integración de un servidor MCP nativo dentro del clúster de GKE para conectar grandes modelos de lenguaje (LLMs) con la API de Kubernetes de forma segura. Esto permitirá dotar a la IA del contexto en tiempo real de los logs de Quarkus y los eventos operacionales del sistema para realizar diagnósticos de anomalías en tiempo real y ejecutar decisiones adaptativas y predictivas que mejoren las reglas de nuestro HPA.  
  
FinOps e Infraestructura Sostenible (Green Computing): Despliegue de herramientas de observabilidad energética como Kepler y monitorización de costes cloud como Kubecost para asociar la eficiencia del código Java con el coste de facturación real de GCP y la huella de carbono emitida por cada microservicio.

Evolución hacia Service Mesh (Istio): Implementación de una malla de servicios para aislar por completo la seguridad de la capa de aplicación, forzando un cifrado de extremo a extremo nativo y automático entre Pods mediante mTLS, además de habilitar estrategias avanzadas de tráfico como los despliegues de canario.

Automatización GitOps Declarativa: Sustitución de los despliegues manuales por la integración de ArgoCD, transformando este repositorio de GitHub en la única fuente de verdad: cualquier cambio aprobado en el código o en los YAML desencadenará asíncronamente una sincronización directa en GKE.  
