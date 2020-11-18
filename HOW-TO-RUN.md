# How to Run

This is a step-by-step guide how to run the example:

## Installation

* The example is implemented in Java. See
   https://www.java.com/en/download/help/download_options.xml . The
   examples need to be compiled so you need to install a JDK (Java
   Development Kit). A JRE (Java Runtime Environment) is not
   sufficient. After the installation you should be able to execute
   `java` and `javac` on the command line.

* The example run in Docker Containers. You need to install Docker
  Community Edition, see https://www.docker.com/community-edition/
  . You should be able to run `docker` after the installation.

* The example need a lot of RAM. You should configure Docker to use 4
  GB of RAM. Otherwise Docker containers might be killed due to lack
  of RAM. On Windows and macOS you can find the RAM setting in the
  Docker application under Preferences/ Advanced.
  
* After installing Docker you should also be able to run
  `docker-compose`. If this is not possible, you might need to install
  it separately. See https://docs.docker.com/compose/install/ .

## Build
### Services
Change to the directory `microservice` and run `./mvnw clean
package` or `./mvnw.cmd clean package` (Windows). This will take a while:

```
[~/microservice/microservice-demo]./mvnw clean package
```

If this does not work:

* Ensure that `settings.xml` in the directory `.m2` in your home
directory contains no configuration for a specific Maven repo. If in
doubt: delete the file.

* The tests use some ports on the local machine. Make sure that no
server runs in the background.

* Skip the tests: `./mvnw clean package
  -Dmaven.test.skip=true` or `./mvnw.cmd clean package
  -Dmaven.test.skip=true` (Windows).

* In rare cases dependencies might not be downloaded correctly. In
  that case: Remove the directory `repository` in the directory `.m2`
  in your home directory. Note that this means all dependencies will
  be downloaded again.

### Client
Create file config firebase to build mobile app.
1. Generating Android credentials
Download the `google-services.json` file and place it inside of your project 
at the following location: `client/mobile/chatapp/android/app/google-services.json`.
2. Generating iOS credentials
Download the `GoogleService-Info.plist` file and place it inside of your project
at the following location: `client/mobile/chatapp/ios/GoogleService-Info.plist`

```sh
# build client web
cd client/web/chatapp && yarn && yarn build
# build client mobile
cd client/mobile/chatapp && yarn && cd android && gradlew assembleRelease
```
## Prepare config folder
If you want to run configuration server with local folder `config`. 
You must init git in folder `config` by below commands:
```sh
cd config
git init
git commit -m "init"
```
## Run the containers

First you need to build the Docker images. Change to the directory
`docker` and run `docker-compose build`. This will download some base
images, install software into Docker images and will therefore take
its time:

```
[~/microservice/docker]docker-compose build 
```

Afterwards the Docker images should have been created. They have the prefix
`chatapp`:

```
[~/microservice/docker]docker images
```

Now you can start the containers using `docker-compose up -d`. The
`-d` option means that the containers will be started in the
background and won't output their stdout to the command line:

```
[~/microservice/docker]docker-compose up -d
```

Check wether all containers are running:

```
[~/microservice/docker]docker ps
```
`docker ps -a`  also shows the terminated Docker containers. That is
useful to see Docker containers that crashed rigth after they started.

If one of the containers is not running, you can look at its logs using
e.g.  `docker logs chatapp_security_1`. The name of the container is
given in the last column of the output of `docker ps`. Looking at the
logs even works after the container has been
terminated. If the log says that the container has been `killed`, you
need to increase the RAM assigned to Docker to e.g. 4GB. On Windows
and macOS you can find the RAM setting in the Docker application under
Preferences/ Advanced.
  
If you need to do more trouble shooting open a shell in the container
using e.g. `docker exec -it chatapp_security_1 /bin/sh` or execute
command using `docker exec chatapp_security_1 /bin/ls`.

If you want to run one or multiple service, you can use bash scripts in folder `scripts`. 
Following instruction to execute. Input service index need to build (example `0 1 2 3`) 
and `y` to rebuild service and one more times `y` to build, start docker.
When build service by this way, auto call `scripts/wait-for-services.sh` 
to wait service start successfully.
```sh
cd scripts && ./rebuildServices.sh
```

You can access:

* The application through Zuul at http://localhost:8080/
* The Eureka dashboard at http://localhost:8761/
* The Hystrix dashboard at http://localhost:8989/
* The Chat App client web at http://localhost:8000/
* Swagger UI at http://localhost:8080/security/swagger-ui/

Mange resources:

* Adminer for Mysql data at http://localhost:6060/
* Redis commander at http://localhost:7070/
* RabbitMQ Management at http://localhost:15672/

You can terminate all containers using `docker-compose down`.