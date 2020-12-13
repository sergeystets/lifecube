## Life Cube

A simple app/website that does 3 basic things:
- The Simple Web Entry form for wishes/dreams/ambitions.
- Page for reading submissions others have done (with pagination).
- Way to search for keywords in the submission.


## Building and running (for Development):
### Prerequisites
1. Install Java 8
2. Docker

### Local stack

- Run `docker-compose -f docker-compose-local.yml up` to run all the required AWS-service on your 
local machine using [LocalStack](https://github.com/localstack/localstack).
- Please, run the following command in a terminal when the LocalStack is ready
`curl -X PUT "http://localhost:4571/submissions/_doc/86d13fc5-f867-4c5c-a96c-7a69b24f1d41" -H 'Content-Type: application/json' -d '{"id" : "86d13fc5-f867-4c5c-a96c-7a69b24f1d41", "text" : "my goal is to become famous 2", "type" : "GOAL"}'`
It will add a sample data into ElasticSearch (running inside LocalStack)

Use `local` (by adding the following argument to a maven command `-Dspring.profiles.active=local`) Spring profile to connect the backend application to LocalStack.

### Frontend
#### Prerequisites
- Install `Node` and `NPM` (tested on versions: `Node: v10.16.0`, `NPM: 6.10.2`)

#### Building and running
- Please, `run npm install` and `npm run serve` in `/fronted` directory to start dev server.
- Please note, that `server.port` property in `application-<profile>.properties` file should be in sync with `VUE_APP_SERVER_URL` evn variable defined in `frontend/.env` file. 

### Backend
- Run `lifecube.LifeCubeApplication` application using `local` Spring profile (`-Dspring.profiles.active=local`)

## Building and running (in production)
- Run `./mvnw clean install` in a root directory. This will build `frontend` module first and then `backend`. 
During the `install` step the `frontend` app is being build into `target/dist` folder (which is a production ready bundle containing all the frontend-related files). 
Then the content of `/frontend/target/dist` folder will be copied into `/backend/src/main/resources/public` directory. 
In that way the app (frontend + backend) will be packed into a single executable `.jar` file, which could be then deployed anywhere a single artifact.
**Note** if `./mvnw clean install` will fail with any of NPM/Node.js related errors like:
```
[ERROR] Failed to execute goal com.github.eirslett:frontend-maven-plugin:1.7.6:install-node-and-npm (install node and npm) on project frontend: Could not download Node.js: Could not download http://nodejs.org/dist/v10.16.0/node-v10.16.0-darwin-x64.tar.gz: sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target -
```
it means `frontend-maven-plugin` plugin defined in `/frontend/pom.xml` file does not work for you.

Then: 
1. install Node and NPM manually (using official guide) 
2. go to a `frontend` directory and run `npm install` + `npm run build`
3. after that in a root project directory run `./mvnw clean install -Dskip.npm=true`

- Run `docker build -t <put-image-name-here> .`
- Run `docker run -e SPRING_PROFILES_ACTIVE=prod -p 8080:8080 --rm <put-image-name-here>:latest` to verify that the app works.
- Deploy it into AWS (for example using AWS ECS)

## Infrastructure (deploying to AWS)
### AWS Lambda
The source code of AWS lambdas are stored in `/aws/lambda` directory. 
In particular, `dynamo-db-to-elastic-search-lambda` is needed to synchronize all changes made in AWS Dynamo DB with ElasticSearch.

### Elastic Search
The backend is compatible with ES v 7.1

### Dynamo DB


## Google Captcha
The app is using Google re-captcha v3, please use Google documentation to generate new credentials and update the following properties correspondingly:
```
google.recaptcha.key.site=<site key>
google.recaptcha.key.secret=<secret key>
google.recaptcha.key.enabled=true
```
 
