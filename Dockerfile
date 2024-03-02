FROM openjdk:18-jdk-alpine as builder

WORKDIR /app/usermanagement_ms

#COPIAMOS EL PROYECTO AL WORKDIR
COPY . .

#COPIAMOS EL .mvn QUE CONTIENE LE WRAPPER DE MVN
COPY ./.mvn ./.mvn 
#COPIAMOS EL EJECUTABLE
COPY ./mvnw .

COPY ./pom.xml .

# -Dmaven.test.skip NO COMPILA NI EL TEST NI LO EJECUTA
# -Dmaven.main.skip NO COMPILA NI EJECUTA NADA DEL MAIN
# -Dspring-boot.repackage.skip EMPAQUETAR EL PROYECTO SIN CODIGO FUENTE Y SIN TEST
# rm -r ./target BORRAMOS LA CARPETA TARGET
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

#COPIAMOS EL CODIGO FUENTE
#SI HAY UN CAMBIO, SOLO CAMBIA ESTO, LO DE ARRIBA ESTA GUARDADO EN CACHÉ
COPY ./src ./src

#EL RUN SE EJECUTA CUANDO SE CONSTRUYE LA IMAGEN
RUN ./mvnw clean package

FROM openjdk:18-jdk-alpine

WORKDIR /app
COPY  --from=builder /app/usermanagement_ms/target/user_management_ms-0.0.1-SNAPSHOT.jar .
EXPOSE 8001

ENTRYPOINT ["java","-jar","./user_management_ms-0.0.1-SNAPSHOT.jar"]