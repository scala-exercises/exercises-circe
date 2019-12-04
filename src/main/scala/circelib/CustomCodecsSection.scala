/*
 *  scala-exercises - exercises-circe
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package circelib

import io.circe.generic.extras._, io.circe.syntax._
import circelib.helpers.CustomCodecsHelpers.json
import org.scalaexercises.definitions.Section
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/**
 * =Custom encoders/decoders=
 *
 * If you want to write your own codec instead of using automatic or semi-automatic derivation, you can do so in a couple of ways.
 *
 * Firstly, you can write a new `Encoder[A]` and `Decoder[A]` from scratch
 *
 * {{{
 * class Thing()
 *
 * implicit val encodeFoo: Encoder[Thing] = new Encoder[Thing] {
 *    final def apply(a: Thing): Json = ??? // your implementation goes here
 * }
 *
 * implicit val decodeFoo: Decoder[Thing] = new Decoder[Thing] {
 *    final def apply(c: HCursor): Decoder.Result[Thing] = Left(DecodingFailure("Not implemented yet", c.history))
 * }
 * }}}
 *
 * But in many cases you might find it more convenient to piggyback on top of the decoders that are already available. For example, a codec for
 * `java.time.Instant` might look like this:
 * {{{
 * import cats.syntax.either._
 *
 * import java.time.Instant
 *
 * implicit val encodeInstant: Encoder[Instant] = Encoder.encodeString.contramap[Instant](_.toString)
 *
 * implicit val decodeInstant: Decoder[Instant] = Decoder.decodeString.emap { str =>
 *    Either.catchNonFatal(Instant.parse(str)).leftMap(t => "Instant")
 * }
 * }}}
 *
 * @param name Custom codecs
 */
object CustomCodecsSection extends AnyFlatSpec with Matchers with Section {

  /**
   * =Custom key types=
   *
   * If you need to encode/decode `Map[K, V]` where `K` is not `String` (or `Symbol`, `Int`, `Long`, etc), you need to provide a `KeyEncoder`
   * and/or `KeyDecoder` for your custom key type.
   *
   * For example:
   * {{{
   *   import io.circe._, import io.circe.syntax._
   *
   *   case class Foo(value: String)
   *
   *
   *   implicit val fooKeyEncoder = new KeyEncoder[Foo] {
   *     override def apply(foo: Foo): String = foo.value
   *   }
   *
   *   val map = Map[Foo, Int](
   *     Foo("hello") -> 123,
   *     Foo("world") -> 456
   *   }
   *
   *   val json = map.asJson
   *
   *   implicit val fooKeyDecoder = new KeyDecoder[Foo] {
   *     override def apply(key: String): Option[Foo] = Some(Foo(key))
   *   }
   *
   *   json.as[Map[Foo, Int]]
   * }}}
   *
   * What would be returned as a result of decoding and traversing the returned `Map`:
   */
  def mapJson(res0: Either[String, Int]) =
    json.hcursor.downField("hello").as[Int] should be(res0)

  /**
   * =Custom key mappings via annotations=
   *
   * It’s often necessary to work with keys in your JSON objects that aren’t idiomatic case class member names in
   * Scala. While the standard generic derivation doesn’t support this use case, the experimental circe-generic-extras
   * module does provide two ways to transform your case class member names during encoding and decoding.
   *
   * In many cases the transformation is as simple as going from camel case to snake case, in which case all you need
   * is a custom implicit configuration:
   * {{{
   *   import io.circe.generic.extras._, io.circe.syntax._
   * }}}
   *
   */
  def basicCustomKeyMapping(res0: String) = {
    implicit val config: Configuration = Configuration.default.withSnakeCaseMemberNames

    @ConfiguredJsonCodec case class User(firstName: String, lastName: String)

    User("Foo", "McBar").asJson.noSpaces shouldBe res0
  }

  /**
   * In other cases you may need more complex mappings. These can be provided as a function:
   */
  def complexCustomKeyMapping(res0: String) = {
    implicit val config: Configuration = Configuration.default.copy(
      transformMemberNames = {
        case "i"   => "my-int"
        case other => other
      }
    )

    @ConfiguredJsonCodec case class Bar(i: Int, s: String)

    Bar(13, "Qux").asJson.noSpaces shouldBe res0
  }

  /**
   * Since this is a common use case, we also support for mapping member names via an annotation:
   */
  def mappingMemberAnnotation(res0: String) = {
    implicit val config: Configuration = Configuration.default

    @ConfiguredJsonCodec case class Bar(@JsonKey("my-int") i: Int, s: String)

    Bar(13, "Qux").asJson.noSpaces shouldBe res0
  }

}
