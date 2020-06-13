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

package circelib.helpers

import io.circe.Json

object JsonHelpers {

  val jsonFromFields: Json = Json.fromFields(
    List(
      ("key1", Json.fromString("value1")),
      ("key2", Json.fromInt(1))
    )
  )

  val jsonArray: Json = Json.fromValues(
    List(
      Json.fromFields(List(("field1", Json.fromInt(1)))),
      Json.fromFields(
        List(
          ("field1", Json.fromInt(200)),
          ("field2", Json.fromString("Having circe in Scala Exercises is awesome"))
        )
      )
    )
  )

  def transformJson(jsonArray: Json): Json =
    jsonArray mapArray (_.init)
}
