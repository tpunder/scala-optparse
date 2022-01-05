Scala OptParse
==============

[![Build Status](https://github.com/frugalmechanic/scala-optparse/actions/workflows/build.yml/badge.svg)](https://github.com/frugalmechanic/scala-optparse/actions/workflows/build.yml)

Scala OptParse is a simple command line parsing library for Scala.

This was one of the first Scala libraries that I wrote (as you can probably tell
by looking at the code). It is still in production use today (as of Jan 2022) 
although no longer at Frugal Mechanic (which no longer exists).

Supported Scala Versions
------------------------

 - 2.10
 - 2.11
 - 2.12
 - 2.13
 - 3.x

SBT Dependency
--------------
    libraryDependencies += "com.frugalmechanic" %% "scala-optparse" % "1.2.0"

Example Usage
-------------

```scala
import com.frugalmechanic.optparse._

object HelloWorldApp extends OptParse {
  val name = StrOpt()

  def main(args:Array[String]) {
    parse(args)
    println("Hello "+name.getOrElse("world"))
  }
}
```

Authors
-------

Tim Underwood (<a href="https://github.com/tpunder" rel="author">GitHub</a>, <a href="https://www.linkedin.com/in/tpunder" rel="author">LinkedIn</a>, <a href="https://twitter.com/tpunder" rel="author">Twitter</a>)

Copyright
---------

Copyright [Frugal Mechanic](http://frugalmechanic.com)

License
-------

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)
