SICREDI
=======

![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=br.com%3Amavenquickstart&metric=alert_status)
[![GitHub last commit](https://img.shields.io/github/last-commit/google/skia.svg?style=flat)]()

Sistema para o gerenciamento de sessões de votação para uma pauta.
Nesse sistema podemos criar uma pauta que será aberta para votação de seus membros, estes só podem votar uma única vez, Sim ou Não, em cada pauta. Esses votos são computados e exibidos em cada pauta.


----------------------------------------------------------------------------
Começando
---------

Para poder executar esse projeto é preciso instalar os seguintes programas:

* [MAVEN 3.6.3](https://maven.apache.org/download.cgi)
* [JAVA 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [Eclipse-2020-03](https://www.eclipse.org/downloads/) - Você pode baixar qualquer outra IDE
* [Lombok v1.18.12](https://projectlombok.org/) - Para a instalação do lombok tem alguns passos diferentes, esses passos está própria página.

----------------------------------------------------------------------------

Desenvolvimento
---------------

Para dar inicio ao desenvolvimento é necessário baixar/checkout a aplicação:

* [GIT - HTTPS](https://github.com/falcao998/sicredi.git)

Execute o comando :

```
git clone https://github.com/falcao998/sicredi.git
```

Ou você pode baixar o projeto criando um repositório pela IDE que você utiliza.

Caso você importe o projeto fora da sua IDE, você tem que fazer o carregamento do projeto por dentro da IDE, no Eclipse você tem que fazer o import do projeto Maven.


----------------------------------------------------------------------------

Build / Deploy / Publicação
---------------------------

 Para poder fazer o build local é necessário fazer algumas alterações em 1 único arquivo - [application.properties](https://github.com/falcao998/sicredi/blob/master/src/main/resources/application.properties):
 
 Ele se encontra dessa forma:
 
 ```
spring.datasource.url={JDBC_DATASOURCE_URL}
#spring.datasource.url=jdbc:h2:file:/opt/java/sicredi
#pring.datasource.driverClassName=org.h2.Driver
#spring.datasource.username=sa
#spring.datasource.password=sa
#spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
#spring.jpa.hibernate.ddl-auto=create
#spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
 ```
 E deve ficar dessa forma:
 
 ```
#spring.datasource.url={JDBC_DATASOURCE_URL}
spring.datasource.url=jdbc:h2:file:/opt/java/sicredi
pring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=sa
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create
spring.h2.console.enabled=true
 #spring.jpa.hibernate.ddl-auto=update
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
 ```
 
Lembrando que para a configuração que fiz para esse projeto, não se deve commitar esse arquivo. Pois isso quebra as configurações do HEROKU.

Para o build da aplicação é só selecionar o projeto e pedir para rodar como um aplicação.


----------------------------------------------------------------------------

Para rodar os testes você pode rodar pela linha de comando:

```
mvn test
```

Ou ir no projeto e selecionar as classes de teste e roda-las manualmente.