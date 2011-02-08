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

/**
 * Companion object for creating StrOpt's
 *
 * ==See [[com.frugalmechanic.optparse.StrOpt]] for documentation and examples==
 */
object StrOpt {
  /**
   * Helper method to create a new StrOpt
   *
   * @param long Long name for this option (--long_name on the command line)
   * @param short Short name for this option (-short_name on the command line)
   * @param default Default value for this option
   * @param desc Description of the parameter (used in help message)
   * @param enables Other flags that this option implicitly enables
   * @param disables Other flags that this option implicitly disables
   * @param inavlidWith Other options that this option is not valid with
   * @param validWith Other options that are required to be set when using this options
   * @param exclusive Whether or not this option is exclusive and cannot be used with any other options (e.g. like --help where it prints the help message and exits)
   * @param validate A method that takes the String value and returns a boolean indicating if the argument value is valid
   */
  def apply(
    /* Common Options from Opt Trait */
    long: Option[String]=None,
    short: Option[Char]=None,
    default: Option[String]=None,
    desc: String="",
    enables: => Seq[BoolOpt]=Nil,
    disables: => Seq[BoolOpt]=Nil,
    invalidWith: => Seq[Opt]=Nil,
    validWith: => Seq[Opt]=Nil,
    exclusive: Boolean=false,
    validate: String => Boolean = {s => true}
  ) = new StrOpt(long, short, default, desc, enables, disables, invalidWith, validWith, exclusive, validate)
}

/**
 * A command line option that takes a string argument
 *
 * ==Examples==
 * {{{
 * object MyApp extends OptParse {
 *   // Basic string argument using a default long name of --name and short name of -n
 *   val name = StrOpt()
 *
 *   // Example with all available options instead of relying on the defaults
 *   val message = BoolOpt(
 *      long="message",          // Long name to use (--message)
 *      short="m",               // Short name to use (-m)
 *      default="Hello",         // Default value
 *      desc="Message to print", // Help message description
 *      enables=Nil,             // Other flags to enable if this one is enabled (single option or a Seq of options)
 *      disables=Nil,            // Other flags to disable if this one is enabled (single option or a Seq of options)
 *      invalidWith=Nil,         // Other options this flag is invalid with (they cannot be set)
 *      validWith=Nil,           // Other options that are required with this flag
 *      exclusive=false,         // Other options can be set when this option is set
 *      validate="^[a-zA-Z ,]+$" // Use a regex for validation via an implicit that converts it to: (String) => Boolean
 *   )
 *
 *   def main(args:Array[String]) {
 *     parse(args)
 *
 *     // getOrElse is available via an implicit from OptVal to Option[String]
 *     println(message+" "+name.getOrElse("Anonymous"))
 *   }
 * }
 * }}}
 *
 * @param long Long name for this option (--long_name on the command line)
 * @param short Short name for this option (-short_name on the command line)
 * @param default Default value for this option
 * @param desc Description of the parameter (used in help message)
 * @param enables Other flags that this option implicitly enables
 * @param disables Other flags that this option implicitly disables
 * @param inavlidWith Other options that this option is not valid with
 * @param validWith Other options that are required to be set when using this options
 * @param exclusive Whether or not this option is exclusive and cannot be used with any other options (e.g. like --help where it prints the help message and exits)
 * @param validate A method that takes the String value and returns a boolean indicating if the argument value is valid
 */
class StrOpt(
  /* Common Options from Opt Trait */
  long: Option[String],
  short: Option[Char],
  default: Option[String],
  desc: String,
  enables: => Seq[BoolOpt],
  disables: => Seq[BoolOpt],
  invalidWith: => Seq[Opt],
  validWith: => Seq[Opt],
  exclusive: Boolean,

  /* ArgOpt Specific Options */
  validate: String => Boolean
) extends ArgOpt[String](long, short, default, desc, enables, disables, invalidWith, validWith, exclusive, validate) with SingleOpt[String] {

  /**
   * A no-op default parsing method
   */
  def parseValue(v:String) = v
}