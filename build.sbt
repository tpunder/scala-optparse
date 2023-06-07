name := "scala-optparse"

description := "Command line option parsing for Scala"

scalaVersion := "3.2.2"

crossScalaVersions := Seq("3.2.2", "2.13.10", "2.12.17", "2.11.12")

scalacOptions := Seq("-unchecked", "-deprecation", "-language:implicitConversions", "-feature", "-Xfatal-warnings")

// Don't use -Xfatal-warnings for the API Docs
Compile / doc / scalacOptions := Seq()

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"

publishTo := sonatypePublishToBundle.value

ThisBuild / versionScheme := Some("semver-spec")
