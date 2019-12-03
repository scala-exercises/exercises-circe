/*
 *  scala-exercises - exercises-circe
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package circelib

import org.scalacheck.ScalacheckShapeless._
import org.scalaexercises.Test
import org.scalatest.refspec.RefSpec
import org.scalatestplus.scalacheck.Checkers
import shapeless.HNil

class CustomCodecsSpec extends RefSpec with Checkers {

  import CustomCodecsSection._

  def `map Json` = {
    check(
      Test.testSuccess(
        mapJson _,
        (Right[String, Int](123): Either[String, Int]) :: HNil
      )
    )
  }

  def `basic custom key mapping` =
    check(
      Test.testSuccess(
        basicCustomKeyMapping _,
        """{"first_name":"Foo","last_name":"McBar"}""" :: HNil
      )
    )

  def `complex custom key mapping` =
    check(Test.testSuccess(complexCustomKeyMapping _, """{"my-int":13,"s":"Qux"}""" :: HNil))

  def `mapping member annotation` =
    check(Test.testSuccess(mappingMemberAnnotation _, """{"my-int":13,"s":"Qux"}""" :: HNil))

}
