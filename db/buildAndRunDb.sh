docker image build -t calls-api-postgres .
docker build -t calls-api-postgres . && docker run -p 5432:5432 --env POSTGRES_PASSWORD=postgres --name calls-api-postgres calls-api-postgres