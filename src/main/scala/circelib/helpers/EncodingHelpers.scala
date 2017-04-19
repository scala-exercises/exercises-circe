/*
 * scala-exercises - exercises-circe
 * Copyright (C) 2015-2016 47 Degrees, LLC. <http://www.47deg.com>
 */

package circelib.helpers

import io.circe._
import io.circe.syntax._

object EncodingHelpers {

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
