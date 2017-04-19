/*
 * scala-exercises - exercises-circe
 * Copyright (C) 2015-2016 47 Degrees, LLC. <http://www.47deg.com>
 */

package circelib

import org.scalaexercises.Test
import org.scalacheck.Shapeless._
import org.scalatest.Spec
import org.scalatest.prop.Checkers
import shapeless.HNil

class EncodingDecodingSpec extends Spec with Checkers {

  def `decode Json` = {
    check(
      Test.testSuccess(
        EncodingDecodingSection.decodeJson _,
        true :: (Right[String, List[Int]](List(1, 2, 3)): Either[String, List[Int]]) :: HNil
      )
    )
  }

  def `automatic derivation Json` = {
    check(
      Test.testSuccess(
        EncodingDecodingSection.automaticDerivation _,
        (Right[String, String]("Chris"): Either[String, String]) :: HNil
      )
    )
  }

  def `map Json` = {
    check(
      Test.testSuccess(
        EncodingDecodingSection.mapJson _,
        (Right[String, Int](123): Either[String, Int]) :: HNil
      )
    )
  }

}