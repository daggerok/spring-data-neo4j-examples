# Neo4j REST repository

```bash
docker run --rm --name neo4j -d -p7474:7474 -p7687:7687 -eNEO4J_AUTH=neo4j/s3cr3t neo4j:4.3.6-community
./mvnw -f data-rest clean compile spring-boot:start

http --ignore-stdin get :8001/
http --ignore-stdin post :8001/author firstName=Maksim lastName=Kostromin
http --ignore-stdin get :8001/author
http --ignore-stdin get ':8001/author/search/findAllByLastNameContainsIgnoreCaseOrderByAt?name=a'
http --ignore-stdin get ':8001/author/search/findAllByLastNameContainsIgnoreCaseOrderByAt?name=kos'

./mvnw -f data-rest spring-boot:stop
docker stop neo4j
```
