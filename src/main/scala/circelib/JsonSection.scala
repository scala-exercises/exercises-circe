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
import org.scalaexercises.definitions
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import circelib.helpers.JsonHelpers

/** @param name Json
 */
object JsonSection extends AnyFlatSpec with Matchers with definitions.Section {

  import JsonHelpers._

  /** `Json` is the circe data type representing a JSON object. It's very useful to be familiar with this data type since
   * it's how circe models the base type we want to address.
   *
   * To begin, let's briefly talk about the shape of every `Json` object. It's basically semi-structured data built on
   * top of key-value pairs. These key-value pairs have a specific shape:
   *   - keys are strings.
   *   - values can be multiple types.
   *
   * Next, to model a real JSON object, we need to support different data types in the value field. For this purpose,
   * we have different available methods so we can create a `Json` object from different source data types. Some examples
   * of these methods are `fromString`, `fromBoolean`, `fromDouble` and so on. For further details about all possible
   * methods, see the [[http://circe.github.io/circe/api/io/circe/Json$.html Scala docs]].
   *
   * Let's take a look at how these methods work.
   *
   * {{{
   *   scala> import io.circe.Json
   *   scala> // fromString example
   *   scala> val jsonString: Json = Json.fromString("scala exercises")
   *   jsonString: io.circe.Json = "scala exercises"
   *
   *   scala> // fromDouble example
   *   scala> val jsonDouble: Option[Json] = Json.fromDouble(1)
   *   jsonDouble: Option[io.circe.Json] = Some(1.0)
   *
   *   scala> val jsonBoolean: Json = Json.fromBoolean(true)
   *   jsonBoolean: io.circe.Json = true
   *
   *   scala> // fromFields example
   *   scala> val fieldList = List(
   *           ("key1", Json.fromString("value1")),
   *           ("key2", Json.fromInt(1)))
   *   fieldList: List[(String, io.circe.Json)] = List((key1,"value1"), (key2,1))
   *
   *   scala> val jsonFromFields: Json = Json.fromFields(fieldList)
   *   jsonFromFields: io.circe.Json =
   *   {
   *     "key1" : "value1",
   *     "key2" : 1
   *   }
   *
   * }}}
   *
   *
   * In addition, there are a few other methods that allow you to convert a `Json` object to a `String`.
   *
   * {{{
   *
   *   scala> jsonFromFields.noSpaces
   *   res0: String = {"name":"sample json","version":1,"data":{"done":false,"rate":4.9}}
   *
   *   scala> jsonFromFields.spaces2
   *   res1: String =
   *   {
   *     "name" : "sample json",
   *     "version" : 1,
   *     "data" : {
   *       "done" : false,
   *       "rate" : 4.9
   *     }
   *   }
   *
   *   scala> jsonFromFields.spaces4
   *   res2: String =
   *   {
   *       "name" : "sample json",
   *       "version" : 1,
   *       "data" : {
   *           "done" : false,
   *           "rate" : 4.9
   *       }
   *   }
   *
   * }}}
   *
   * What would be the string output for our `jsonFromFields` value?
   */
  def jsonToString(res0: String) =
    jsonFromFields.noSpaces should be(res0)

  /** Let's see how we can use these methods to create custom `Json`s that represents specific JSON strings.
   */
  def jsonObject(res0: Json, res1: (String, Json), res2: (String, Json), res3: Json) = {

    "{\"key\":\"value\"}" should be(res0.noSpaces)

    "{\"name\":\"sample json\",\"data\":{\"done\":false}}" should be(
      Json.fromFields(List(res1, res2)).noSpaces
    )

    "[{\"x\":1}]" should be(res3.noSpaces)

  }

  /** Furthemore, there are some other methods that allow you to deal with `Json` objects and apply transformation. We
   * can use them to modify or apply any changes to a given `Json` object in a simpler way, as if we we're dealing with it
   * manually.
   *
   * We are going to start with this `Json` array:
   *
   * {{{
   *   val jsonArray: Json = Json.fromValues(List(
   *     Json.fromFields(List(("field1", Json.fromInt(1)))),
   *     Json.fromFields(List(
   *       ("field1", Json.fromInt(200)),
   *       ("field2", Json.fromString("Having circe in Scala Exercises is awesome"))
   *     ))
   *   ))
   *
   * }}}
   *
   * Finally, we have a `transformJson` method:
   *
   * {{{
   *   def transformJson(jsonArray: Json): Json =
   *     jsonArray mapArray { oneJson: Vector[Json] =>
   *       oneJson.init
   *     }
   * }}}
   *
   * So, with these in mind, what should be the result if we apply our `transformJson` function to our `jsonArray` value?
   *
   */
  def jsonClass(res0: String) =
    transformJson(jsonArray).noSpaces should be(res0)

}
