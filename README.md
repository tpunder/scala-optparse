Scala OptParse
==============

[![Build Status](https://github.com/tpunder/scala-optparse/actions/workflows/build.yml/badge.svg)](https://github.com/tpunder/scala-optparse/actions/workflows/build.yml)

Scala OptParse is a simple command line parsing library for Scala

SBT Dependency
--------------
    libraryDependencies += "com.frugalmechanic" %% "scala-optparse" % "1.1.3"

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
