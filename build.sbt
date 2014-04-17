name := "scala-optparse"

organization := "com.frugalmechanic"

version := "1.1.1"

description := "Command line option parsing for Scala"

licenses := Seq("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

homepage := Some(url("http://frugalmechanic.com/tech/scala-optparse"))

scalaVersion := "2.10.4"

// Note: Use "++ 2.11.0" to select a specific version when building
crossScalaVersions := Seq("2.10.4", "2.11.0")

// Use .target instead of target so it doesn't interfere with native sbt
EclipseKeys.eclipseOutput := Some(".target")

publishMavenStyle := true

resolvers += "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases/"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.3" % "test"

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT")) 
    Some("snapshots" at nexus + "content/repositories/snapshots") 
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <developers>
    <developer>
      <id>tim</id>
      <name>Tim Underwood</name>
      <email>tim@frugalmechanic.com</email>
      <organization>Frugal Mechanic</organization>
      <organizationUrl>http://frugalmechanic.com</organizationUrl>
    </developer>
  </developers>
  <scm>
      <connection>scm:git:git://github.com/frugalmechanic/scala-optparse.git</connection>
      <developerConnection>scm:git:git://github.com/frugalmechanic/scala-optparse.git</developerConnection>
      <url>https://github.com/frugalmechanic/scala-optparse</url>
  </scm>)