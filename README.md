### Dependências
[**Kotlin**](https://kotlinlang.org/) -
[**Spring**](https://spring.io/) -
[**Java 17**](https://jdk.java.net/17/) -
[**Postgres**](https://www.postgresql.org/) -
[**Docker**](https://www.docker.com/) -
[**Maven**](https://maven.apache.org/)


### Instruções sobre como compilar e executar o projeto
1. Projeto foi construído com Kotlin + spring + maven, para compilar execute o seguinte comando:
```
$ mvn clean install
```

2. A Aplicação depende de um banco de dados, postgres, para isso inicie os serviços contidos no *docker-compose*
   Logo devemos executar o comando:
```
$ docker-compose up
```
O banco de dados postgres sera iniciado na porta 5432, com os seguintes dados de acesso:

```
url: localhost
database: mydb
user: postgres
password: postgres
```

3. Variáveis de ambiente
```
FEIGN_CLIENT_EXCHANGE_RATE_APIKEY=uoWFi3qvX2LlMKcXyE51npDU46In1AEo
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/mydb?createDatabaseIfNotExist=true
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
```

### Testes
Para executar os testes unitários e integração executar o seguinte comando na raiz do projeto:
```
$ mvn test
```

### CI/CD
[**GitHub Actions**](https://github.com/features/actions) - Integração continua com a branch master

### Serviço de Orquestração
[**AWS Elastic Beanstalk**](https://aws.amazon.com/pt/elasticbeanstalk/)

Load Balancer:
```
http://jayaapp-env.eba-3qy2f6b6.us-east-2.elasticbeanstalk.com
```
API's:
```
POST {BASE_URL}/transaction
Body: {
	"userId": 1,
	"sourceCurrency": "EUR",
	"sourceValue": "10",
	"destinationCurrency": "BRL"
}

Reponse:{
	"transactionId": 1,
	"userId": 1,
	"sourceCurrency": "EUR",
	"sourceValue": 10,
	"destinationCurrency": "BRL",
	"destinationValue": 55.62,
	"conversionRate": 5.56267,
	"createdAt": "2023-03-30T18:20:02.328600935"
}

GET {BASE_URL}/transaction/user/1
Response: [
	{
    	"transactionId": 1,
    	"userId": 1,
    	"sourceCurrency": "EUR",
    	"sourceValue": 10.00,
    	"destinationCurrency": "BRL",
    	"destinationValue": 55.60,
    	"conversionRate": 5.56,
    	"createdAt": "2023-03-30T18:20:02.328601"
	}
]
```

Segurança
```
Tipo: Basic access authentication
user: user
pass: password
```
