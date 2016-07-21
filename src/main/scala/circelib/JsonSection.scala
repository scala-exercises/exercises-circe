package circelib

import io.circe.Json
import org.scalaexercises.definitions
import org.scalatest._
import circelib.helpers.JsonHelpers

/** @param name Json
  */
object JsonSection extends FlatSpec with Matchers with definitions.Section {

  import JsonHelpers._

  /** Json is the circe data type to represent a Json object. It is a very useful data type to be familiar with since
    * it's how circe is modeling the base type we want to deal with.
    *
    * Firstly, let's talk briefly about the shape of everyt Json object. It basically is semi structured data build on
    * top of key-value pairs. These key-value pairs have a specific shape:
    *   - keys are strings
    *   - values can be of multiple types.
    *
    * Then, to model a real json object we need to support different data types on the value field. For this purposes
    * we have different available methods so we can create a Json object from different source data types. Some examples
    * of these methods are `fromString`, `fromBoolean`, `fromDouble` and so on. For further details about all possible
    * methods, see https://travisbrown.github.io/circe/api/#io.circe.Json$
    *
    * Let's see how these methods work.
    *
    * {{{
    *   scala> // fromString example
    *   scala> val jsonString: Json = Json.fromString("scala exercises")
    *   jsonString: io.circe.Json = "scala exercises"
    *
    *   scala> // fromDouble example
    *   scala> val jsonDouble: Option[Json] = Json.fromDouble(1)
    *   jsonDouble: Option[io.circe.Json] = Some(1.0)
    *
    *   scala> val jsonDouble: Option[Json] = Json.fromDouble(1)
    *   jsonString: Option[io.circe.Json] = Some(1.0)
    *
    *   scala> // fromFields example
    *   scala> val fieldList = List(
    *           ("key1", Json.fromString("value1")),
    *           ("key2", Json.fromInt(1)))
    *   fieldList: List[(String, io.circe.Json)] = List((key1,"value1"), (key2,1))
    *
    *   scala> val jsonFromFields: Json = Json.fromFields(fieldList)
    *   jsonFromFields: io.circe.Json =
    *   {
    *     "key1" : "value1",
    *     "key2" : 1
    *   }
    *
    * }}}
    *
    *
    * In addition, there are some other methods that allow you to convert from a Json object to a String.
    *
    * {{{
    *
    *   scala> jsonFromFields.noSpaces
    *   res0: String = {"name":"sample json","version":1,"data":{"done":false,"rate":4.9}}
    *
    *   scala> jsonFromFields.spaces2
    *   res1: String =
    *   {
    *     "name" : "sample json",
    *     "version" : 1,
    *     "data" : {
    *       "done" : false,
    *       "rate" : 4.9
    *     }
    *   }
    *
    *   scala> jsonFromFields.spaces4
    *   res2: String =
    *   {
    *       "name" : "sample json",
    *       "version" : 1,
    *       "data" : {
    *           "done" : false,
    *           "rate" : 4.9
    *       }
    *   }
    *
    * }}}
    *
    * What would be the string output for our jsonFromFields value?
    */
  def jsonToString(res0: String) = {

    jsonFromFields.noSpaces should be (res0)
  }

   /** Let's see how we can use these methods to create custom Jsons that represents specific Json strings.
    */
  def jsonObject(res0: Json, res1: (String, Json), res2: (String, Json), res3: Json) = {

    "{\"key\":\"value\"}" should be(res0.noSpaces)

    "{\"name\":\"sample json\",\"data\":{\"done\":false}}" should be (Json.fromFields(List(
      res1,
      res2)
    ).noSpaces)

    "[{\"x\":1}]" should be (res3.noSpaces)

  }

  /** Furthemore, there are some other methods that allows you to deal with Json objects and apply transformation. We
    * can use them to modify or apply any changes to a given Json object in a simpler way as if we have to deal with it
    * manually.
    *
    * We are going to start from this Json array:
    *
    * {{{
    *   val jsonArray: Json = Json.fromValues(List(
    *     Json.fromFields(List(("field1", Json.fromInt(1)))),
    *     Json.fromFields(List(
    *       ("field1", Json.fromInt(200)),
    *       ("field2", Json.fromString("Having circe in Scala Exercises is awesome"))
    *     ))
    *   ))
    *
    * }}}
    *
    * Finally, we have a transformJson method:
    *
    * {{{
    *   def transformJson(jsonArray: Json): Json =
    *     jsonArray mapArray { oneJson: List[Json] =>
    *       oneJson.init
    *     }
    * }}}
    *
    * So, having them, what's should be the result if we apply our transformJson function to our jsonArray value?
    *
    */

  def jsonClass(res0: String) = {

    transformJson(jsonArray).noSpaces should be (res0)

  }
}
