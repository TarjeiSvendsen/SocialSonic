# SocialSonic


## Build

### Container

## Tests
To test everything simultaneously (in IntelliJ IDEA), add a launch configuration running everything with the tag "UnitTests"

## Environment Variables

### Postgres
| KEY               | Default Value                           | Description                                                        |
|-------------------|-----------------------------------------|--------------------------------------------------------------------|
| POSTGRES_DB       | SocialSonic                             | The DB used by postgres                                            |
| POSTGRES_USER     | Sonic                                   | The user used to login to the DB                                   |
| POSTGRES_PASSWORD |                                         | The password used for the POSTGRES_USER, needs to be set!!         |
| POSTGRES_PORT     | 5535                                    | The default port for the Postgres container                        |
| POSTGRES_URL      | postgresql://localhost:5535/SocialSonic | Used by SocialSonic, needs to be changed alongside POSTGRES_PORT!! |

### SocialSonic
| KEY              | Default Value | Description      |
|------------------|---------------|------------------|
| SOCIALSONIC_PORT | 8054          | The default port |
