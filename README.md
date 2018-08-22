# Flink on Kubernetes
This repository is a sample setup to run an Apache Flink job in Kubernetes.

It uses the new StandaloneJob entry point introduced in Flink 1.6.0 which means that when the JobManager starts,
it will automatically run the job that is packaged in the JAR. 

## Setup
First, compile the example application

    mvn clean package
    
Then, make sure you build the Docker image and make it available to your Kubernetes cluster.
When the Docker image is available, set up the Flink cluster by applying all manifests from the `kubernetes` folder:

    kubectl apply -f kubernetes
    
If you want to see the JobManager UI, you can forward its port to your local machine:

    kubectl port-forward <jobmanager-podname> 8081
    
You can safely scale the TaskManagers up or down:
       
    kubectl scale deployment flink-taskmanager --replicas=4
    
As soon as the required number of task slots are available the job will start running.