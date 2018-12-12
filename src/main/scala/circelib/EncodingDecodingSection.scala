/*
 * scala-exercises - exercises-circe
 * Copyright (C) 2015-2016 47 Degrees, LLC. <http://www.47deg.com>
 */

package circelib

import org.scalatest._
import io.circe.parser.decode
import io.circe.syntax._
import io.circe.generic.auto._

/** @param name Encoding and decoding
 */
object EncodingDecodingSection
    extends FlatSpec
    with Matchers
    with org.scalaexercises.definitions.Section {

  import circelib.helpers.EncodingHelpers._

  /**
   * Circe uses `Encoder` and `Decoder` type classes for encoding and decoding. An `Encoder[A]` instance provides a function
   * that will convert any `A` to a `Json` and a `Decoder[A]` takes a `Json` value to return either an exception or an `A`. Circe provides
   * implicit instances of these type classes for many types from the Scala standard library, including `Int`, `String`, and others.
   * It also provides instances for `List[A]`, `Option[A]`, and other generic types, but only if `A` has an `Encoder` instance.
   *
   * Encoding data to `Json` can be done using the `.asJson` syntax.
   *
   * {{{
   *   import io.circe.syntax._
   *
   *   val intsJson = List(1, 2, 3).asJson
   *   // intsJson: io.circe.Json =
   *   // [
   *   //   1,
   *   //   2,
   *   //   3
   *   // ]
   * }}}
   *
   * Use the `.as` syntax for decoding data from `Json`:
   *
   * {{{
   *   intsJson.as[List[Int]]
   *   // res0: io.circe.Decoder.Result[List[Int]] = Right(List(1, 2, 3))
   * }}}
   *
   * The decode function from the included [parser] module can be used to directly decode a JSON String
   *
   * {{{
   *   import io.circe.parser.decode
   * }}}
   *
   *  Let's decode a JSON String:
   */
  def decodeJson(res0: Boolean, res1: Either[String, List[Int]]): Unit = {
    val decodeList = decode[List[Int]]("[1, 2, 3]")

    decodeList.isRight should be(res0)

    decodeList should be(res1)
  }

  /**
   * ==Semi-automatic derivation
   *
   * Sometimes it's convenient to have an `Encoder` or `Decoder` defined in your code, and '''semi-automatic''' derivation can help. You´d write:
   *
   * {{{
   *   import io.circe._, io.circe.generic.semiauto._
   *
   *   case class Foo(a: Int, b: String, c: Boolean)
   *   implicit val fooDecoder: Decoder[Foo] = deriveDecoder[Foo]
   *   implicit val fooEncoder: Encoder[Foo] = deriveEncoder[Foo]
   * }}}
   *
   * Or simply:
   *
   * {{{
   *   implicit val fooDecoder: Decoder[Foo] = deriveDecoder
   *   implicit val fooEncoder: Encoder[Foo] = deriveEncoder
   * }}}
   *
   * ==@JsonCodec==
   *
   *  The circe-generic projects includes a `@JsonCodec` annotation that simplifies the use of semi-automatic generic derivation:
   *
   *  {{{
   *    import io.circe.generic.JsonCodec, io.circe.syntax._
   *
   *    @JsonCodec case class Bar(i: Int, s: String)
   *
   *    Bar(13, "Qux").asJson
   *    // res4: io.circe.Json =
   *    // {
   *    //   "i" : 13,
   *    //   "s" : "Qux"
   *    // }
   *  }}}
   *
   *  This works both case classes and sealed trait hierarchies.
   *  NOTE: You will need the [[http://docs.scala-lang.org/overviews/macros/paradise.html Macro Paradise]] plugin to use annotation macros like `@JsonCodec`
   *
   *  ==forProductN helper methods==
   *
   * It's also possible to construct encoders and decoders for case class-like types in a relatively boilerplate-free way without generic derivation:
   * {{{
   *   case class User(id: Long, firstName: String, lastName: String)
   *
   *   object UserCodec {
   *     implicit val decodeUser: Decoder[User] =
   *       Decoder.forProduct3("id", "first_name", "last_name")(User.apply)
   *
   *     implicit val encodeUser: Encoder[User] =
   *       Encoder.forProduct3("id", "first_name", "last_name")(u =>
   *         (u.id, u.firstName, u.lastName)
   *       )
   *    }
   * }}}
   *
   *  It’s not as clean or as maintainable as generic derivation, but it’s less magical, it requires nothing but circe-core, and if you need a custom name
   *  mapping it’s currently the best solution (although 0.6.0 introduces experimental configurable generic derivation in the circe-generic-extras module).
   *
   *
   * ==Fully automatic derivation==
   *
   *  It is also possible to derive an `Encoder` and `Decoder` for many types with no boilerplate at all.
   *  Circe uses [[https://github.com/milessabin/shapeless shapeless]] to automatically derive the necessary type class instances:
   *
   *  Let´s see what happens when we create a `Json` with derived fields
   *
   *  For this example we need to import `io.circe.generic.auto._`
   */
  def automaticDerivation(res0: Either[String, String]): Unit = {
    case class Person(name: String)

    case class Greeting(salutation: String, person: Person, exclamationMarks: Int)

    val greetingJson = Greeting("Hey", Person("Chris"), 3).asJson

    greetingJson.hcursor.downField("person").downField("name").as[String] should be(res0)
  }

  /**
   *  ==Custom encoders/decoders
   *
   *  If you want to write your own codec instead of using automatic or semi-automatic derivation, you can do so in a couple of ways.
   *
   *  Firstly, you can write a new `Encoder[A]` and `Decoder[A]` from scratch
   *
   *  {{{
   *    class Thing()
   *
   *    implicit val encodeFoo: Encoder[Thing] = new Encoder[Thing] {
   *       final def apply(a: Thing): Json = ??? // your implementation goes here
   *    }
   *
   *    implicit val decodeFoo: Decoder[Thing] = new Decoder[Thing] {
   *       final def apply(c: HCursor): Decoder.Result[Thing] = Left(DecodingFailure("Not implemented yet", c.history))
   *    }
   *  }}}
   *
   *  But in many cases you might find it more convenient to piggyback on top of the decoders that are already available. For example, a codec for
   *  `java.time.Instant` might look like this:
   *  {{{
   *    import cats.syntax.either._
   *
   *    import java.time.Instant
   *
   *    implicit val encodeInstant: Encoder[Instant] = Encoder.encodeString.contramap[Instant](_.toString)
   *
   *    implicit val decodeInstant: Decoder[Instant] = Decoder.decodeString.emap { str =>
   *       Either.catchNonFatal(Instant.parse(str)).leftMap(t => "Instant")
   *     }
   *  }}}
   *
   * ==Custom key types==
   *
   * If you need to encode/decode `Map[K, V]` where `K` is not `String` (or `Symbol`, `Int`, `Long`, etc), you need to provide a `KeyEncoder`
   * and/or `KeyDecoder` for your custom key type.
   *
   * For example:
   * {{{
   *   import io.circe.syntax._
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
   *   )
   *
   *   implicit val fooKeyDecoder = new KeyDecoder[Foo] {
   *     override def apply(key: String): Option[Foo] = Some(Foo(key))
   *   }
   *
   *   val json = map.asJson
   * }}}
   *
   * What would be returned as a result of decoding and traversing the returned `Map`:
   */
  def mapJson(res0: Either[String, Int]): Unit =
    json.hcursor.downField("hello").as[Int] should be(res0)

}
