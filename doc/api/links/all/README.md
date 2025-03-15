# Links (all)

Operations which can be performed on all links simultaneously.

## Read

### Request

```cURL
curl -X POST \
    -H "Content-Type: application/json" \
    http://127.0.0.1:8080/link/all
```

### Response

```JSON
{
  "links":[
    {
      "name":"link1",
      "url":"http://localhost",
      "favicon":""
    }
  ]
}
```

## Delete

### Request

```cURL
curl -X DELETE \
    -H "Content-Type: application/json" \
    http://127.0.0.1:8080/link/all
```

### Response

```JSON
{
  "success":true
}
```
