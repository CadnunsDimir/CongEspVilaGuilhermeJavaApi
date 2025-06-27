# Estágio de Build: Compila a aplicação Quarkus
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copia o arquivo pom.xml e faz o download das dependências
# Isso aproveita o cache do Docker para dependências que não mudam frequentemente
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o código-fonte da aplicação
COPY src ./src

# Empacota a aplicação Quarkus
RUN mvn package -DskipTests

# Estágio Final: Cria a imagem de runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o diretório 'lib' com as dependências
COPY --from=build /app/target/quarkus-app .

COPY --from=build /app/target/quarkus-app/*-run.jar app.jar

# Expõe a porta que a aplicação Quarkus usa (padrão é 8080)
EXPOSE 8080

# Define o comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar", "-Dquarkus.http.host=0.0.0.0"]