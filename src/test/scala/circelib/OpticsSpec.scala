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

class OpticsSpec extends Spec with Checkers {

  def `check traversing optics` = {
    check(
      Test.testSuccess(
        OpticsSection.checkTraversingOptics _,
        Option("1 Fake Street, London, England") :: HNil
      )
    )
  }

  def `check traversing optics 2` = {
    check(
      Test.testSuccess(
        OpticsSection.checkTraversingOptics2 _,
        List("banana", "apple") :: HNil
      )
    )
  }

  def `modify optics` = {
    check(
      Test.testSuccess(
        OpticsSection.modifyingJsonOptics _,
        List(2, 4) :: HNil
      )
    )
  }

  def `modify optics dynamic` = {
    check(
      Test.testSuccess(
        OpticsSection.modifyingJsonDynamic _,
        false :: HNil
      )
    )
  }

}