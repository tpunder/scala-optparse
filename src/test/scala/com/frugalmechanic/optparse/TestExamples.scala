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

import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import java.io.{ByteArrayOutputStream, PrintStream}

// We need to use System.out.println since just println seems to cache
// System.out (which we are modifying to capture output)
import System.out.println

class TestExamples extends FunSuite with ShouldMatchers {
  test("Hello World") {
    check(HelloWorldApp, Array(), "Hello world")
    check(HelloWorldApp, Array("--name", "foo"), "Hello foo")
  }

  type HasMain = {def main(args:Array[String])}

  def check(obj:HasMain, args:Array[String], expectedOutput:String):Unit = synchronized {
    val os = new ByteArrayOutputStream
    val printStream = new PrintStream(os)

    val old = System.out
    try {
      System.setOut(printStream)
      obj.main(args)
      printStream.flush
      os.toString("UTF-8").trim should equal(expectedOutput)
    } finally {
      System.setOut(old)
    }
  }
}

// START SNIPPET: hello_world
import com.frugalmechanic.optparse._

object HelloWorldApp extends OptParse {
  val name = StrOpt()

  def main(args:Array[String]) {
    parse(args)
    println("Hello "+name.getOrElse("world"))
  }
}
// START SNIPPET: hello_world