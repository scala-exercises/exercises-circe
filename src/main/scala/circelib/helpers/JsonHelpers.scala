/*
 * scala-exercises - exercises-circe
 * Copyright (C) 2015-2016 47 Degrees, LLC. <http://www.47deg.com>
 */

package circelib.helpers

import io.circe.Json

object JsonHelpers {

  val jsonFromFields: Json = Json.fromFields(
    List(
      ("key1", Json.fromString("value1")),
      ("key2", Json.fromInt(1))
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
