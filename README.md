Scala OptParse
==============

[![Build Status](https://travis-ci.org/frugalmechanic/scala-optparse.svg?branch=master)](https://travis-ci.org/frugalmechanic/scala-optparse)

Scala OptParse is a simple command line parsing library for Scala

SBT Dependency
--------------
    libraryDependencies += "com.frugalmechanic" %% "scala-optparse" % "1.1.1"

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

Tim Underwood (<a href="https://github.com/tpunder" rel="author">GitHub</a>, <a href="https://www.linkedin.com/in/tpunder" rel="author">LinkedIn</a>, <a href="https://twitter.com/tpunder" rel="author">Twitter</a>, <a href="https://plus.google.com/+TimUnderwood0" rel="author">Google Plus</a>)

Copyright
---------

Copyright [Frugal Mechanic](http://frugalmechanic.com)

License
-------

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)

Links
-----

* [Home](http://frugalmechanic.github.com/scala-optparse)
* [Scaladocs](http://frugalmechanic.github.com/scala-optparse/scaladocs/1.1.1#com.frugalmechanic.optparse.OptParse)
* [Source](https://github.com/frugalmechanic/scala-optparse)