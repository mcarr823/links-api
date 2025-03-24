# Health

The health check endpoint does not expect any arguments.

Simply POST an empty payload to the /health endpoint.

If you receive a HTTP 200 status code and a JSON body, then the request was successful.

If you receive anything else (a HTTP 4xx or 5xx status code), then the request was unsuccessful.

* Proper health checks, such as database access checks, have not yet been implemented.

## POST /health

### Request

```cURL
curl -X POST \
    -H "Content-Type: application/json" \
    http://127.0.0.1:8080/health
```

### Success

```JSON
{
  "success":true
}
```

### Error

```Bash
curl: (7) Failed to connect to 127.0.0.1 port 8080 after 0 ms: Could not connect to server
```