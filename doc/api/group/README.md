# Link Groups

CRUD operations for [link groups](../../data/classes/group/README.md).

## Create

### Request

```cURL
curl -X PUT \
    -H "Content-Type: application/json" \
    -d '{"name":"test"}' \
    http://127.0.0.1:8080/group
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
    -d '{"id":1}' \
    http://127.0.0.1:8080/group
```

### Success

```JSON
{
  "linkGroup":{
    "id":1,
    "name":"test",
    "links":[]
  }
}
```

### Failure

```JSON
{
  "error": "Link group not found"
}
```

## Update

### Request

```cURL
curl -X PATCH \
    -H "Content-Type: application/json" \
    -d '{"linkGroup":{"id":1,"name":"test2","links":[]}}' \
    http://127.0.0.1:8080/group
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
    -d '{"id":1}' \
    http://127.0.0.1:8080/group
```

### Response

```JSON
{
  "success":true
}
```
