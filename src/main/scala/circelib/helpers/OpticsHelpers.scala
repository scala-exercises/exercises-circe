/*
 * scala-exercises - exercises-circe
 * Copyright (C) 2015-2016 47 Degrees, LLC. <http://www.47deg.com>
 */

package circelib.helpers

import cats.syntax.either._
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
