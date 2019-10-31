FMPublic

name := "scala-optparse"

description := "Command line option parsing for Scala"

scalaVersion := "2.13.1"

// Note: Use "++ 2.11.1" to select a specific version when building
crossScalaVersions := Seq("2.10.6", "2.11.11", "2.12.10", "2.13.1")

scalacOptions := Seq("-unchecked", "-deprecation", "-language:implicitConversions", "-feature", "-Xlint")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % "test"
