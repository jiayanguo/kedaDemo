# start minikube
minikube start

# install keda
kubectl create namespace keda
kubectl apply -f deploy/crds/keda.k8s.io_scaledobjects_crd.yaml
kubectl apply -f deploy/crds/keda.k8s.io_triggerauthentications_crd.yaml
kubectl apply -f deploy/

#deploy rabbitMQ
helm install rabbitmq --set rabbitmq.username=user,rabbitmq.password=PASSWORD stable/rabbitmq

# deploy consumer
kubectl apply -f rabbitMQReceiver/deploy-consumer.yaml

#deploy producer
kubectl apply -f rabbitMQProducer/deploy-publisher-job.yaml
kubectl create job --from=cronjob/rabbitmq-publish producerjob

# watch result
kubectl get deploy -w
kubectl get hpa

#clean up
kubectl delete cronjob rabbitmq-publish
kubectl delete ScaledObject rabbitmq-consumer
kubectl delete deploy rabbitmq-consumer
helm delete rabbitmq

