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

import io.circe.Decoder.Result
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import io.circe._

/** @param name Traversing and modifying
 */
object TraversingSection
    extends AnyFlatSpec
    with Matchers
    with org.scalaexercises.definitions.Section {

  import circelib.helpers.TraversingHelpers._

  /**
   * Working with JSON in circe usually involves using a cursor. Cursors are used both for extracting data and for performing modification.
   *
   * Suppose we have the following JSON document:
   *
   * {{{
   * import cats.syntax.either._
   * import io.circe._, io.circe.parser._
   *
   * val json: String = """
   * {
   *   "id": "c730433b-082c-4984-9d66-855c243266f0",
   *   "name": "Foo",
   *   "counts": [1, 2, 3],
   *   "values": {
   *   "bar": true,
   *   "baz": 100.001,
   *   "qux": ["a", "b"]
   *   }
   * } """
   *
   * val doc: Json = parse(json).getOrElse(Json.Null)
   * }}}
   * =Extracting data=
   *
   * In order to traverse we need to create an `HCursor` with the focus at the document’s root
   *
   * {{{
   *     val cursor: HCursor = doc.hcursor
   * }}}
   *
   * We can then use various operations to move the focus of the cursor around the document and extract data from it
   */
  def moveFocus(res0: Either[String, Double]) = {
    val baz: Decoder.Result[Double] = cursor.downField("values").downField("baz").as[Double]

    baz should be(res0)
  }

  /**
   * You can also use `get[A](key)` as shorthand for `downField(key).as[A]`.
   *
   * What would be the result in this case?
   */
  def moveFocus2(res0: Either[String, Double]) = {
    val baz2: Decoder.Result[Double] = cursor.downField("values").get[Double]("baz")

    baz2 should be(res0)
  }

  /**
   * You can also move to a side of an Array field.
   *
   * What would the result be when traversing through the array?
   */
  def moveFocus3(res0: Either[String, String]) = {
    val secondQux: Decoder.Result[String] =
      cursor.downField("values").downField("qux").downArray.right.as[String]

    secondQux should be(res0)
  }

  /**
   * =Transforming data=
   *
   * In this section we are going to learn how to use a cursor to modify JSON
   *
   * Circe has three slightly different cursor implementations:
   *
   * `Cursor` provides functionality for moving around a tree and making modifications.
   *
   * `HCursor` tracks the history of operations performed. This can be used to provide useful error messages when something goes wrong.
   *
   * `ACursor` also tracks history, but represents the possibility of failure (e.g. calling `downField` on a field that doesn’t exist.
   *
   * Pay attention because we are going to use a `.mapString` this time.
   *
   * {{{
   *   val reversedNameCursor: ACursor =
   *     cursor.downField("name").withFocus(_.mapString(_.reverse))
   * }}}
   *
   * We can then return to the root of the document and return its value with `top`
   *
   * {{{
   * val reversedName: Option[Json] = reversedNameCursor.top
   * }}}
   *
   * The result contains the original document, but what would be the result for "name" field?
   */
  def modifyJson(res0: Either[String, String]) = {

    val nameResult: Result[String] =
      cursor.downField("name").withFocus(_.mapString(_.reverse)).as[String]

    nameResult should be(res0)

  }
}
