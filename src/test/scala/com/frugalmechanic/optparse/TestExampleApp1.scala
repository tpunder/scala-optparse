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

final class TestExampleApp1 extends AnyFunSuite with Matchers {

  test("Empty Opts") {
    val app: ExampleApp1 = parse(Array())

    app.flag.value shouldBe Some(false)
    app.str.value shouldBe None
    app.number.value shouldBe None

    // Implicit conversions to Option
    app.flag.get shouldBe(false)
    an [NoSuchElementException] should be thrownBy app.str.get

    // Implicit conversions to Boolean
    val flagIsSet: Boolean = app.flag
    val strIsSet: Boolean = app.str

    flagIsSet shouldBe(false)
    strIsSet shouldBe(false)
  }

  test("Simple Argument Values") {
    parse(Array("-f")).flag.get shouldBe(true)
    parse(Array("--flag")).flag.get shouldBe(true)

    parse(Array("-s","foo")).str.get shouldBe("foo")
    parse(Array("--str","foo")).str.get shouldBe("foo")

    parse(Array("-n","123")).number.get shouldBe(123)
    parse(Array("--number","123")).number.get shouldBe(123)

    parse(Array("-a","Foo","-a","Bar")).aliases.get shouldBe(Seq("Foo","Bar"))
    parse(Array("--alias","Foo","--alias","Bar")).aliases.get shouldBe(Seq("Foo","Bar"))
  }

  test("Multi Flags") {
    val app = parse(Array("-fb"))

    app.flag.get shouldBe(true)
    app.bool.get shouldBe(true)
  }

  test("Invalid Arguments") {
    an [IllegalArgumentException] should be thrownBy parse(Array("foo","bar"))
    an [IllegalArgumentException] should be thrownBy parse(Array("--foo"))
    an [IllegalArgumentException] should be thrownBy parse(Array("--flag", "with argument"))
    an [IllegalArgumentException] should be thrownBy parse(Array("-f", "with argument"))
    an [IllegalArgumentException] should be thrownBy parse(Array("--str", "foo", "bar"))
  }

  test("Default Option Arguments") {
    parseDefault(Array("foo","bar")).default.get should be (Seq("foo","bar"))
    parseDefault(Array("--flag","--str","asd","foo","bar")).default.get should be (Seq("foo","bar"))
  }

  test("Invalid Default Option Arguments") {
    an [IllegalArgumentException] should be thrownBy parseDefault(Array("foo","bar","--flag"))
  }

  private def parse(args: Array[String]): ExampleApp1 = {
    val app: ExampleApp1 = new ExampleApp1()
    app.parse(args)
    app
  }

  private def parseDefault(args: Array[String]): DefaultExampleApp1 = {
    val app: DefaultExampleApp1 = new DefaultExampleApp1()
    app.parse(args)
    app
  }

  private class DefaultExampleApp1 extends ExampleApp1 {
    val default: ArgOpt[Seq[String]] = defaultOpt(MultiStrOpt())
  }

}