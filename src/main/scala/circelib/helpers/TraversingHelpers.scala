/*
 *  scala-exercises - exercises-circe
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package circelib.helpers

import io.circe.{ACursor, HCursor, Json}
import io.circe.parser.parse

object TraversingHelpers {

  val json: String = """
  {
    "id": "c730433b-082c-4984-9d66-855c243266f0",
    "name": "Foo",
    "counts": [1, 2, 3],
    "values": {
      "bar": true,
      "baz": 100.001,
      "qux": ["a", "b"]
    }
  }
  """
  val doc: Json    = parse(json).getOrElse(Json.Null)

  val cursor: HCursor = doc.hcursor

  val reversedNameCursor: ACursor =
    cursor.downField("name").withFocus(_.mapString(_.reverse))

}
