FROM adoptopenjdk:16-jre-hotspot as builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} payment-service.jar
RUN java -Djarmode=layertools -jar payment-service.jar extract

FROM adoptopenjdk:16-jre-hotspot
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]