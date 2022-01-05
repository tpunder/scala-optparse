FMPublic

name := "scala-optparse"

description := "Command line option parsing for Scala"

// Note: Don't publish for Scala 3.1 since it is not backwards compatible with
//       3.0 and you can only have a single 3.X artifact published at a time
scalaVersion := "3.0.2"

// Note: Use "++ 2.11.1" to select a specific version when building
crossScalaVersions := Seq("2.10.7", "2.11.12", "2.12.15", "2.13.7", "3.0.2")

scalacOptions := Seq("-unchecked", "-deprecation", "-language:implicitConversions", "-feature", "-Xfatal-warnings")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"
