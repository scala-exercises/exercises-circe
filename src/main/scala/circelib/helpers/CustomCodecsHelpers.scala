/*
 *  scala-exercises - exercises-circe
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package circelib.helpers

import io.circe.KeyEncoder
import io.circe.syntax._

object CustomCodecsHelpers {

  case class Foo(value: String)
  // defined class Foo

  implicit val fooKeyEncoder = new KeyEncoder[Foo] {
    override def apply(foo: Foo): String = foo.value
  }

  val map = Map[Foo, Int](
    Foo("hello") -> 123,
    Foo("world") -> 456
  )

  val json = map.asJson

}
