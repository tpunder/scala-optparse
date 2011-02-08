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
 * Companion object for creating BoolOpt's
 *
 * ==See [[com.frugalmechanic.optparse.BoolOpt]] for documentation and examples==
 */
object BoolOpt {
  /**
   * @param long Long name for this option (--long_name on the command line)
   * @param short Short name for this option (-short_name on the command line)
   * @param default Default value for this option
   * @param desc Description of the parameter (used in help message)
   * @param enables Other flags that this option implicitly enables
   * @param disables Other flags that this option implicitly disables
   * @param inavlidWith Other options that this option is not valid with
   * @param validWith Other options that are required to be set when using this options
   * @param exclusive Whether or not this option is exclusive and cannot be used with any other options (e.g. like --help where it prints the help message and exits)
   */
  def apply(
    /* Common Options from Opt Trait */
    long: Option[String]=None,
    short: Option[Char]=None,
    default: Option[Boolean]=Some(false),
    desc: String="",
    enables: => Seq[BoolOpt]=Nil,
    disables: => Seq[BoolOpt]=Nil,
    invalidWith: => Seq[Opt]=Nil,
    validWith: => Seq[Opt]=Nil,
    exclusive: Boolean=false
  ) = new BoolOpt(long, short, default, desc, enables, disables, invalidWith, validWith, exclusive)
}

/**
 * A boolean/flag command line option
 *
 * ==Examples==
 * {{{
 * import com.frugalmechanic.optparse._
 *
 * object MyApp extends OptParse {
 *   // Basic flag using a default long name of --flag and short name of -f
 *   val flag = BoolOpt()
 *
 *   // Example with all available options instead of relying on the defaults
 *   val verbose = BoolOpt(
 *      long="verbose",     // Long name to use (--verbose)
 *      short="v",          // Short name to use (-v)
 *      default=false,      // Default value for this flag (true/false)
 *      desc="Be verbose",  // Help message description
 *      enables=noisy,      // Other flags to enable if this one is enabled (single option or a Seq of options)
 *      disables=Seq(quiet),// Other flags to disable if this one is enabled (single option or a Seq of options)
 *      invalidWith=quiet,  // Other options this flag is invalid with (they cannot be set)
 *      validWith=noisy,    // Other options that are required with this flag
 *      exclusive=false,    // Other options can be set when this option is set
 *   )
 *
 *   // You can also use "new BoolOpt()" instead of using the companion object
 *   val noisy = new BoolOpt()
 *   val quiet = new BoolOpt()
 *
 *   def main(args:Array[String]) {
 *     parse(args)
 *
 *     if(verbose) println("You want verbose output")
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
 */
class BoolOpt(
  /* Common Options from Opt Trait */
  long: Option[String],
  short: Option[Char],
  default: Option[Boolean],
  desc: String,
  enables: => Seq[BoolOpt],
  disables: => Seq[BoolOpt],
  invalidWith: => Seq[Opt],
  validWith: => Seq[Opt],
  exclusive: Boolean
) extends OptVal[Boolean](long, short, default, desc, enables, disables, invalidWith, validWith, exclusive) {

}