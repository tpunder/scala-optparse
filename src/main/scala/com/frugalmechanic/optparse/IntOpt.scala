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
 * ==See [[com.frugalmechanic.optparse.IntOpt]] for documentation and examples==
 */
object IntOpt {
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
   * @param validate A method that takes the String value and returns a boolean indicating if the argument value is valid
   */
  def apply(
    /* Common Options from Opt Trait */
    long: Option[String]=None,
    short: Option[Char]=None,
    default: Option[Int]=None,
    desc: String="",
    enables: => Seq[BoolOpt]=Nil,
    disables: => Seq[BoolOpt]=Nil,
    invalidWith: => Seq[Opt]=Nil,
    validWith: => Seq[Opt]=Nil,
    exclusive: Boolean=false,
    validate: Int => Boolean = {s => true}
  ) = new IntOpt(long, short, default, desc, enables, disables, invalidWith, validWith, exclusive, validate)
}

/**
 * ==IntOpt has the same usage as [[com.frugalmechanic.optparse.StrOpt]] except it returns a Int instead of a String==
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
class IntOpt(
  /* Common Options from Opt Trait */
  long: Option[String],
  short: Option[Char],
  default: Option[Int],
  desc: String,
  enables: Seq[BoolOpt],
  disables: Seq[BoolOpt],
  invalidWith: Seq[Opt],
  validWith: Seq[Opt],
  exclusive: Boolean,

  /* ArgOpt Specific Options */
  validate: Int => Boolean
) extends ArgOpt[Int](long, short, default, desc, enables, disables, invalidWith, validWith, exclusive, validate) with SingleOpt[Int] {

  /**
   * Parses the string command line argument into an Int
   */
  def parseValue(v:String) = v.toInt
}