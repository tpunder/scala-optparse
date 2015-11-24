FMPublic

name := "scala-optparse"

version := "1.1.3-SNAPSHOT"

description := "Command line option parsing for Scala"

scalaVersion := "2.11.7"

// Note: Use "++ 2.11.1" to select a specific version when building
crossScalaVersions := Seq("2.10.6", "2.11.7")

// Use .target instead of target so it doesn't interfere with native sbt
EclipseKeys.eclipseOutput := Some(".target")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"
