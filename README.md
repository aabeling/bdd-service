Rest service to edit jbehave stories and to execute them


     $ curl http://localhost:8080/steps
     $ curl -X POST -H "Content-Type: application/json" -d @src/test/resources/requests/example1.json http://localhost:8080/stories
     $ curl -X POST -H "Content-Type: application/json" -d @src/test/resources/requests/example2.json http://localhost:8080/stories
     
Todos: 
* cleanup configuration and stepsFactory
* handle long running tests

