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

class CustomCodecsSpec extends RefSpec with Checkers {

  import CustomCodecsSection._

  def `map Json` = {
    check(
      Test.testSuccess(
        mapJson _,
        (Right[String, Int](123): Either[String, Int]) :: HNil
      )
    )
  }

  def `basic custom key mapping` =
    check(
      Test.testSuccess(
        basicCustomKeyMapping _,
        """{"first_name":"Foo","last_name":"McBar"}""" :: HNil
      )
    )

  def `complex custom key mapping` =
    check(Test.testSuccess(complexCustomKeyMapping _, """{"my-int":13,"s":"Qux"}""" :: HNil))

  def `mapping member annotation` =
    check(Test.testSuccess(mappingMemberAnnotation _, """{"my-int":13,"s":"Qux"}""" :: HNil))

}
