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

package circelib

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import io.circe.parser.decode
import io.circe.syntax._
import io.circe.generic.auto._

/** @param name Encoding and decoding
 */
object EncodingDecodingSection
    extends AnyFlatSpec
    with Matchers
    with org.scalaexercises.definitions.Section {

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
  def decodeJson(res0: Boolean, res1: Either[String, List[Int]]) = {
    val decodeList = decode[List[Int]]("[1, 2, 3]")

    decodeList.isRight should be(res0)

    decodeList should be(res1)
  }

  /**
   * =Semi-automatic derivation=
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
   * =@JsonCodec=
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
   *  =forProductN helper methods=
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
   *  It’s not as clean or as maintainable as generic derivation, but it’s less magical, it requires nothing but `circe-core`, and if you need a custom name
   *  mapping it’s currently the best solution (although `0.6.0` introduces experimental configurable generic derivation in the `circe-generic-extras` module).
   *
   *
   * =Automatic derivation=
   *
   *  It is also possible to derive an `Encoder` and `Decoder` for many types with no boilerplate at all.
   *  Circe uses [[https://github.com/milessabin/shapeless shapeless]] to automatically derive the necessary type class instances:
   *
   *  Let´s see what happens when we create a `Json` with derived fields
   *
   *  For this example we need to import `io.circe.generic.auto._`
   */
  def automaticDerivation(res0: Either[String, String]) = {
    case class Person(name: String)

    case class Greeting(salutation: String, person: Person, exclamationMarks: Int)

    val greetingJson = Greeting("Hey", Person("Chris"), 3).asJson

    greetingJson.hcursor.downField("person").downField("name").as[String] should be(res0)
  }

}
