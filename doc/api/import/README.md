# Import

Links supports two different import formats:
- JSON exported from Links itself, or
- TXT export from Onetab

In both cases, command type is PUT, the endpoint is /import, and the JSON payload formatted as below:

| Argument | Value                                       |
|----------|---------------------------------------------|
| format   | LINKS or ONETAB                             |
| text     | encoded JSON (LINKS) or plain text (ONETAB) |

Pseudo example:

| Type   | Data                                      |
|--------|-------------------------------------------|
| Input  | {"format":"FORMAT", "text":"import data"} |
| Output | {"ids":[1,2,3]}                           |

Proper examples below.

## Links JSON

### Request

```cURL
curl -X PUT \
    -H "Content-Type: application/json" \
    -d '{"format":"LINKS","text":"{\"groups\":\"[{\"id\":1,\"name\":\"test\",\"links\":[]}]\"}"}' \
    http://127.0.0.1:8080/import
```

### Response

```JSON
{
  "ids":[2]
}
```

* Note that the IDs in the input data are required, but will be ignored by the import.

## Onetab TXT

### Request

```cURL
curl -X PUT \
    -H "Content-Type: application/json" \
    -d '{"format":"ONETAB","text":"https://github.com | Github
https://bitbucket.com | Bitbucket

https://lemmy.world | Lemmy"}' \
    http://127.0.0.1:8080/import
```

### Response

```JSON
{
  "ids":[1,2]
}
```

* Be mindful of the spacing with onetab imports. Links expects the data to be formatted exactly the same way as onetab exports it, with the blank lines and such preserved. 
