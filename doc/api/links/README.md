# Links

CRUD operations for [individual links](../../data/classes/link/README.md).

## Create

### Request

```cURL
curl -X PUT \
    -H "Content-Type: application/json" \
    -d '{"groupId":1,"link":{"name":"link1","url":"http://localhost","favicon":""}}' \
    http://127.0.0.1:8080/link
```

### Response

```JSON
{
  "id":1
}
```

## Read

### Request

```cURL
curl -X POST \
    -H "Content-Type: application/json" \
    -d '{"linkId":1}' \
    http://127.0.0.1:8080/link
```

### Success

```JSON
{
  "id":1,
  "link":{
    "name":"link1",
    "url":"http://localhost",
    "favicon":""
  }
}
```

### Failure

```JSON
{
  "error":"Link not found"
}
```

## Update

### Request

```cURL
curl -X PATCH \
    -H "Content-Type: application/json" \
    -d '{"linkId":1,"link":{"name":"link1","url":"http://localhost","favicon":""}}' \
    http://127.0.0.1:8080/link
```

### Response

```JSON
{
  "success":true
}
```

## Delete

### Request

```cURL
curl -X DELETE \
    -H "Content-Type: application/json" \
    -d '{"linkId":1}' \
    http://127.0.0.1:8080/link
```

### Response

```JSON
{
  "success":true
}
```
