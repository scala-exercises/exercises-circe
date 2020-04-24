/*
 * Copyright 2016-2020 47 Degrees <https://47deg.com>
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

class OpticsSpec extends RefSpec with Checkers {

  def `check traversing optics` = {
    check(
      Test.testSuccess(
        OpticsSection.checkTraversingOptics _,
        Option("1 Fake Street, London, England") :: HNil
      )
    )
  }

  def `check traversing optics 2` = {
    check(
      Test.testSuccess(
        OpticsSection.checkTraversingOptics2 _,
        List("banana", "apple") :: HNil
      )
    )
  }

  def `modify optics` = {
    check(
      Test.testSuccess(
        OpticsSection.modifyingJsonOptics _,
        List(2, 4) :: HNil
      )
    )
  }

  def `recursively modify optics` = {
    check(
      Test.testSuccess(
        OpticsSection.recursiveModifyJsonOptics _,
        Option("123.45") :: HNil
      )
    )
  }

  def `modify optics dynamic` = {
    check(
      Test.testSuccess(
        OpticsSection.modifyingJsonDynamic _,
        false :: HNil
      )
    )
  }

}
