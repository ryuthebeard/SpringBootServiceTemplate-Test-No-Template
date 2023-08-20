# Spring Boot Template Guide
This Spring Boot Template provides a [Spring Boot API](#libraries-in-use) template and CI/CD Pipeline which does the following;

- Spring Boot Unit/Integration Tests using [Maven test runner](#running-tests)
- SonarQube Static Code Analysis Test using [SonarCloud](https://sonarcloud.io/)
- Docker Vulnerability Checks using GCP [Container Analysis](https://cloud.google.com/container-analysis/docs)
- Deploy to Respective Environment using [Cloud Deploy](https://cloud.google.com/container-analysis/docs)

Please note that following 
* CI/CD is designed to work using [Git Flow Branching method](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow). See [Developing and Releasing to Production using Git Flow](https://www.notion.so/shara-inc/Developing-and-Releasing-to-Production-using-Git-Flow-d7d6d64b224f4248988a4a2a26b74dc0)
* Commit messages must follow [Conventional Commits specifications](https://www.conventionalcommits.org/en/v1.0.0/). We have git hooks for running these checks.
* The `develop` is the main branch by default. This allows us to set up deployment into the development environment first and then production later. 
* Deployment to production will be via the `master` branch. 


## Known Issues
1. Due to [GCP Cloud Build limitation](https://stackoverflow.com/questions/69378289/how-to-fully-automate-the-google-cloud-build-trigger-creation) there is a manual step to connect Cloud Build to GitHub Repository. The first attempt to create the Cloud Build trigger will fail with the following error `ERROR: (gcloud.beta.builds.triggers.create.github) FAILED_PRECONDITION: Repository mapping does not exist. Please visit https://console.cloud.google.com/cloud-build/triggers/connect to connect a repository to your project`. To resolve this for reach repo please reach out to the Cloud Infrastructure and DevSecOps team.
2. By default, ingress is disabled and you will need to Cloud Infrastructure and DevSecOps team to enable it.  
3. Multi-Cluster Ingress not currently functioning as desired. We are currently using NGINX Ingress and thus deployment would be to only one cluster
4. Deployment into staging & production is currently WIP.
5. You need to manually run the following command to update the hooks path config for git hooks to work
   ```shell
   git config --local core.hooksPath .githooks/
   ```

# Table of Content
<!-- TOC -->
**[Folder Structure](#folder-structure)**<br>
**[Libraries in Use](#libraries-in-use)**<br>
**[API Versioning](#api-versioning)**<br>
**[Controller layer validations](#controller-layer-validations)**<br>
**[Liquibase](#liquibase)**<br>
**[H2H Database](#h2h-database)**<br>
**[Code Coverage](#code-coverage)**<br>
**[Linting](#linting)**<br>
**[Sonarqube Integration](#sonarqube-integration)**<br>
**[Running tests](#running-tests)**<br>
**[Comments](#comments)**<br>
**[Packaging and Versioning (CI/CD Pipeline)](#packaging-and-versioning--cicd-pipeline-)**<br>
**[How to run](#how-to-run)**<br>
**[Access](#access)**<br>
**[Swagger](#swagger)**<br>
<!-- TOC -->

### Dependencies

* JAVA 17
* Postgres DB Access
* Apache Kafka Access

### Folder Structure

src/main/java
src/test/java

### Libraries in Use

|                                          |
|------------------------------------------|
| Spring Starter Web                       |
| Spring Data JPA                          |
| Postgres Connector                       |
| Spring Actuator                          |   
| [Liquibase](https://www.liquibase.org/)  |    
| Spring Kafka                             |
| [Spring boot Doc](https://springdoc.org/) |
| H2 Database                              |
| Lombok                                   |
| spring-boot-starter-validation           |
| Redis                                    |


### API Versioning

To enable API Versioning each the RequestMapping should have the format: **_RequestMapping("
/api/API_VERSION/DOMAIN")_** e.g. _/api/v1/notification_

#### Controller layer validations

Using _spring-boot-starter-validation_, the application is able to catch validation errors on the
Web/ Controller layer.
Add Validation annotations(_@NotNull_, _@NotBlank_) to the DTO then add _@Valid_ annotation
controller method.

### Liquibase

To rollback run:

``` mvn liquibase:rollback -Dliquibase.rollbackCount=1 ```

Where 1 is the number of previous migrations to rollback

### Caching
Redis is used for caching in the repo layer. This has been enabled by adding
spring.cache.type=redis in application.properties and adding the annotation @EnableCaching 
in the Main class.

#### H2H Database

Embedded database to test repository layer

### Code Coverage

We are using jacoco plugin to enforce minimum code coverage(Currently set to a minimum 70%).

You can view test coverage html report from: `target/site/index.html`

### Linting

To ensure standardization in naming and code format we have a check before build: checkstyle

[Google Style guide](https://google.github.io/styleguide/javaguide.html)

To enable formatting in your IDEA import google-style-intelij.xml(for intelij).

In Intellij, go Under Settings(command + ,) -> Editor -> Code Style. In Scheme settings (settings icon on right side) -> import schemes-> intellij idea code style xml. Select the xml in the root folder of this project(google-style-intelij.xml). Then in Scheme dropdown select the GoogleStyle IDE (newly added style). Click on apply and close.

### Sonarqube Integration

```
mvn clean verify sonar:sonar -Dsonar.host.url=URL -Dsonar.login=TOKEN
```

### Running tests

```
./mvnw clean verify
```

### Comments

To enable automated semantic versioning, code comments should
follow [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/#specification).

```aidl
<type>[(optional <scope>)]: <description>

[optional <body>]

[optional <footer(s)>]
```

Where Type can be: build, chore, ci, docs, feat, fix, perf, refactor, revert, style, test
<scope> is the module or package the change has affected e.g Users, pom, notification

fix type leads to a bump in the patch version
e.g.

```aidl
git commit -m 'fix(users): Checking for null pointer before accessing the list object'
```

feat type leads to a bump in the minor version
e.g.

```aidl
git commit -m 'feat(notification): Query notification using message id'
```

A type that has (!) after leads to a bump in the major version.
e.g

```aidl
git commit -m 'feat(users)!: removing support for v1 apis'
```

### Packaging and Versioning (CI/CD Pipeline)

The command below will be able to update pom.xml version depending on the commit messages

```

./mvnw  conventional-commits:version --batch-mode release:clean release:prepare release:perform -Dgoals=verify -Darguments="-Dmaven.javadoc.skip=true -Dmaven.test.skipTests=true -Dmaven.test.skip=true"

```

### How to run

```
./mvnw spring-boot:run

```

### Access

[Default](http://localhost:8080)

### Swagger

[swagger-ui](http://localhost:8080/swagger-ui/index.html#/)
