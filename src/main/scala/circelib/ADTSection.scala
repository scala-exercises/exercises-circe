/*
 *  scala-exercises - exercises-circe
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package circelib

import io.circe.Error
import io.circe.syntax._
import org.scalaexercises.definitions.Section
import org.scalatest.{FlatSpec, Matchers}

/**
 * ==ADTs encoding and decoding==
 *
 * The most straightforward way to encode / decode ADTs is by using generic derivation for the case classes but
 * explicitly defined instances for the ADT type.
 *
 * @param name ADT (Algebraic Data Types)
 */
object ADTSection extends FlatSpec with Matchers with Section {

  import helpers.ADTHelpers._

  /**
   * Consider the following ADT:
   * {{{
   * sealed trait Event
   *
   * case class Foo(i: Int) extends Event
   * case class Bar(s: String) extends Event
   * case class Baz(c: Char) extends Event
   * case class Qux(values: List[String]) extends Event
   * }}}
   *
   * And the encoder / decoder instances:
   * {{{
   * import cats.syntax.functor._
   * import io.circe.{ Decoder, Encoder }, io.circe.generic.auto._
   * import io.circe.syntax._
   *
   * object GenericDerivation {
   *   implicit val encodeEvent: Encoder[Event] = Encoder.instance {
   *     case foo @ Foo(_) => foo.asJson
   *     case bar @ Bar(_) => bar.asJson
   *     case baz @ Baz(_) => baz.asJson
   *     case qux @ Qux(_) => qux.asJson
   *   }
   *
   *   implicit val decodeEvent: Decoder[Event] =
   *     List[Decoder[Event]](
   *       Decoder[Foo].widen,
   *       Decoder[Bar].widen,
   *       Decoder[Baz].widen,
   *       Decoder[Qux].widen
   *     ).reduceLeft(_ or _)
   * }
   * }}}
   *
   * Note that we have to call widen (which is provided by Cats’s Functor syntax, which we bring into scope with
   * the first import) on the decoders because the Decoder type class is not covariant. The invariance of circe’s
   * type classes is a matter of some controversy (Argonaut for example has gone from invariant to covariant and
   * back), but it has enough benefits that it’s unlikely to change, which means we need workarounds like this
   * occasionally.
   *
   * It’s also worth noting that our explicit Encoder and Decoder instances will take precedence over the
   * generically-derived instances we would otherwise get from the io.circe.generic.auto._ import (see slides from
   * Travis Brown’s talk here for some discussion of how this prioritization works).
   *
   * We can use these instances like this:
   */
  def genericDerivation(res0: Either[Error, Foo], res1: String) = {
    import GenericDerivation._
    import io.circe.parser.decode

    decode[Event]("""{ "i": 1000 }""") shouldBe res0
    (Foo(100): Event).asJson.noSpaces shouldBe res1
  }

  // TODO: Example from documentation not compiling (https://github.com/circe/circe/issues/1125)
  /*
  /**
   * =A more generic solution=
   *
   * We can avoid the fuss of writing out all the cases by using the `circe-shapes` module:
   * {{{
   * // To suppress previously imported implicit codecs.
   * import GenericDerivation.{ decodeEvent => _, encodeEvent => _ }
   *
   * object ShapesDerivation {
   *   import io.circe.shapes
   *   import shapeless.{ Coproduct, Generic }
   *
   *   implicit def encodeAdtNoDiscr[A, Repr <: Coproduct](implicit
   *     gen: Generic.Aux[A, Repr],
   *     encodeRepr: Encoder[Repr]
   *   ): Encoder[A] = encodeRepr.contramap(gen.to)
   *
   *   implicit def decodeAdtNoDiscr[A, Repr <: Coproduct](implicit
   *     gen: Generic.Aux[A, Repr],
   *     decodeRepr: Decoder[Repr]
   *   ): Decoder[A] = decodeRepr.map(gen.from)
   *
   * }
   *
   * And then:
   * }}}
   */
  def shapesDerivation(res0: Either[Error, Event], res1: String) = {
    import ShapesDerivation._

    import io.circe.parser.decode, io.circe.syntax._

    decode[Event]("""{ "i": 1000 }""") shouldBe res0
    (Foo(100): Event).asJson.noSpaces shouldBe res1
  }
  /**
   * This will work for any ADT anywhere that `encodeAdtNoDiscr` and `decodeAdtNoDiscr` are in scope. If we wanted it
   * to be more limited, we could replace the generic `A` with our ADT types in those definitions, or we could make
   * the definitions non-implicit and define implicit instances explicitly for the ADTs we want encoded this way.
   *
   * The main drawback of this approach (apart from the extra `circe-shapes` dependency) is that the constructors
   * will be tried in alphabetical order, which may not be what we want if we have ambiguous case classes (where
   * the member names and types are the same).
   */

   */

  /**
   *  =The future=
   *
   * The generic-extras module provides a little more configurability in this respect. We can write the following,
   * for example:
   * {{{
   * // Same as above
   * import ShapesDerivation.{encodeAdtNoDiscr => _, decodeAdtNoDiscr => _}
   *
   * object GenericExtraDerivation {
   *   import io.circe.generic.extras.Configuration
   *
   *   implicit val genDevConfig: Configuration =
   *     Configuration.default.withDiscriminator("what_am_i")
   * }
   * }}}
   */
  def genericExtrasADT(res0: Either[Error, Event]) = {
    import GenericExtraDerivation._
    import io.circe.parser.decode
    import io.circe.generic.extras.auto._

    decode[Event]("""{ "i": 1000, "what_am_i": "Foo" }""") shouldBe res0
  }

}
