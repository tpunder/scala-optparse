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
 * Type and Object aliases for OptParse that are mixed into OptParse to allow
 * importing of only OptParse (import com.frugalmechanic.optparse.OptParse) and
 * still have access to all of the types when you mix OptParse into your
 * application object.
 */
trait OptParseTypes {
  import com.frugalmechanic.{optparse => o}

  type OptParse = o.OptParse

  type Opt       = o.Opt
  type ArgOpt[T] = o.ArgOpt[T]
  type OptVal[T] = o.OptVal[T]
  
  type MultiOpt[T]  = o.MultiOpt[T]
  type SingleOpt[T] = o.SingleOpt[T]
  
  type BoolOpt = o.BoolOpt
  val BoolOpt  = o.BoolOpt

  type FileOpt = o.FileOpt
  val FileOpt  = o.FileOpt

  type IntOpt = o.IntOpt
  val IntOpt  = o.IntOpt

  type MultiStrOpt = o.MultiStrOpt
  val MultiStrOpt  = o.MultiStrOpt

  type StrOpt = o.StrOpt
  val StrOpt  = o.StrOpt
}
