# How to
### Prerequisites
docker-compose and docker installed
#### Build, run unit tests, and run with Gradle
```shell
./gradlew clean build bootRun
```  

#### View automatically created swagger documentation 
Open http://localhost:8080/swagger-ui.html  
Here you can add add nodes and get aggregated node alarms.

#### Build alarmservice base docker image
```shell
$ docker-compose build alarmservice 
``` 
docker-compose will by default use docker-compose.yml file  

#### Run local docker registry
```shell
$ docker run -d -p 5000:5000 --name registry registry:2
```

#### Push alarmservice docker image local docker registry
```shell
$ docker-compose push alarmservice
```
#### Run alarmservice in docker container
```shell
$ docker-compose run alarmservice
```
#### Automated functional acceptance tests
See https://atlas.transmode.se/bitbucket/users/pabe/repos/alarmservice-acceptance-test/browse

NOTE:   
For some reason I can't reach application on 'http://localhost:8080/swagger-ui.html'
 when run as docker container?
I have exposed port 8080 and mapped it. Needs research.

It is reachable on container ip though, http://container-ip:8080/swagger-ui.html