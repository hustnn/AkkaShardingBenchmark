name := "AkkaShardingDemo"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.10",
  "com.typesafe.akka" %% "akka-cluster" % "2.4.10",
  "com.typesafe.akka" %% "akka-cluster-sharding" % "2.4.10",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.10",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.10",
  "com.typesafe.akka" %% "akka-distributed-data-experimental" % "2.4.10",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.10" % "test",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.10",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test")
