# LinkGroup

A link group is a named group of links.

Link groups are used to divide up links based on their intended purpose, origin, or whatever other criteria you deem to be appropriate.

A link group contains the following parameters:

| Parameter | Type    | Description                                      |
|-----------|---------|--------------------------------------------------|
| id        | Integer | Unique identifier for the given link group       |
| name      | String  | Name of the given link group                     |
| links     | Array   | All of the links associated with this link group |

* See the [Link](../link/README.md) class for a breakdown of the "links" parameter.

## Example

```JSON
{
  "id": 1,
  "name": "Coding",
  "links": [
    {
      "name": "Github",
      "url": "https://github.com",
      "favicon": "github.png"
    },
    {
      "name": "Bitbucket",
      "url": "https://bitbucket.org",
      "favicon": "bitbucket.png"
    }
  ]
}
```