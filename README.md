# bpiatek task solution with docker and docker compose

### Copy project to your local machine
```shell
# go to desired location and run
git clone https://github.com/bpiatek/exerciseapp.git
```
##### Running with docker-compose from terminal
```shell
# enter main application folder
cd exerciseapp
# build with maven (has to be version 3.8.1 or higher)
mvn package
# run detached
docker-compose up --build -d
# to stop 
docker-compose stop
```
Application together with PosgreSQL database will start.  
Open browser and go to:
```shell
http://localhost:8080/swagger-ui.html
```
There is a simple interactive documentation to try out the app.
