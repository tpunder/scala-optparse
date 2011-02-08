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
 * The base type of all command line options
 */
abstract class Opt(_long:Option[String],
                   _short:Option[Char],
                   _desc:String,
                   _enables: => Seq[BoolOpt],
                   _disables: => Seq[BoolOpt],
                   _invalidWith: => Seq[Opt],
                   _validWith: => Seq[Opt],
                   _exclusive:Boolean) {

  /**
   * The long form of this option (if any)
   *
   * (e.g. "myoption" would be called with "--myoption" on the command line)
   */
  def long: Option[String] = _long

  /**
   * The short form of this option (if any)
   *
   * (e.g. "a" would be called with "-a" on the command line)
   */
  def short: Option[Char] = _short

  /**
   * The description of this option which is used as part of the help message
   */
  def desc: String = _desc

  /**
   * A List of boolean options that this option enables
   */
  def enables: Seq[BoolOpt] = _enables

  /**
   * A list of boolean option that this option disables
   */
  def disables: Seq[BoolOpt] = _disables

  /**
   * Other command line options that this option is invalid with
   */
  def invalidWith: Seq[Opt] = _invalidWith

  /**
   * Other command line options that are required when this option is set
   */
  def validWith: Seq[Opt] = _validWith

  /**
   * Whether or not this command line options is exclusive and cannot be used
   * with any other options (e.g. a help options that prints the help message
   * and then quits)
   */
  def exclusive:Boolean = _exclusive

  // Reset this option to it's default
  protected[optparse] def reset:Unit

  /* Placeholders for the actual long and short names since "long" and "short"
   * above might be initialized to None and we need a place to put any generated
   * values.
   */
  protected[optparse] var actualLong:Option[String] = None
  protected[optparse] var actualShort:Option[Char] = None

  // Also track the method name for easier error reporting
  protected[optparse] var methodName:String = ""
}