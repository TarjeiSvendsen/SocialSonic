# SocialSonic


## Build

### Container

## Tests
To test everything simultaneously (in IntelliJ IDEA), add a launch configuration running everything with the tag "UnitTests"

## Environment Variables

### Postgres
| KEY               | Default Value                           | Description                                                |
|-------------------|-----------------------------------------|------------------------------------------------------------|
| POSTGRES_DB       | SocialSonic                             | The DB used by postgres                                    |
| POSTGRES_USER     | Sonic                                   | The user used to login to the DB                           |
| POSTGRES_PASSWORD |                                         | The password used for the POSTGRES_USER, needs to be set!! |
| POSTGRES_PORT     | 5535                                    | The default port for the Postgres container                |
| POSTGRES_URL      | postgresql://localhost:5535/SocialSonic | Used by JDBC, needs to be changed with POSTGRES_PORT       |

### SocialSonic
| KEY                         | Default Value | Description                                                                                                       |
|-----------------------------|---------------|-------------------------------------------------------------------------------------------------------------------|
| SCS_PORT                    | 8054          | The default port                                                                                                  |
| SCS_NON_ADMIN_USER_CREATION | false         | Allow non-admin users to create other accounts. (Does not allow non-admin users to create admin users if enabled) |
| SCS_LEGACY_AUTH             | false         | Enable clear-text authentication, not recommended.                                                                |
| SCS_DISABLE_SCROLLING       | true          | Disable scrolling on server-side, recommended if this is already enabled in a client                              |
