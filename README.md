# Flink on Kubernetes
This repository is a sample setup to run an Apache Flink cluster in Kubernetes. The `example-app` directory contains a simple Flink job we will submit to the cluster.


#### Setting up the Flink cluster
Set up the Flink cluster, this will create a single JobManager and 2 TaskManagers:

    kubectl apply -f kubernetes/flink
    
If you want to see the JobManager UI, you can forward its port to your local machine:

    kubectl port-forward <jobmanager-podname> 8081
    
You can safely scale the TaskManagers:
       
    kubectl scale deployment flink-taskmanager --replicas=4
    
When scaling down, note that it might take a while before the JobManager removes the TaskManagers from its internal state.
    
#### Running the example app
Build the Docker image:

    eval $(minikube docker-env)
    docker build -t example-app .
    
The image will submit the application JAR to the Flink cluster when started, so we can run it using a Kubernetes Job resource, which will terminate once completed successfully.
    
    kubectl apply -f kubernetes/app
    
