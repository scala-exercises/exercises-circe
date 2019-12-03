/*
 *  scala-exercises - exercises-circe
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package circelib

import org.scalaexercises.Test
import org.scalacheck.ScalacheckShapeless._
import org.scalatest.refspec.RefSpec
import org.scalatestplus.scalacheck.Checkers
import shapeless.HNil

class EncodingDecodingSpec extends RefSpec with Checkers {

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

}
