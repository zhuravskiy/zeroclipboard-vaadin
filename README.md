//todo intro

##Usage

//todo examples

##Build and Development

```./gradlew idea``` - Intelij IDEA project files generation

```./gradlew :demo:vaadinRun``` - run embedded jetty server

```./gradlew :addon:publish``` - build and publish to maven repository, see more into build.gradle publishing.repositories.maven

If you need debug client side, you can use GWT SuperDev mode, run
code server ```./gradlew :demo:vaadinSuperDevMode``` and server ```./gradlew :demo:vaadinRun```  