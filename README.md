# links-api

links-api is the API interface for performing CRUD operations on the Links database.

API requests are performed via HTTP(S) requests to a REST interface.

## Documentation

- [API Spec](doc/api/README.md)
- [Classes/Terms](doc/classes/README.md)
- [Databases](doc/databases/README.md)

## Docker

### Compiling

```Bash
docker buildx build . -t mcarr823/links-api:latest
```

### Running

```Bash
docker run --rm -p 8080:8080 -e DB_TYPE=CHANGEME mcarr823/links-api
```

* The above command hasn't specified a database and won't run as-is. See the Databases section of the documentation for a proper setup.
