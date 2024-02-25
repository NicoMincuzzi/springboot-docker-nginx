# Stage 1: Build the application
FROM maven:3.8.6-amazoncorretto-11@sha256:ea3950751ee0bb51032bc420bb9f54c83a7e72bafec61f5cf7f7b72b5b5699fe AS build
WORKDIR /app
COPY pom.xml .
COPY src src
RUN mvn clean package -DskipTests

# Stage 2: Create a smaller runtime image
FROM amazoncorretto:11-alpine-jdk@sha256:1a24347f430decabc6ecdb1cb5175cd9f70c1de7d36b2c902d904ba66fb70cba
WORKDIR /opt/dummy_app
# set user/group IDs
RUN addgroup -S -g 1001 dummy-app && adduser -S -h /home/dummy_app -u 1001 dummy-app -G dummy-app
RUN chown -R dummy-app:dummy-app /opt/dummy_app
USER dummy-app

COPY --from=build /app/target/dummy-app.jar /opt/dummy_app/dummy-app.jar

HEALTHCHECK --interval=5s \
            --timeout=3s \
            CMD curl -f http://localhost:8080/actuator/health || exit 1

EXPOSE 8080

CMD ["java", "-Duser.timezone=UTC -Dfile.encoding=UTF-8","-jar", "DummyApp.jar"]