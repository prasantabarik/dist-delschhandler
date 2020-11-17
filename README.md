# Service Template 
Template for creating services using Spring-Boot and Kotlin
# Features Available
1.	Service Template using Spring-Boot with Kotlin
2.	Setup for Open API Documentation
3.  Setup for Log4j and integration with Azure App Insights
4.  Setup for Prometheus metrics
5.	Integration with Cosmos DB
6.	Setup with example for Component Testing using Mockito-Kotlin
7.  Setup with example for Spring Contract Testing
8.  Setup with example for performance testing using Gatling
9.  Setup with example for BDD Framework using Cucumber

# Getting Started Guide

**Installation**

First clone the repository

`git clone https://190181@dev.azure.com/190181/CommerceMVP/_git/Kotlin-service-template
`

**Creation of the Maven Archetype**

Generate the archetype as below,

`cd Kotlin-service-template
mvn archetype:create-from-project
cd target\generated-sources\archetype\
mvn install`

The archetype will be created and be stored in local maven repositories

**Creation of the new service From Template**

**From Command Line**

`mvn archetype:generate -DarchetypeGroupId=com.tcs.service -DarchetypeArtifactId=service-kotlin-template-archetype -DarchetypeVersion=0.0.1-SNAPSHOT 
-DgroupId=com.tcs.service -DartifactId=<Mention the Service Name> -Dversion=1.0-SNAPSHOT`

**From IntelliJ**

File -> New Project -> Create From Archetype -> Import Archetype
In the dialogue box, mention the artifactId and groupId

# Getting Started with Cucumber (BDD Framework)
Cucumber setup maintained in a submodule called cucumber-test
cd cucumber-test/test/resources/features
A sample .feature file is present.
All the test cases will go under defined .feature files.
Each use case can be captured in plain english text in some defined format.
The format can be finalised within the team.

