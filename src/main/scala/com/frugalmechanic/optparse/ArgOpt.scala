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
 * Base type for command line options that take an argument
 *
 * For implementation examples see:
 *
 *  - [[com.frugalmechanic.optparse.StrOpt]]
 *  - [[com.frugalmechanic.optparse.IntOpt]]
 *  - [[com.frugalmechanic.optparse.FileOpt]]
 *  - [[com.frugalmechanic.optparse.MultiStrOpt]]
 */
abstract class ArgOpt[T](_long:Option[String],
                   _short:Option[Char],
                   _default:Option[T],
                   _desc:String,
                   _enables: => Seq[BoolOpt],
                   _disables: => Seq[BoolOpt],
                   _invalidWith: => Seq[Opt],
                   _validWith: => Seq[Opt],
                   _exclusive:Boolean,
                   _validate: T => Boolean
) extends OptVal[T](_long, _short, _default, _desc, _enables, _disables, _invalidWith, _validWith, _exclusive) {

  /**
   * An invididual element (usually just T)
   */
  type Elem

  /**
   * Specifies how to parse the string from the command line argument into a value of type T
   */
  def parseValue(value:String):Elem

  /**
   * Set the value (sending it through parseValue())
   */
  def setValue(arg:String)

  /**
   * Validate the parsed value using the validate argument passed in on the constructor
   */
  def validate(value:T): Boolean = _validate(value)
}