# Link Groups (all)

Operations which can be performed on all link groups simultaneously.

## Read

### Request

```cURL
curl -X POST \
    -H "Content-Type: application/json" \
    http://127.0.0.1:8080/group/all
```

### Response

```JSON
{
  "results":[
    {
      "id":1,
      "name":"test2",
      "links":[]
    }
  ]
}
```

## Delete

### Request

```cURL
curl -X DELETE \
    -H "Content-Type: application/json" \
    http://127.0.0.1:8080/group/all
```

### Response

```JSON
{
  "success":true
}
```
