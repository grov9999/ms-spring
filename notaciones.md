## Dockerfile

## EJECUTAR
```shell
docker build -t mi-aplicacion-springboot .
docker run --name mi-contenedor-springboot mi-aplicacion-springboot
docker-compose up -d mi-contenedor-springboot
```
```shell
mvn clean install package -DskipTests
```
### V1
```Dockerfile
FROM eclipse-temurin:17-jdk as builder
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests


FROM eclipse-temurin:17-jre
WORKDIR /application
ARG JAR_FILE=target/*.jar
COPY --from=builder /app/${JAR_FILE} ms_product.jar
ENTRYPOINT ["java", "-jar", "ms_product.jar"]
```


### V2
```Dockerfile
FROM eclipse-temurin:17-jre
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ms_product.jar
ENTRYPOINT ["java", "-jar", "ms_product.jar"]
```

### V3
```Dockerfile
FROM eclipse-temurin:17-jdk-alpine
# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app
# Crea un usuario no root para ejecutar la aplicación
RUN addgroup -S spring && adduser -S spring -G spring
# Copia el archivo JAR generado por Spring Boot al contenedor
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
# Cambia los permisos del archivo JAR para que solo el usuario creado pueda acceder
RUN chown spring:spring app.jar && chmod 500 app.jar
# Cambia al usuario no root
USER spring
# Define un volumen temporal (opcional, útil para archivos temporales en Spring Boot)
VOLUME /tmp
# Variable de entorno para pasar opciones de la JVM
ENV JAVA_OPTS=""
# Comando para ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar"]
```

```url
\\wsl.localhost\docker-desktop\tmp\docker-desktop-root\var\lib\docker
```