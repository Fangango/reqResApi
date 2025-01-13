# ReqResAPI Auto tests
### Test framework for automation testing ReqResAPI service

## Java Version
### 8 (liberica)

## Libs:
* JUnit 5
* OkHTTP 3
* GSON
* Jackson
* Kotlin Logging
* Strikt
* Logback


# Structure
src/test/kotlin/dataClasses - data classes

src/test/kotlin/helpers - support methods and extensions

src/test/kotlin/testDate - test data

src/test/kotlin/tests - test classes and tests

src/test/resources - project settings


## Running
### run all tests:
./gradlew clean test
### run test suit:
./gradlew clean test --tests "tests.GetResourcesTestSuit"
### run single test
./gradlew clean test --tests "tests.GetResourcesTestSuit.testGetResourcesWithoutParams"