init-dependencies:
	docker-compose up -d

run:
	mvn spring-boot:run
	
test:
	mvn test