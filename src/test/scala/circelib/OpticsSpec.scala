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

class OpticsSpec extends RefSpec with Checkers {

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

  def `recursively modify optics` = {
    check(
      Test.testSuccess(
        OpticsSection.recursiveModifyJsonOptics _,
        Option("123.45") :: HNil
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
