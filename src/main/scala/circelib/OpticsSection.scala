/*
 * Copyright 2016-2020 47 Degrees <https://47deg.com>
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

import io.circe._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import io.circe.optics.JsonPath._

/** @param name Optics
 */
object OpticsSection extends AnyFlatSpec with Matchers with org.scalaexercises.definitions.Section {

  import circelib.helpers.OpticsHelpers._

  /**
   * Optics are a powerful tool for traversing and modifying JSON documents. They can reduce boilerplate considerably,
   * especially if you are working with deeply nested JSON.
   *
   * circe provides support for optics by integrating with Monocle. To use them, add a dependency on `circe-optics` to your build:
   *
   * {{{
   *   libraryDependencies += "io.circe" %% "circe-optics" % circeVersion
   * }}}
   *
   * Note that this will require your project to depend on both Scalaz and cats.
   *
   * =Traversing JSON=
   *
   * Let´s try a few examples using this JSON document:
   *
   * {{{
   *   val json: Json = parse("""
   *   {
   *     "order": {
   *       "customer": {
   *         "name": "Custy McCustomer",
   *         "contactDetails": {
   *           "address": "1 Fake Street, London, England",
   *           "phone": "0123-456-789"
   *         }
   *       },
   *       "items": [{
   *         "id": 123,
   *         "description": "banana",
   *         "quantity": 1
   *       }, {
   *         "id": 456,
   *         "description": "apple",
   *         "quantity": 2
   *       }],
   *       "total": 123.45
   *     }
   *   }
   * """).getOrElse(Json.Null)
   * }}}
   *
   * If we wanted to get the customer’s phone number, we could do it using a cursor as follows:
   * {{{
   *   val phoneNum: Option[String] = json.hcursor.downField("order").
   *   downField("customer").
   *   downField("contactDetails").
   *   get[String]("phone").
   *   toOption
   *   // phoneNum: Option[String] = Some(0123-456-789)
   * }}}
   *
   * This works, but we could do it in a simpler way using optics like this:
   *
   * In this example we will take the customer´s address.
   *
   * {{{
   *   import io.circe.optics.JsonPath._
   *   // import io.circe.optics.JsonPath._
   *
   *   val _address = root.order.customer.contactDetails.address.string
   *
   *   val address: Option[String] = _address.getOption(json)
   *
   * }}}
   *
   * Now is your turn, let´s try your answer:
   */
  def checkTraversingOptics(res0: Option[String]) = {
    val address: Option[String] = _address.getOption(json)
    address should be(res0)
  }

  /**
   * Note the difference between cursors and optics. With cursors, we start with a JSON document,
   * get a cursor from it, and then use that cursor to traverse the document. With optics, on the other hand,
   * we first define the traversal we want to make, then apply it to a JSON document.
   *
   * In other words, optics provide a way to separate the description of a JSON traversal from its execution.
   * Consequently we can reuse the same traversal against many different documents, compose traversals together, and so on.
   *
   * Let’s look at a more complex example. This time we want to get the descriptions of all the items in the order.
   * Using a cursor it might look like this:
   * {{{
   *   val items: Vector[Json] = json.hcursor.
   *   downField("order").
   *   downField("items").
   *   focus.
   *   flatMap(_.asArray).
   *   getOrElse(Vector.empty)
   *
   *
   *   val descriptions: Vector[String] =
   *   items.flatMap(_.hcursor.get[String]("description").toOption)
   *   // descriptions: Vector[String] = Vector("banana", "apple")
   * }}}
   *
   * And with optics:
   */
  def checkTraversingOptics2(res0: List[String]) = {
    val items: List[String] = root.order.items.each.description.string.getAll(json)

    items should be(res0)
  }

  /**
   * =Modifying JSON=
   *
   * Optics can also be used for making modifications to JSON.
   *
   * In this example we will try modifiying the quantities. Let´s try your answer, `modifiedQuantities` would be...
   */
  def modifyingJsonOptics(res0: List[Int]) = {
    val doubleQuantities: Json => Json = root.order.items.each.quantity.int.modify(_ * 2)

    val modifiedJson = doubleQuantities(json)

    val modifiedQuantities: List[Int] = root.order.items.each.quantity.int.getAll(modifiedJson)
    modifiedQuantities should be(res0)
  }

  /**
   * =Recursively modifying JSON=
   *
   * Sometimes you may need to recursively modify JSON. Let assume you need to transform all numbers into strings in
   * the example JSON:
   */
  def recursiveModifyJsonOptics(res0: Option[String]) = {
    import io.circe.optics.JsonOptics._
    import monocle.function.Plated

    val recursiveModifiedJson = Plated.transform[Json] { j =>
      j.asNumber match {
        case Some(n) => Json.fromString(n.toString)
        case None    => j
      }
    }(json)

    root.order.total.string.getOption(recursiveModifiedJson) shouldBe res0
  }

  /**
   *
   * =Dynamic=
   *
   * `JsonPath` relies on a feature of Scala called `Dynamic`. Using `Dynamic` you can call methods that don´t actually exist.
   * When you do so, the `selectDynamic` method is called, and the name of the method you wanted to call is passed as an argument.
   *
   * '''WARNING:''' The use of `Dynamic` means that your code is not "typo-safe". So be careful when you are typing
   *
   * {{{
   *   val doubleQuantities: Json => Json =
   *     root.order.itemss.each.quantity.int.modify(_ * 2) // Note the "itemss" typo
   *
   *   val modifiedJson = doubleQuantities(json)
   * }}}
   *
   * Let´s see the result for the last affirmation
   */
  def modifyingJsonDynamic(res0: Boolean) = {
    val modifiedQuantitiesDynamic: List[Int] =
      root.order.items.each.quantity.int.getAll(modifiedJson)

    modifiedQuantitiesDynamic == List(2, 4) should be(res0)
  }

}
