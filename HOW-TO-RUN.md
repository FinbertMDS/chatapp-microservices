# How to Run

This is a step-by-step guide how to run the example:

## Installation

* The example is implemented in Java. See
   https://www.java.com/en/download/help/download_options.xml . The
   examples need to be compiled so you need to install a JDK (Java
   Development Kit). A JRE (Java Runtime Environment) is not
   sufficient. After the installation you should be able to execute
   `java` and `javac` on the command line.

Recommend: Java version 17

* The client is implemented in ReactJS and ReactNative. See 
  https://nodejs.org/en to download and install NodeJS, and 
  https://yarnpkg.com/getting-started/install to install Yarn .
  After the installation you should be able to execute
   `node -v` and `yarn -v` on the command line.

Recommend: Node version 20, Yarn version 1.22.22

* The example run in Docker Containers. You need to install Docker, see https://www.docker.com/
  . You should be able to run `docker` after the installation.

* The example need a lot of RAM. You should configure Docker to use 12
  GB of RAM. Otherwise Docker containers might be killed due to lack
  of RAM. On Windows and macOS you can find the RAM setting in the
  Docker application under Preferences/ Advanced.

## Build
### Prepare client credential files
Create file config firebase to build mobile app.
1. Generating Android credentials
Download the `google-services.json` file and place it inside of your project 
at the following location: `client/mobile/chatapp/android/app/google-services.json`.
2. Generating iOS credentials
Download the `GoogleService-Info.plist` file and place it inside of your project
at the following location: `client/mobile/chatapp/ios/GoogleService-Info.plist`

## Run the containers
You can use bash scripts in folder `scripts`. 
Following instruction to execute. Input `y` to rebuild service and one more times `y` to start docker.
When build service by this way, auto call `scripts/wait-for-services.sh` 
to wait service start successfully.
```sh
[~/scripts]./rebuildServices.sh
```

You can access:

* The application through Api gateway at http://localhost:8080/
* The Eureka dashboard at http://localhost:8761/
* The Config at http://localhost:9191/service-name/default
* The Chat App client web at http://localhost:8000/

* Adminer for Mysql data at http://localhost:6060/
* RabbitMQ Management at http://localhost:15672/

## Clean up
```sh
[~/scripts]./stopDocker.sh
```