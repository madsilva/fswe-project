name := """play-java-starter-example"""

version := "1.0-SNAPSHOT"

//lazy val root = (project in file(".")).enablePlugins(PlayJava)
lazy val root = (project in file(".")).enablePlugins(PlayJava,PlayEbean)

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies += guice
libraryDependencies += jdbc
libraryDependencies += evolutions

// Test Database
libraryDependencies += "com.h2database" % "h2" % "1.4.196"

// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test
//libraryDependencies ++= Seq(
//  jdbc,
//  anorm
//)

//libraryDependencies += "com.typesafe.play" %% "anorm" % "2.3.9"

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.41"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "org.avaje" % "ebean" % "2.7.3",
  "javax.persistence" % "persistence-api" % "1.0.2"
)

//libraryDependencies ++= Seq(
//  "com.typesafe.play" % "play-ebean_4.0.6" % "3.0.0"
//)

playEbeanModels in Compile := Seq("models.*")
playEbeanDebugLevel := 4

// Dependency for Mochito Tests
libraryDependencies += "org.mockito" % "mockito-core" % "2.18.3" % "test"