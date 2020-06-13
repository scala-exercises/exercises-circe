/*
 * Copyright 2016-2020 47 Degrees Open Source <https://www.47deg.com>
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

package circelib

import org.scalacheck.ScalacheckShapeless._
import org.scalaexercises.Test
import org.scalatest.refspec.RefSpec
import org.scalatestplus.scalacheck.Checkers
import shapeless.HNil

class TraversingSpec extends RefSpec with Checkers {

  def `move as` = {
    check(
      Test.testSuccess(
        TraversingSection.moveFocus _,
        (Right[String, Double](100.001): Either[String, Double]) :: HNil
      )
    )
  }

  def `move as2` = {
    check(
      Test.testSuccess(
        TraversingSection.moveFocus2 _,
        (Right[String, Double](100.001): Either[String, Double]) :: HNil
      )
    )
  }

  def `move as3` = {
    check(
      Test.testSuccess(
        TraversingSection.moveFocus3 _,
        (Right[String, String]("b"): Either[String, String]) :: HNil
      )
    )
  }

  def `modify Json` = {
    check(
      Test.testSuccess(
        TraversingSection.modifyJson _,
        (Right[String, String]("ooF"): Either[String, String]) :: HNil
      )
    )
  }
}
