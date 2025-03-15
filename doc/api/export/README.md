# Export

Links supports two different export formats:
- Links JSON (eg. to backup your Links data), or
- Onetab TXT (for importing into Onetab)

In both cases, command type is POST, the endpoint is /export, and the JSON payload formatted as below:

| Argument | Value                                       |
|----------|---------------------------------------------|
| format   | LINKS or ONETAB                             |

Pseudo example:

| Type   | Data                        |
|--------|-----------------------------|
| Input  | {"format":"FORMAT"}         |
| Output | {"data":"my exported data"} |

Proper examples below.

## Links JSON

### Request

```cURL
curl -X POST \
    -H "Content-Type: application/json" \
    -d '{"format":"LINKS"}' \
    http://127.0.0.1:8080/export
```

### Response

```JSON
{
  "data":"{\"groups\":\"[{\"id\":1,\"name\":\"test\",\"links\":[]}]\"}"
}
```

## Onetab TXT

### Request

```cURL
curl -X POST \
    -H "Content-Type: application/json" \
    -d '{"format":"ONETAB"}' \
    http://127.0.0.1:8080/export
```

### Response

```JSON
{
  "data":"https://github.com | Github\nhttps://bitbucket.com | Bitbucket\n\nhttps://lemmy.world | Lemmy"
}
```
