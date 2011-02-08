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
 * An example app demonstrating how to use OptParse
 *
 * For ease of testing this is a class instead of an object.  If you wanted to
 * be able to run this class using it's main method then you would need to make
 * it an object.
 */
class ExampleApp1 extends OptParse {
  // For testing we want OptParse to throw exceptions instead of calling System.exit
  override val optParseExitOnError = false

  /**
   * An example boolean option.
   * 
   * defaults to -f and --flag as long as no other option names conflict
   */
  val flag = BoolOpt()

  /**
   * An example options taking a string as an argument
   *
   * Defaults to -s and --str as long as no other option names conflict
   */
  val str = StrOpt()

  /**
   * An example Int option
   *
   * Defaults to -n and --number since no other option names conflict
   */
  val number = IntOpt()

  /**
   * An example string option that can be passed multiple times
   *
   * Defaults to -a and the long form is overridden to --alias
   */
  val aliases = MultiStrOpt(long="alias")

  /**
   * Another flag (-b or --bool) to test multiple flags (e.g. -fb)
   */
  val bool = BoolOpt()  

  def main(args:Array[String]) {
    parse(args)

    // An implicit Opt to Boolean conversion is used here
    if(flag) println("Flag is set")

    // The same implicit is used here along with an implicit from OptVal to Option (the str.get)
    if(str) println("Str is set: "+str.get)
  }
}