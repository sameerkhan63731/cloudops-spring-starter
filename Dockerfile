FROM eclipse-temurin:17-jre
RUN useradd --system --uid 10001 spring
WORKDIR /app
COPY target/cloudops-spring-starter-1.0.0.jar app.jar
USER 10001
EXPOSE 8080
ENTRYPOINT ["java","-XX:MaxRAMPercentage=75","-jar","/app/app.jar"]
