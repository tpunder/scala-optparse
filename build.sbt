name := "scala-optparse"

organization := "com.frugalmechanic"

version := "1.2-SNAPSHOT"

description := "Command line option parsing for Scala"

licenses := Seq("Apache License, Version 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

homepage := Some(url("http://frugalmechanic.com/tech/scala-optparse"))

scalaVersion := "2.9.2"

crossScalaVersions := Seq("2.8.1", "2.8.2", "2.9.0", "2.9.0-1", "2.9.1", "2.9.2", "2.10.0-M6", "2.10.0-RC1")

// Use .target instead of target so it doesn't interfere with native sbt
EclipseKeys.eclipseOutput := Some(".target")

publishMavenStyle := true

libraryDependencies <<= (scalaVersion, libraryDependencies) { (sv, deps) =>
  val versionMap = Map("2.10.0-M6" -> "1.9-2.10.0-M6-B2")
  val scalaTestVersion = versionMap.getOrElse(sv, "1.8")
  deps :+ "org.scalatest" %% "scalatest" % scalaTestVersion % "test"
}

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