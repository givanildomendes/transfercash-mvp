FROM openjdk:11
ARG JAR_FILE=target/transfercash-mvp-0.0.1.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

# 1 - Dentro da pasta raiz do projeto(local onde está o Dockerfile) abrir o CMD/powershel e rodar o comando abaixo para gerar a imagem docker localmente
##docker build -t bank/transfercash-spring-boot-docker .

# 2 - Criar a rede do docker. Como não utilizei o dockerswarm(cluster) posde ser uma rede do tipo bridge. Somente para não utilizar a default.
##docker network create --driver bridge my-network

# 3 - Rodar o seu programa no container desatachando o terminal 
##docker run -d -p 8080:8080 --name transfercash --network my-network bank/transfercash-spring-boot-docker