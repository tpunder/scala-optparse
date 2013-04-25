Scala OptParse is a simple command line parsing library for Scala!

SBT Dependency
--------------
    libraryDependencies += "com.frugalmechanic" %% "scala-optparse" % "1.1.1"

Example Usage
-------------
    import com.frugalmechanic.optparse._

    object HelloWorldApp extends OptParse {
      val name = StrOpt()

      def main(args:Array[String]) {
        parse(args)
        println("Hello "+name.getOrElse("world"))
      }
    }

License
-------

[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)

Links
-----

* [Home](http://frugalmechanic.github.com/scala-optparse)
* [Scaladocs](http://frugalmechanic.github.com/scala-optparse/scaladocs/1.1.1#com.frugalmechanic.optparse.OptParse)
* [Source](https://github.com/frugalmechanic/scala-optparse)