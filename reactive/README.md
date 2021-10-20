# Neo4j reactive repository

```bash
docker run --rm --name neo4j -d -p7474:7474 -p7687:7687 -eNEO4J_AUTH=neo4j/s3cr3t neo4j:4.3.6-community
./mvnw -f reactive clean compile spring-boot:start

http --ignore-stdin get :8002/api/v1/author
http --ignore-stdin post :8002/api/v1/author firstName=Maksim lastName=Kostromin
http --ignore-stdin get :8002/api/v1/author
http --ignore-stdin get ':8002/api/v1/author/a'
http --ignore-stdin get ':8002/api/v1/author/kos'

./mvnw -f reactive spring-boot:stop
docker stop neo4j
```
