FROM openjdk:8-alpine AS builder

WORKDIR /example-app

ADD gradle gradle
ADD example-app/build.gradle gradlew ./

RUN ./gradlew help > /dev/null

ADD example-app/src src

RUN ./gradlew jar --no-daemon

# -------------------
FROM flink:alpine

ENV JOB_MANAGER_HOST=flink-jobmanager
ENV JOB_MANAGER_PORT=6123

COPY --from=builder /example-app/build/libs/example-app-1.0-SNAPSHOT.jar app.jar

CMD flink run -m $JOB_MANAGER_HOST:$JOB_MANAGER_PORT -c nl.sanderp.flink.MainKt app.jar