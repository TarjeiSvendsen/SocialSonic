# SocialSonic


## Build

### Container

## Tests
To test everything simultaneously (in IntelliJ IDEA), add a launch configuration running everything with the tag "UnitTests"

## Environment Variables
| KEY                         | Default Value         | Description                                                                                                       |
|-----------------------------|-----------------------|-------------------------------------------------------------------------------------------------------------------|
| SCS_PORT                    | 8054                  | The default port                                                                                                  |
| SCS_NON_ADMIN_USER_CREATION | false                 | Allow non-admin users to create other accounts. (Does not allow non-admin users to create admin users if enabled) |
| SCS_WATCHSERVICE_INTERVAL   | 5                     | How often (in minutes) the watchservice should do a scan of the music folders.                                    |
| DB_DB_NAME                  | SocialSonic           | The name of the database in postgres, mariadb, etc...                                                             |
| DB_HOST                     | host.docker.internal  | The host on which the database is hosted.                                                                         |
| DB_PORT                     | 5535                  | The port of the database host                                                                                     |
| DB_USER                     | Sonic                 | The database user to login with                                                                                   |
| DB_PASSWORD                 |                       | The password to use with DB_USER                                                                                  |
| DB_DRIVER                   | org.postgresql.Driver | The driver for JDBC to use, can be org.postgresql.Driver, org.mariadb.jdbc.Driver, or com.mysql.jdbc.Driver       |
| DB_JDBC_TYPE                | postgresql            | The db type for the jdbc connection url, MUST be changed when changing the DB_DRIVER.                             |

## Credits

[eduardo-sl/java-docker-image](https://github.com/eduardo-sl/java-docker-image?tab=readme-ov-file) was used to reduce the docker image file from 515 MB, to only 193MB, a 62.5% reduction.

