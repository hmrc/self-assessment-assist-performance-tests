**This is a template README.md.  Be sure to update this with project specific content that describes your performance test project.**

# self-assessment-assist-performance-tests

Performance test suite for the `SELF-ASSESSEMENT-ASSIST`, using [performance-test-runner](https://github.com/hmrc/performance-test-runner) under the hood.

## Pre-requisites

### Starting services

Start Mongo Docker container as follows:

```bash
docker run --rm -d --name mongo -d -p 27017:27017 mongo:4.0
```

To run services required for the test locally, execute:
```
./run-services.sh
```
or, if using sm2:
```
./run-services.sh sm2
```

## Running tests

Run smoke test (locally) as follows:

```bash
./local-perf-tests.sh
```

alternatively:


```bash
sbt -Dperftest.runSmokeTest=true -DrunLocal=true gatling:test
```

Run full performance test (locally) as follows:

```bash
sbt -DrunLocal=true gatling:test
```

Run smoke test (staging) as follows:

```bash
sbt -Dperftest.runSmokeTest=true -DrunLocal=false gatling:test
```

### Logging

The default log level for all HTTP requests is set to `WARN`. Configure [logback.xml](src/test/resources/logback.xml) to update this if required.


## Scalafmt

Check all project files are formatted as expected as follows:

```bash
sbt scalafmtCheckAll scalafmtCheck
```

Format `*.sbt` and `project/*.scala` files as follows:

```bash
sbt scalafmtSbt
```

Format all project files as follows:

```bash
sbt scalafmtAll
```

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
