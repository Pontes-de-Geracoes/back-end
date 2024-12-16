FROM openjdk:17.0.1-jdk-oracle as build

WORKDIR /workspace/app

# Copy Maven files first for better layer caching
COPY api/pom.xml .
COPY api/.mvn/ .mvn/
COPY api/mvnw .

# Copy source code
COPY api/src/ src/

RUN chmod -R 777 ./mvnw

RUN ./mvnw install -DskipTests

RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:17.0.1-jdk-oracle

VOLUME /tmp

ARG DEPENDENCY=/workspace/app/target/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.pontedegeracoes.api.ApiApplication"]