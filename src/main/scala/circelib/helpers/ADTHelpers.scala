/*
 *  scala-exercises - exercises-circe
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
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

    implicit def encodeAdtNoDiscr[A, Repr <: Coproduct](
        implicit
        gen: Generic.Aux[A, Repr],
        encodeRepr: Encoder[Repr]): Encoder[A] = encodeRepr.contramap(gen.to)

    implicit def decodeAdtNoDiscr[A, Repr <: Coproduct](
        implicit
        gen: Generic.Aux[A, Repr],
        decodeRepr: Decoder[Repr]): Decoder[A] = decodeRepr.map(gen.from)

  }

  object GenericExtraDerivation {
    import io.circe.generic.extras.Configuration

    implicit val genDevConfig: Configuration =
      Configuration.default.withDiscriminator("what_am_i")
  }

}
