# API Documentation

## Endpoints

The Links API supports the following primary endpoints:

| Endpoint   | Description                     |
|------------|---------------------------------|
| /group     | Queries one group of links      |
| /group/all | Queries all groups of links     |
| /link      | Queries a single link           |
| /link/all  | Queries all individual links    |
| /import    | Imports one or more link groups |
| /export    | Exports all link groups         |

## Request formatting

All requests have a content type of "application/json" and follow the below scheme for CRUD operations:

| Operation | HTTP Request Type |
|-----------|-------------------|
| Create    | PUT               |
| Read      | POST              |
| Update    | PATCH             |
| Delete    | DELETE            |

## Supported request types

| Endpoint   | PUT       | POST    | PATCH   | DELETE  |
|------------|-----------|---------|---------|---------|
| /group     | &check;   | &check; | &check; | &check; |
| /group/all | &cross;   | &check; | &cross; | &check; |
| /link      | &check;   | &check; | &check; | &check; |
| /link/all  | &cross;   | &check; | &cross; | &check; |
| /import    | &check;   | &cross; | &cross; | &cross; |
| /export    | &cross;   | &check; | &cross; | &cross; |

## Examples

For more detailed explanations of each endpoint, and examples of the CRUD operations for each endpoint, check the documentation for the respective endpoint.

- [Link Groups](group/README.md)
- [Link Groups (all)](group/all/README.md)
- [Links](links/README.md)
- [Links (all)](links/all/README.md)
- [Import](import/README.md)
- [Export](export/README.md)
