# Databases

links-api uses [Exposed](https://github.com/JetBrains/Exposed) for database support.

Only the H2 databases (MariaDB, MySQL, Postgres, SQLite) are supported.

## Environment variables

You can choose which database to use by providing the following environment variables:

| Parameter | Default Value            | Description                                   |
|-----------|--------------------------|-----------------------------------------------|
| DB_TYPE   | MEMORY                   | What type of database to connect to           |
| DB_HOST   | localhost                | Database host/server name or IP               |
| DB_PORT   | Depends on database type | Port through which to connect to the database |
| DB_NAME   |                          | Name of the database to connect to            |
| DB_USER   | root                     | User as whom to connect to the database       |
| DB_PASS   |                          | Password for the given database user          |
| DB_FILE   |                          | Location of the database file                 |

## Database types

The DB_TYPE environment variable can be any of the below:

| Database type | Value    |
|---------------|----------|
| RAM/Memory    | MEMORY   |
| MariaDB       | MARIADB  |
| MySQL         | MYSQL    |
| Postgres      | POSTGRES |
| SQLite        | SQLITE   |

eg. DB_TYPE=MARIADB

## Required arguments

The required environment variables vary depending on the type of database you're trying to connect to.

- &check; Required
- &dash; Unsupported/Not required

* Note that some required parameters have default values. See the tables above

| Argument | Memory | MariaDB | MySQL   | Postgres | SQLite  |
|----------|--------|---------|---------|----------|---------|
| DB_HOST  | &dash; | &check; | &check; | &check;  | &dash;  |
| DB_PORT  | &dash; | &check; | &check; | &check;  | &dash;  |
| DB_NAME  | &dash; | &check; | &check; | &check;  | &dash;  |
| DB_USER  | &dash; | &check; | &check; | &check;  | &dash;  |
| DB_PASS  | &dash; | &check; | &check; | &check;  | &dash;  |
| DB_FILE  | &dash; | &dash;  | &dash;  | &dash;   | &check; |

## Example

### Minimal example

```Bash
docker run --rm \
 -p 8080:8080 \
 -e DB_TYPE=MYSQL \
 -e DB_NAME=testdb \
 mcarr823/links-api
```

The above example connects to a MySQL database called "testdb".

The host and port aren't specified, so it connects to the default host "localhost" and default mysql port 3306.

Likewise, the user and password aren't specified, so the default user "root" is used with an empty password.

### Full example

```Bash
docker run --rm \
 -p 8080:8080 \
 -e DB_TYPE=MARIADB \
 -e DB_HOST=192.168.1.170 \
 -e DB_PORT=3307 \
 -e DB_NAME=testdb \
 -e DB_USER=myuser \
 -e DB_PASS=mypass \
 mcarr823/links-api
```

The above example connects to a MariaDB database at IP address 192.168.1.170 on the non-standard port of 3307.

The database name is "testdb", the user is "myuser", and their password is "mypass".

### SQLite example

```Bash
docker run --rm \
 -p 8080:8080 \
 -e DB_TYPE=SQLITE \
 -e DB_FILE=/app/data.db \
 -v ./links.db:/app/data.db \
 mcarr823/links-api
```

SQLite is different than the other database formats. It only requires a single argument: the location of the SQLite database file.

In the above example, local file "links.db" from the host system is mounted to "/app/data.db" in the container.

DB_FILE then specifies to use that file at the mounted location of "/app/data.db" as the database. 
