# Link

A link is a named url.

A link contains the following parameters:

| Parameter | Type   | Description                          |
|-----------|--------|--------------------------------------|
| name      | String | Name used to identify the given link |
| url       | String | The URL of the given website         |
| favicon   | String | Favicon for the given website        |

* Note that the favicon parameter is required, but currently unused.

## Example

```JSON
{
  "name": "Bitbucket",
  "url": "https://bitbucket.org",
  "favicon": "bitbucket.png"
}
```