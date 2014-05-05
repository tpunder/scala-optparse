name := "scala-optparse"

version := "1.1.1"

description := "Command line option parsing for Scala"

scalaVersion := "2.10.4"

// Note: Use "++ 2.11.0" to select a specific version when building
crossScalaVersions := Seq("2.10.4", "2.11.0")

// Use .target instead of target so it doesn't interfere with native sbt
EclipseKeys.eclipseOutput := Some(".target")

libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.3" % "test"
