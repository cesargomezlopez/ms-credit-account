# ms-credit-account

### Steps to deploy in docker:
Once you have Docker installed and running its daemon, we open the bash in the path where the Dockerfile is located and execute the following commands:
```
docker network create everis --attachable (only if our spring-config-server-application network is not created yet)
docker build -t ms-credit-account .
docker run --network=everis --name ms-credit-account -p 8888:8888 ms-credit-account:latest
```