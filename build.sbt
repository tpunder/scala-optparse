FMPublic

name := "scala-optparse"

description := "Command line option parsing for Scala"

scalaVersion := "2.13.2"

// Note: Use "++ 2.11.1" to select a specific version when building
crossScalaVersions := Seq("2.10.7", "2.11.12", "2.12.11", "2.13.2")

scalacOptions := Seq("-unchecked", "-deprecation", "-language:implicitConversions", "-feature", "-Xlint")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"
