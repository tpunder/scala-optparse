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

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class TestExampleApp1 extends FunSuite with ShouldMatchers {

  test("Empty Opts") {
    val app = parse(Array())

    app.flag.value should be(Some(false))
    app.str.value should be(None)
    app.number.value should be(None)
    
    // Implicit conversions to Option
    app.flag.get should be(false)
    evaluating { app.str.get } should produce [NoSuchElementException]

    // Implicit conversions to Boolean
    val flagIsSet:Boolean = app.flag
    val strIsSet:Boolean = app.str

    flagIsSet should be(false)
    strIsSet should be(false)
  }

  test("Simple Argument Values") {
    parse(Array("-f")).flag.get should be(true)
    parse(Array("--flag")).flag.get should be(true)

    parse(Array("-s","foo")).str.get should be("foo")
    parse(Array("--str","foo")).str.get should be("foo")

    parse(Array("-n","123")).number.get should be(123)
    parse(Array("--number","123")).number.get should be(123)

    parse(Array("-a","Foo","-a","Bar")).aliases.get should be(Seq("Foo","Bar"))
    parse(Array("--alias","Foo","--alias","Bar")).aliases.get should be(Seq("Foo","Bar"))
  }

  test("Multi Flags") {
    val app = parse(Array("-fb"))

    app.flag.get should be(true)
    app.bool.get should be(true)
  }

  test("Invalid Arguments") {
    evaluating { parse(Array("foo","bar")) } should produce [IllegalArgumentException]
    evaluating { parse(Array("--foo")) } should produce [IllegalArgumentException]
    evaluating { parse(Array("--flag", "with argument")) } should produce [IllegalArgumentException]
    evaluating { parse(Array("-f", "with argument")) } should produce [IllegalArgumentException]
    evaluating { parse(Array("--str", "foo", "bar")) } should produce [IllegalArgumentException]
  }

  test("Default Option Arguments") {
    parseDefault(Array("foo","bar")).default.get should be(Seq("foo","bar"))
    parseDefault(Array("--flag","--str","asd","foo","bar")).default.get should be(Seq("foo","bar"))
  }

  test("Invalid Default Option Arguments") {
    evaluating { parseDefault(Array("foo","bar","--flag")) } should produce [IllegalArgumentException]
  }

  private def parse(args:Array[String]):ExampleApp1 = {
    val app = new ExampleApp1
    app.parse(args)
    app
  }
  
  private def parseDefault(args:Array[String]) = {
    val app = new ExampleApp1 {
      val default = defaultOpt{MultiStrOpt()}
    }
    app.parse(args)
    app
  }
  
}