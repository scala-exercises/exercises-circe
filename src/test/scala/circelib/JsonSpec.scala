/*
 *  scala-exercises - exercises-circe
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
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
