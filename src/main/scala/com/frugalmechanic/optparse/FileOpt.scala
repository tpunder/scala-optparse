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

import java.io.File

/**
 * ==See [[com.frugalmechanic.optparse.FileOpt]] for documentation and examples==
 */
object FileOpt {
  def apply(
    /* Common Options from Opt Trait */
    long: Option[String]=None,
    short: Option[Char]=None,
    default: Option[File]=None,
    desc: String="",
    enables: => Seq[BoolOpt]=Nil,
    disables: => Seq[BoolOpt]=Nil,
    invalidWith: => Seq[Opt]=Nil,
    validWith: => Seq[Opt]=Nil,
    exclusive: Boolean=false,
    validate: File => Boolean = {s => true}
  ) = new FileOpt(long, short, default, desc, enables, disables, invalidWith, validWith, exclusive, validate)
}

/**
 * ==FileOpt has the same usage as [[com.frugalmechanic.optparse.StrOpt]] except it returns a [[java.io.File]] instead of a String==
 */
class FileOpt(
  /* Common Options from Opt Trait */
  long: Option[String]=None,
  short: Option[Char]=None,
  default: Option[File]=None,
  desc: String="",
  enables: Seq[BoolOpt]=Nil,
  disables: Seq[BoolOpt]=Nil,
  invalidWith: Seq[Opt]=Nil,
  validWith: Seq[Opt]=Nil,
  exclusive: Boolean=false,

  /* ArgOpt Specific Options */
  validate: File => Boolean = {f => true}

  /* FileOpt Specific Options */
  //mustExist: Boolean = false
) extends ArgOpt[File](long, short, default, desc, enables, disables, invalidWith, validWith, exclusive, validate) with SingleOpt[File] {
  def parseValue(v:String) = new File(v)
}