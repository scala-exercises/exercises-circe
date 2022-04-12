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

import io.circe._, io.circe.parser._
import io.circe.optics.JsonPath._

object OpticsHelpers {

  val json: Json = parse("""
{
  "order": {
    "customer": {
      "name": "Custy McCustomer",
      "contactDetails": {
        "address": "1 Fake Street, London, England",
        "phone": "0123-456-789"
      }
    },
    "items": [{
      "id": 123,
      "description": "banana",
      "quantity": 1
    }, {
      "id": 456,
      "description": "apple",
      "quantity": 2
    }],
    "total": 123.45
  }
}
""").getOrElse(Json.Null)

  val _address = root.order.customer.contactDetails.address.string

  val items: Vector[Json] = json.hcursor
    .downField("order")
    .downField("items")
    .focus
    .flatMap(_.asArray)
    .getOrElse(Vector.empty)

  val doubleQuantities: Json => Json =
    root.order.itemss.each.quantity.int.modify(_ * 2)

  val modifiedJson = doubleQuantities(json)

}
