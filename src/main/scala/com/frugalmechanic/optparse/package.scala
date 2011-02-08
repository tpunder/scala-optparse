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

package com.frugalmechanic

import scala.util.matching.Regex

package object optparse {
  /**
   * Allows BoolOpt(enables=MyOpt) instead of BoolOpt(enables=Seq(MyOpt))
   */
  implicit def OptToSeq[T](opt:Opt) = Seq(opt)

  // Allows "if(MyFlag) ..."
  implicit def BoolOptToBool(opt:BoolOpt): Boolean = opt.value match {
    case Some(b) => b
    case None => false
  }

  /**
   * Allows: if(NameOpt) ... instead of if(NameOpt.value.isDefined) ...
   */
  implicit def OptToBool[T](opt:OptVal[T]): Boolean = opt.value match {
    case Some(_) => true
    case None => false
  }

  /**
   * Allows longName="name" without needing long=Some("name")
   */
  implicit def ValToOption[T](value:T): Option[T] = Some(value)

  /**
   * Allows regex usage for the validate option: validate="^[a-zA-Z]+$"
   */
  implicit def StringToValidateRegex(regex:Regex): (String) => Boolean = {
    (s:String) => { regex.findFirstIn(s) != None }
  }

  /**
   * Allows any Option methods to be used on an OptVal
   */
  implicit def OptValToOption[T](opt:OptVal[T]): Option[T] = opt.value
}
