/*
 * scala-exercises - exercises-circe
 * Copyright (C) 2015-2016 47 Degrees, LLC. <http://www.47deg.com>
 */

package circelib

import org.scalacheck.Shapeless._
import org.scalaexercises.Test
import org.scalatest.Spec
import org.scalatest.prop.Checkers
import shapeless.HNil

class TraversingSpec extends Spec with Checkers {

  def `move as` = {
    check(
      Test.testSuccess(
        TraversingSection.moveFocus _,
        (Right[String, Double](100.001): Either[String, Double]) :: HNil
      )
    )
  }

  def `move as2` = {
    check(
      Test.testSuccess(
        TraversingSection.moveFocus2 _,
        (Right[String, Double](100.001): Either[String, Double]) :: HNil
      )
    )
  }

  def `move as3` = {
    check(
      Test.testSuccess(
        TraversingSection.moveFocus3 _,
        (Right[String, String]("b"): Either[String, String]) :: HNil
      )
    )
  }

  def `modify Json` = {
    check(
      Test.testSuccess(
        TraversingSection.modifyJson _,
        (Right[String, String]("ooF"): Either[String, String]) :: HNil
      )
    )
  }
}