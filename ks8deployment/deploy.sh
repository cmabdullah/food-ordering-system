kubectl apply -f order-deployment-local.yml
kubectl apply -f order-deployment-service-local.yml
kubectl apply -f payment-deployment-local.yml
kubectl apply -f payment-deployment-service-local.yml
kubectl apply -f restaurant-deployment-local.yml
kubectl apply -f restaurant-deployment-service-local.yml
kubectl apply -f customer-deployment-local.yml
kubectl apply -f customer-deployment-service-local.yml
kubectl apply -f order-node-port.yml
kubectl apply -f customer-node-port.yml