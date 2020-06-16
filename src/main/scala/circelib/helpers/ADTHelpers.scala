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

import cats.syntax.functor._
import io.circe.{Decoder, Encoder}, io.circe.generic.auto._
import io.circe.syntax._

object ADTHelpers {
  sealed trait Event

  case class Foo(i: Int)               extends Event
  case class Bar(s: String)            extends Event
  case class Baz(c: Char)              extends Event
  case class Qux(values: List[String]) extends Event

  object GenericDerivation {

    implicit val encodeEvent: Encoder[Event] = Encoder.instance {
      case foo @ Foo(_) => foo.asJson
      case bar @ Bar(_) => bar.asJson
      case baz @ Baz(_) => baz.asJson
      case qux @ Qux(_) => qux.asJson
    }

    implicit val decodeEvent: Decoder[Event] =
      List[Decoder[Event]](
        Decoder[Foo].widen,
        Decoder[Bar].widen,
        Decoder[Baz].widen,
        Decoder[Qux].widen
      ).reduceLeft(_ or _)
  }

  object ShapesDerivation {
    import shapeless.{Coproduct, Generic}

    implicit def encodeAdtNoDiscr[A, Repr <: Coproduct](implicit
        gen: Generic.Aux[A, Repr],
        encodeRepr: Encoder[Repr]
    ): Encoder[A] = encodeRepr.contramap(gen.to)

    implicit def decodeAdtNoDiscr[A, Repr <: Coproduct](implicit
        gen: Generic.Aux[A, Repr],
        decodeRepr: Decoder[Repr]
    ): Decoder[A] = decodeRepr.map(gen.from)

  }

  object GenericExtraDerivation {
    import io.circe.generic.extras.Configuration

    implicit val genDevConfig: Configuration =
      Configuration.default.withDiscriminator("what_am_i")
  }

}
