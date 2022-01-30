O desenvolvimento foi realizado utilizando:
<br/>
Spring Tool Suite 4
<br/>
Spring boot 2.6.3
<br/>
Java 11
<br/>
Maven
<br/>
Spring Data JPA
<br/>
H2 Database
<br/>
Swagger
<br/>
Junit (testes integrados)
<br/>
Mockito (testes unitarios)
<br/>

<br/>
Para o caso de realizar testes pelo Postman está disponível um export do arquivo que utilizei (Case Itau.postmanv2.1.0_collection.json) na pasta src\main\resources. Caso deseje utiliza-lo faça o import no postman local. 
<br/>

<br/><br/>
Clonar o projeto do GITHUB
<br/>
Importar para o "Spring Tool Suite" como projeto Maven
<br/>
Realizar start da aplicação. Está configurado para rodar na porta 8080.
<br/>
Para acessar o swagger basta acessar a url: http://localhost:8080/swagger-ui.html
usuário: transfercash_user
senha: Caseit@u
<br/>

Para acessar o banco de dados basta acessar a url: http://localhost:8080/h2-console
<br/>
<br/>
Os endpoints da API de transferencia com exemplo do json de entrada a serem enviados na request são:
<br/>
http://localhost:8080/transfer/v1/createcustomeraccount (Cadastro de um cliente)
<br/>
{
<br/>
	"customer": {
<br/>
		"idCustomer":1,
<br/>
		"name":"Givanildo Dias Mendes"
<br/>
	},
<br/>
	"idCustomerAccountBank": 1,
<br/>
	"balance": 40
<br/>
}
<br/>
<br/>

http://localhost:8080/transfer/v1/listallcustomeraccount (Listar clientes - Não há json de entrada)
<br/>
<br/>
http://localhost:8080/transfer/v1/customeraccountbyid (Buscar cliente pelo numero da conta)
<br/>
{
<br/>
	"idCustomerAccountBank": 1
<br/>
}
<br/>
<br/>	
http://localhost:8080/transfer/v1/transfercash (Realizar transferência com controle de concorrencia)
<br/>
{
<br/>
	"valueTransferCash":20,
<br/>
	"customerAccountBankOri":{
<br/>
		"customer":{
<br/>
			"idCustomer":1
<br/>
		},
<br/>
		"idCustomerAccountBank":1
<br/>
	},
<br/>
	"customerAccountBankDest":{
<br/>
		"customer":{
<br/>
			"idCustomer":2
<br/>
		},
<br/>
		"idCustomerAccountBank":2
<br/>
	}
<br/>
}
<br/>
		
<br/>
http://localhost:8080/transfer/v1/listalltransactions (Listar Trasferências OK/KO ordenado por data decrescente)
<br/>
<br/>
TESTES
<br/>
Os testes unitários do mockito pode ser realizados a qualquer momento com base de dados vazia ou não, pois é o conceito do mock que garante os testes.
<br/>
Para rodar os testes integrados do junit basta executa-lo quando a base de dados estiver completamente vazia. 
<br/>

	
-----------------------------------------------------------------------------------------------------------
<br/>
<br/>
Caso deseje rodar com docker basta acessar o diretório raiz do projeto onde se encontra o arquivo Dockerfile e seguir os seguintes passos.
<br/>
# 1 - Dentro da pasta raiz do projeto(local onde está o Dockerfile) abrir o "CMD/powershel/bash" e rodar o comando abaixo para gerar a imagem docker localmente
<br/>
docker build -t bank/transfercash-spring-boot-docker .
<br/>
<br/>
# 2 - Criar a rede do docker. Como não utilizei o dockerswarm(cluster) pode ser uma rede do tipo bridge. Somente para não utilizar a default.
<br/>
docker network create --driver bridge my-network
<br/>
<br/>
# 3 - Rodar o seu programa no container desatachando o terminal 
<br/>
docker run -d -p 8080:8080 --name transfercash --network my-network bank/transfercash-spring-boot-docker 
<br/>
Após isso é só acessar os endpoints.
