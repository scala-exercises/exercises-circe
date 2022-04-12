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

import io.circe.Json
import org.scalacheck.ScalacheckShapeless._
import org.scalaexercises.Test
import org.scalatest.refspec.RefSpec
import org.scalatestplus.scalacheck.Checkers
import shapeless.HNil
import circelib.utils.JsonArbitraries

class JsonSpec extends RefSpec with Checkers {

  import JsonArbitraries._

  def `json to String` = {

    val jsonString = "{\"key1\":\"value1\",\"key2\":1}"
    check(
      Test.testSuccess(
        JsonSection.jsonToString _,
        jsonString :: HNil
      )
    )
  }

  def `Json Object helpers` = {

    val res0: Json           = Json.obj("key" -> Json.fromString("value"))
    val res1: (String, Json) = ("name", Json.fromString("sample json"))
    val res2: (String, Json) = ("data", Json.obj("done" -> Json.fromBoolean(false)))
    val res3: Json           = Json.fromValues(List(Json.obj("x" -> Json.fromInt(1))))

    check(
      Test.testSuccess(
        JsonSection.jsonObject _,
        res0 :: res1 :: res2 :: res3 :: HNil
      )
    )
  }

  def `Json Class methods` = {

    val jsonArray = "[{\"field1\":1}]"

    check(
      Test.testSuccess(
        JsonSection.jsonClass _,
        jsonArray :: HNil
      )
    )
  }
}
