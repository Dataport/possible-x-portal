# POSSIBLE-X Portal

The POSSIBLE-X Portal is the centralized landing page for interacting with the POSSIBLE-X Dataspace.

Currently, it allows new users to send a registration request and upon administrative approval, receive a Gaia-X
compliant participant credential and dataspace identity, which can be used for further functionalities in the dataspace.

## Repository structure

The repository is structured as a Gradle multi-project build.

```
(...)
├── libs.versions.toml          # configuration file of version catalog for dependencies
├── settings.gradle.kts         # root project settings
├── buildSrc/                   # shared build configuration
├── frontend/                   # Angular frontend code for the GUI
│   └── build.gradle.kts        # build file for the Angular frontend
├── backend/                    # Spring backend
│   └── build.gradle.kts        # build file for the Spring backend
```

## (Re-)Generate Typescript API interfaces and REST client

The typescript API interfaces and the corresponding REST client are auto-generated from the Spring backend entity and
controller classes using the following command:

```
./gradlew generateTypeScript
```

Afterwards they can be found at

```
frontend/src/app/services/mgnt/api/backend.ts
```

## Build

If you only want to build the project, you can run

```
./gradlew build
```

after which the built jar can be found at `backend/build/libs/backend-x.y.z.jar`

## Run Portal Backend (currently including frontend)

Through gradle:

```
./gradlew bootRun
```

Alternatively running the jar directly (if built previously):

```
java -jar backend/build/libs/backend-x.y.z.jar
```

Once the service is running, you can access it at e.g. http://localhost:8088/ (depending on the used configuration).

The OpenAPI documentation can be found at http://localhost:8088/swagger-ui.html .

## Run Portal Frontend

Consumer (local testing):

```
cd frontend/
npm start
```

Once the service is running, you can access it at e.g. http://localhost:4300/  (depending on the used configuration).

