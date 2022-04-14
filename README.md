# AID-HF

## Project Structure

`/src/*` structure follows default Java structure.

`/src/main/webapp/*` structure follows default Angular structure.

`/target` folder where artifacts can be found after compile / build.

## Development

Before you can build this project, you must install and configure the following dependencies on your machine:

1. [Node.js] **(v16.14.0)**: We use Node to run a development web server and build the project (for the frontend).
   Depending on your system, you can install Node either from source or as a pre-packaged bundle.
2. [Java] **(minimal v8)**: We use Java on the backend.
3. [Maven] **(v3.6)**: We use Maven to manage backend dependencies and builds.

[Node.js]: https://nodejs.org/en/
[Java]: https://www.java.com/en/
[Maven]: https://maven.apache.org/

### Frontend development

After installing Node, move to the `/src/main/webapp/` folder. You should be able to run the following command to
install the necessary development tools. You will only need to run this command when dependencies in
`/src/main/webapp/package.json`change.

```
npm install
```

To run the frontend in a development mode, run this command from the `/src/main/webapp/` folder:

```
npm start
```

Now a development webserver will serve the application from `http://localhost:4200/`.

### Backend development

After installing Java and Maven, move to the project's root folder. You should be able to run the following command to
install the dependencies. You will only need to run this command when dependencies change in `/pom.xml`.

```
mvn install
```

To run the backend in development mode, run this command from the `/` (root of project):

```
mvn spring-boot:run
```

Edit the `application.yml` file inside `/src/main/resources` and set the `spring-profiles-active` property to: `dev`.
This allows the application to load correct configuration from the `application-dev.yml` file and communicate with the
frontend on port 4200.

Now a webserver will serve the application from `http://localhost:4200/api/advice`

#### OpenMarkov

It is worth noting that the project contains several OpenMarkov repositories. You can find them in
`src/main/java/org/openmarkov` folder. In file `src/main/java/org/openmarkov/plugin/PluginLoader.java` methods
`loadAllPlugins()` and `PluginLoader()` have been modified to allow integration with the rest of the project. In case
you want to update the OpenMarkov repositories, these changes need to be reflected. You can find the details about
OpenMarkov on their [Bitbucket page].

[bitbucket page]: https://bitbucket.org/cisiad/org.openmarkov/wiki/Home

## Building for production

Both components (frontend and backend) are separate entities that communicate through a REST-API and can be deployed in
various ways.

We chose to include the javascript client in backend's webserver (this is an easy option to get started). This means
that only one application needs to run for the application to function properly. Both frontend and backend will start
simultaneously.

Other option is to separate the two components - build and run frontend and backend separately. Something to keep in
mind - The development tools of Angular already include a webserver (which you also use for frontend development), but
this webserver is not suited for production use! If you choose this option, you have to configure a webserver yourself (
for example NGINX) to serve the frontend.

Either way you decide to do it, edit the `application.yml` file inside `/src/main/resources` and set
the `spring-profiles-active`
property to: `prod`. This allows the application to load correct configuration from the `application-prod.yml` file.

### Packaging as jar with frontend included

To build the final jar for production, run this command from the root folder:

```
mvn -Pprod clean verify
```

The output file you can find in: `target/aid-hf-<version>-SNAPSHOT.jar` and can be started with:

```
java -jar <fileName.jar>
```

Now the included Tomcat webserver will serve the Javascript (angular) application on route: `http://localhost:8080/`
and the API route(s) on `http://localhost:8080/api/advice`. Be aware that this raises CORS related issues for
communication coming from other ports (which is better in terms of security).

### Packaging as jar without frontend

To build the final jar for production with only the REST-API routes included, run:

```
mvn clean verify
```

Now this package doesn't contain the frontend on route `http://localhost:8080` but only the API
on: `http://localhost:8080/api/advice`. To build the frontend go to `src/main/webapp` and run:

```
npm run build
```

The output can be found in `target/classes/static`. Copy these files to the appropriate folder of the webserver (of your
choice). Be aware of CORS related issues when dealing with a separate deployment. By default, when the origin deviates
from the backend URL you can get an error on HTTP requests. Please check the
file `src/main/resources/application-prod.yml` with the property `allowed-api`. Within this file you can adjust the
configuration to allow external origins.
