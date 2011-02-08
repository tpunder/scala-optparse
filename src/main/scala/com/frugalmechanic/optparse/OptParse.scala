/**
 * Copyright 2011 Frugal Mechanic, LLC (http://frugalmechanic.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.frugalmechanic.optparse

import scala.collection.mutable.{HashMap,ListBuffer}
import scala.util.matching.Regex

/**
 * =Simple Command Line Parsing for Scala=
 * 
 * OptParse provides simple command line parsing for Scala that only requires
 * a minimal amount of code.
 *
 *
 * ==Hello World Example==
 *
 * {{{
 * import com.frugalmechanic.optparse._
 *
 * object SimpleApp extends OptParse {
 *   val name = StrOpt()
 *
 *   def main(args:Array[String]) {
 *     parse(args)
 *     println("Hello "+name.getOrElse("world"))
 *   }
 * }
 * }}}
 *
 * And then you can pass options with:
 *
 * {{{./simpleApp --name World}}}
 * or
 * {{{./simpleApp -n World}}}
 *
 *
 * ==More Complete Example==
 *
 * {{{
 * import com.frugalmechanic.optparse._
 *
 * object MyApp extends OptParse {
 *   // --flag (-f is ambiguous since it overlaps with file)
 *   val flag = BoolOpt()
 *
 *   // --name (-n is ambiguous since it overlaps with number)
 *   val name = StrOpt()
 *   
 *   // Can be called multiple times with --aliases or -a (e.g.: --aliases Foo --aliases Bar OR -a Foo -a Bar)
 *   val aliases = MultiStrOpt()
 *
 *   // --number (-n is ambiguous since it overlaps with name)
 *   val number = IntOpt()
 *
 *   // --file (-f is ambiguous since it overlaps with flag)
 *   val file = FileOpt()
 *
 *   def main(args:Array[String]) {
 *     // Parse the command line arguments
 *     parse(args)
 *
 *     // Implicit conversion to bool
 *     if(flag) println("flag was set!")
 *     
 *     // Implicit conversion to bool to check if a value is set
 *     if(name) println("Name: "+name.get)
 *     
 *     if(aliases) println("Your alias(es) are: "+aliases.getOrElse(Nil))
 *
 *     if(number) println("Your number is: "+number.get)
 *
 *     if(file) println("Your file is: "+file.get)
 *   }
 *
 * }
 * }}}
 *
 *
 * ===Show help message===
 * {{{./myApp --help}}}
 * or
 * {{{./myApp -h}}}
 *
 *
 * ===Pass in some options===
 * {{{./myApp --flag --name Tim --aliases Timothy --aliases Timmy --number 123}}}
 *
 *
 * ==Nested Options Object Example==
 *
 * You can also use a nested options object (or class) for parsing the options:
 * {{{
 * import com.frugalmechanic.optparse._
 *
 * object MyApp2 {
 *   object options extends OptParse {
 *      val flag = BoolOpt()
 *   }
 *
 *   def main(args:Array[String]) {
 *     options.parse(args)
 *
 *     if(options.flag) println("flag is set")
 *   }
 * }
 * }}}
 *
 *
 * ==Command Line Option Types==
 *
 *  - [[com.frugalmechanic.optparse.BoolOpt]]
 *  - [[com.frugalmechanic.optparse.StrOpt]]
 *  - [[com.frugalmechanic.optparse.IntOpt]]
 *  - [[com.frugalmechanic.optparse.FileOpt]]
 *  - [[com.frugalmechanic.optparse.MultiStrOpt]]
 */
trait OptParse {
  
  /**
   * A default help option (--help or -h) that displays the help message
   */
  val helpOpt = BoolOpt("help",'h')

  // A list of all our options
  protected val allOpts = new ListBuffer[Opt]

  // Long Option Names (e.g. --foo)
  protected val longNames = new HashMap[String,Opt]

  // Short Option Names (e.g. -f)
  protected val shortNames = new HashMap[Char,Opt]

  // List of options that we found during parsing (for validation)
  protected val foundOpts = new ListBuffer[Opt]

  // Our "default" option (if any) where argument values not prefixed by an
  // option name will go. e.g. "./myApp foo", the foo would be parsed by the
  // default option
  private var _defaultOpt:Option[ArgOpt[_]] = None

  /**
   * Override to true to enable simple println's showing what OptParse is doing
   */
  val optParseDebug = false

  /**
   * By default OptParse will do a System.exit on any parsing errors or when
   * --help is invoked.  You can override this to false if you want an
   * IllegalArgumentException exception thrown instead
   */
  val optParseExitOnError = true

  private lazy val init:Unit = {
    val proposedLongNames = new HashMap[String,ListBuffer[Opt]]
    val proposedShortNames = new HashMap[Char,ListBuffer[Opt]]

    // Scan all properties/members for Opt's and build up the longNames/shortNames
    if(optParseDebug) println("Scanning Options...")

    val optClass = classOf[Opt]

    //
    // Pass 1 - Gather all the Opt's and track long/short names
    //
    getClass.getMethods.filter{f => optClass.isAssignableFrom(f.getReturnType) && f.getParameterTypes.isEmpty }.foreach { f =>
      val methodName:String = f.getName
      val opt:Opt = f.invoke(this).asInstanceOf[Opt]
      if(optParseDebug) println(" Found Opt: "+f.getName+" => "+opt)

      // Keep track of all of our opts
      allOpts += opt
      opt.methodName = methodName

      /* Long Name */
      opt.long match {
        case Some(longName) => registerLongName(longName, opt)
        case None =>
          // The method name is the proposed longName
          proposedLongNames.getOrElseUpdate(methodName, new ListBuffer) += opt
      }

      /* Short Name */
      opt.short match {
        case Some(shortName) => registerShortName(shortName, opt)
        case None =>
          // The first character of the method name is the proposed short name
          proposedShortNames.getOrElseUpdate(methodName(0), new ListBuffer) += opt
      }
    }

    //
    // Pass 2 - Finalize any of the long/short name proposals
    //
    proposedLongNames.foreach { case (longName, opts) =>
        if(!longNames.contains(longName) && opts.size == 1) {
          registerLongName(longName, opts.head)
        }
    }

    proposedShortNames.foreach { case (shortName, opts) =>
      if(!shortNames.contains(shortName) && opts.size == 1) {
        registerShortName(shortName, opts.head)
      }
    }

    //
    // Pass 3 - Make sure all options have either a long or short name
    //
    allOpts.foreach { opt =>
      assert(opt.actualLong != None || opt.actualShort != None, "Command Line Options is missing a long or short name: "+opt.methodName)

      if(optParseDebug) println(opt.actualShort+" "+opt.actualLong+" => "+opt)
    }
  }

  private def registerLongName(name:String, opt:Opt) {
    assert(!longNames.contains(name), "Duplicate Long Name: "+name)
    longNames(name) = opt
    opt.actualLong = name
  }

  private def registerShortName(char:Char, opt:Opt) {
    assert(!shortNames.contains(char), "Duplicate Short Name: "+char)
    shortNames(char) = opt
    opt.actualShort = char
  }
  
  /**
   * Declare a "default option"
   */
  def defaultOpt[T](arg:ArgOpt[T]):ArgOpt[T] = {
    if(_defaultOpt.isDefined) exit("A default option is already defined!")
    _defaultOpt = Some(arg)
    arg
  }

  /**
   * Parse the given command line options
   */
  def parse(args:Array[String]) {
    init

    // Reset all opts to their default values
    allOpts.foreach{_.reset}

    // Reset any foundOpts
    foundOpts.clear

    val buf = new ListBuffer[String]
    buf ++= args

    try {
      while(!buf.isEmpty) parseArg(buf)
      
      foundOpts.foreach{handleDisablesEnables}
      
      validateArgs()
    } catch {
      case ex:IllegalArgumentException =>
        if(optParseExitOnError) {
          exit("Error Parsing Command Line Arguments: "+ex.getMessage)
        } else throw ex
    }
  }

  private def parseArg(args:ListBuffer[String]) {
    def nextArg(): String = {
      if(args.isEmpty) error("Missing next argument")
      args.remove(0)
    }

    def parseDefaultOpt(value:String) {
      _defaultOpt match {
        case Some(opt) => opt.setValue(value)
        case None => error("No Default Opt Specified.  Not sure how to parse: "+value)
      }
    }

    val head:String = nextArg()

    /**
     * Arg Formats (with value):
     *  --name value
     *  --name=value
     *  -n value
     *  -nvalue
     *  -n=value
     *  value (default opt)
     *
     * Arg Formats (without values):
     *  --flag
     *  --noflag
     *  -f
     *  -fv (collapsed multiple flags)
     *
     * Misc Formats:
     *  -- value1 value2 --weird_value
     *  (terminate normal parsing, everything else should be the default opt)
     */

    if(head == "--") {
      // Terminate parsing, everything else is the default opt

      while(!args.isEmpty) {
        val value = nextArg()

        // Parse the values if there is a default opt set
        parseDefaultOpt(value)
      }
    } else if(head == "-") {
      error("Not sure what to do with '-'")
    } else if(head.startsWith("--")) {
      // Long Opt

      // e.g. --name=foo
      val equalsIdx = head.indexOf("=")
      val hasEquals = equalsIdx >= 0

      val endIdx = if(hasEquals) equalsIdx else head.length

      val name = head.substring(2, endIdx)

      longNames.get(name) match {
        case None => error("Unknown long option: "+name)
        case Some(opt) =>
          foundOpts += opt

          opt match {
            case argOpt:ArgOpt[_] =>
              val value = if(hasEquals) head.substring(equalsIdx+1) else nextArg()
              argOpt.setValue(value)

              if(optParseDebug) println("Parsed Long Options: '"+name+"' with value: '"+value+"'")

            case boolOpt:BoolOpt =>
              // Should not have a value
              if(hasEquals) error("Option does not take a value: "+name)
              boolOpt.value = true

              if(optParseDebug) println("Parsed BoolOpt: "+name)
            case _ =>
              error("Unknown opt type")
          }
      }

    } else if(head.startsWith("-")) {
      // Short Opt
      var idx:Int = 1

      while(idx < head.length) {
        val ch:Char = head(idx)

        shortNames.get(ch) match {
          case None => error("Unknown short option: "+ch)
          case Some(opt) =>
            foundOpts += opt

            opt match {
              case argOpt:ArgOpt[_] =>
                // Possible Patterns:
                //    -ovalue, -o value -o=value, -abovalue => -a, -b, -o value

                val value:String = if(idx+1 == head.length) {
                  // -o value
                  nextArg()
                } else if (head(idx+1) == '=') {
                  // -o= (error, missing value)
                  if(idx+2 >= head.length) error("Missing value for short option: "+ch)

                  // -o=value
                  head.substring(idx+2)
                } else {
                  // -ovalue
                  head.substring(idx+1)
                }

                argOpt.setValue(value)

                if(optParseDebug) println("Parsed Short Option: '"+ch+"' with value: '"+value+"'")
                idx = head.length // Terminate the loop

              case boolOpt:BoolOpt =>
                // TODO: Check that next char should not be an equals

                boolOpt.value = true

                if(optParseDebug) println("Parsed BoolOpt: "+ch)
            }
        }

        idx += 1
      }
    } else {
      // Default Opt (or we messed up parsing...)

      // To be valid default opt value(s) the rest of the arguments should not
      // be prefixed with either "-" or "--"

      parseDefaultOpt(head)

      while(!args.isEmpty) {
        val value = nextArg()
        if(value.startsWith("-")) error("Invalid Argument.  Expected more default option values but instead got: "+value)
        parseDefaultOpt(value)
      }
    }

    if(helpOpt) {
      help
      if(optParseExitOnError) exit(status=0) else throw new IllegalArgumentException()
    }

  }

  private def handleDisablesEnables(opt:Opt) {
    opt.disables.foreach{ disableOpt =>
      disableOpt.value = false
      foundOpts -= disableOpt
    }

    opt.enables.foreach{ enableOpt =>
      enableOpt.value = true
      if(!foundOpts.contains(enableOpt)) foundOpts += enableOpt
      handleDisablesEnables(enableOpt)
    }


  }

  private def validateArgs() {
    foundOpts.foreach{ opt =>
      if(opt.exclusive && foundOpts.size > 1) error(optName(opt)+" is exclusive and cannot be used with other arguments")

      opt.invalidWith.foreach{ other =>
        if(foundOpts.contains(other)) error("Invalid Arg Combination: "+optName(other)+" is not allowed with "+optName(opt))
      }

      opt.validWith.foreach{ other =>
        if(!foundOpts.contains(other)) error("Invalid Arg Combination: "+optName(other)+" is required when using "+optName(opt))
      }
    }
  }

  protected def optName(opt:Opt):String = {
    opt.actualLong match {
      case Some(name) => return "--"+name
      case None =>
    }
    opt.actualShort match {
      case Some(name) => return "-"+name
      case None =>
    }
    throw new Exception("Unknown Name for Opt: "+opt)
  }


  /**
   * Exit the application with a message
   */
  protected def error(msg:String) {
    //System.err.println("Error Parsing Command Line Arguments: "+msg)
    //exit(msg)
    throw new IllegalArgumentException(msg)
  }

  protected def exit(msg:String=null, status:Int = -1) {
    if(null != msg) System.err.println(msg)
    System.exit(status)
  }

  /**
   * Print the help message to STDERR
   */
  def help {
    init

    val maxLongSize = allOpts.foldLeft(0){ (maxLength,opt) =>
      val length = opt.actualLong.map{_.length}.getOrElse(0)
      if(length > maxLength) length else maxLength
    }

    allOpts.foreach { opt =>
      val short:String = opt.actualShort match {
        case None => "  "
        case Some(ch) => "-"+ch
      }

      val long:String = opt.actualLong.map{s => "--"+s}.getOrElse("").padTo(maxLongSize+2,' ')

      val sb = new StringBuilder
      sb.append("  "+short+"  "+long+"  "+opt.desc)

      if(!opt.enables.isEmpty || !opt.disables.isEmpty) {
        sb.append("  (")

        if(!opt.enables.isEmpty) {
          sb.append("enables: ")
          sb.append(opt.enables.map{o =>
            assert(null != o)
            o.actualLong.map{s => "--"+s}.getOrElse(o.toString)
          }.mkString(", "))
        }

        if(!opt.disables.isEmpty) {
          sb.append("disables: ")
          sb.append(opt.disables.map{o =>
            assert(null != o)
            o.actualLong.map{s => "--"+s}.getOrElse(o.toString)
          }.mkString(", "))
        }

        sb.append(")")
      }

      System.err.println(sb.toString)
    }
  }
}
