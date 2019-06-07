# Ame Digital Challenge



This project was made with Java, spring boot, MongoDb, docker-compose



To run this project for this first time, you must run on terminal:

```
make init-dependencies
```

To run the API, you must execute:

```
make run
```

Run the command below to run the tests

```
make test
```


To create planets, you must execute:


```
curl -X POST 'http://localhost:8081/v1/planets' -d '{"name": "Alderaan", "climate": "temperate", "terrain": "grasslands, mountains", "externalId": 2}' -H 'Content-type: application/json'

curl -X POST 'http://localhost:8081/v1/planets' -d '{"name": "Yavin IV", "climate": "temperate, tropical", "terrain": "jungle, rainforests", "externalId": 3}' -H 'Content-type: application/json'

curl -X POST 'http://localhost:8081/v1/planets' -d '{"name": "Hoth", "climate": "frozen", "terrain": "tundra, ice caves, mountain ranges", "externalId": 4}' -H 'Content-type: application/json'

curl -X POST 'http://localhost:8081/v1/planets' -d '{"name": "Dagobah", "climate": "murky", "terrain": "swamp, jungles", "externalId": 5}' -H 'Content-type: application/json'

curl -X POST 'http://localhost:8081/v1/planets' -d '{"name": "Bespin", "climate": "temperate", "terrain": "gas giant", "externalId": 6}' -H 'Content-type: application/json'
```


Find all:

```
curl 'http://localhost:8081/v1/planets' 
```



Search by name:

```
curl 'http://localhost:8081/v1/planets?name=Hoth'
```



Find by id:

```
curl -v 'http://localhost:8081/v1/planets/{PUT_ID_HERE}'
```


Delete by id:

```
curl -X DELETE 'http://localhost:8081/v1/planets/{PUT_ID_HERE}'
```
