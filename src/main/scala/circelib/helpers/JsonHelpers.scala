/*
 *  scala-exercises - exercises-circe
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package circelib.helpers

import io.circe.Json

object JsonHelpers {

  val jsonFromFields: Json = Json.fromFields(
    List(
      ("name", Json.fromString("sample json")),
      ("version", Json.fromInt(1)),
      ("data", Json.fromFields(List(("done", Json.fromBoolean(false)))))
    ))

  val jsonArray: Json = Json.fromValues(
    List(
      Json.fromFields(List(("field1", Json.fromInt(1)))),
      Json.fromFields(
        List(
          ("field1", Json.fromInt(200)),
          ("field2", Json.fromString("Having circe in Scala Exercises is awesome"))
        ))
    ))

  def transformJson(jsonArray: Json): Json =
    jsonArray mapArray (_.init)
}
