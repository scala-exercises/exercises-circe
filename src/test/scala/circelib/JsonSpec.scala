package circelib

import io.circe.Json
import org.scalacheck.Shapeless._
import org.scalaexercises.Test
import org.scalatest.Spec
import org.scalatest.prop.Checkers
import shapeless.HNil
import circelib.utils.JsonArbitraries

class JsonSpec extends Spec with Checkers {

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

    val res0: Json = Json.obj("key" -> Json.fromString("value"))
    val res1: (String, Json) = ("name", Json.fromString("sample json"))
    val res2: (String, Json) = ("data", Json.obj("done" -> Json.fromBoolean(false)))
    val res3: Json = Json.fromValues(List(Json.obj("x" -> Json.fromInt(1))))

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