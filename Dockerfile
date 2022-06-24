FROM maven:3.8.6-openjdk-11-slim@sha256:bed43e35f3e5f013670d4d7d247612de5ac82355b0c58c244005c532dfe6a1d7 AS build
RUN mkdir /project
COPY . /project
WORKDIR /project
RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim@sha256:0d6cfc233809ed5cfa1fca13059ea36af58916a72799fb21c9ecebe7802bbddd
RUN mkdir /app
RUN addgroup --system --gid 1001 appuser && adduser  --system --uid  1001 --group appuser
COPY --from=build /project/target/dummy-app-0.1.0.jar /app/dummy-app-0.1.0.jar
WORKDIR /app
RUN chown -R appuser:appuser /app
USER appuser
EXPOSE 8080
CMD ["java", "-jar", "dummy-app-0.1.0.jar"]