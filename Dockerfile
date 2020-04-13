FROM openjdk:8-jdk-slim
COPY "./target/ms-credit-account-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8003
ENTRYPOINT ["java", "-jar", "app.jar"]