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

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import java.io.{ByteArrayOutputStream, PrintStream}

// We need to use System.out.println since just println seems to cache
// System.out (which we are modifying to capture output)
import System.out.println

final class TestExamples extends AnyFunSuite with Matchers {
  test("Hello World") {
    check(HelloWorldApp, Array(), "Hello world")
    check(HelloWorldApp, Array("--name", "foo"), "Hello foo")
  }

  // Original signature that fails to compile under Scala 3.0 and 3.1 with this error:
  // "scala.MatchError: JavaArrayType(TypeRef(ThisType(TypeRef(NoPrefix,module class lang)),class String)) (of class dotty.tools.dotc.core.Types$CachedJavaArrayType)"
  // Fortunately it looks like we are only testing the HelloWorldApp so we can just directly use that type
//  import scala.language.reflectiveCalls
//  type HasMain = { def main(args: Array[String]): Unit }
//  def check(obj: HasMain, args: Array[String], expectedOutput: String): Unit = synchronized {

  def check(obj: HelloWorldApp.type, args: Array[String], expectedOutput: String): Unit = synchronized {
    val os: ByteArrayOutputStream = new ByteArrayOutputStream()
    val printStream: PrintStream = new PrintStream(os)

    val old: PrintStream = System.out
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

object HelloWorldApp extends OptParse {
  val name = StrOpt()

  def main(args: Array[String]): Unit = {
    parse(args)
    println("Hello "+name.getOrElse("world"))
  }
}
