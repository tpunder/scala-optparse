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
 * Base type for a command line option that contains a value
 */
abstract class OptVal[T](_long:Option[String],
                         _short:Option[Char],
                         _default:Option[T],
                         _desc:String,
                         _enables: => Seq[BoolOpt],
                         _disables: => Seq[BoolOpt],
                         _invalidWith: => Seq[Opt],
                         _validWith: => Seq[Opt],
                         _exclusive:Boolean
) extends Opt(_long, _short, _desc, _enables, _disables, _invalidWith, _validWith, _exclusive) {

  private var _value:Option[T] = None
  private[optparse] def value_=(v:T) { _value = Some(v) }
  private[optparse] def value_=(o:Option[T]) { _value = o }

  /**
   * The value attached to this command line option (wrapped in an Option)
   */
  def value:Option[T] = _value

  /**
   * The default value for this options
   */
  def default: Option[T] = _default

  /**
   * Reset the value to the default
   */
  def reset { value = default }

  /**
   * Get the value
   *
   * This calls Option.get on the underlying value so an exception will be
   * thrown if the value is not set.
   */
  def apply():T = value.get
}