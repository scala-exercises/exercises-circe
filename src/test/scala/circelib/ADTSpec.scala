/*
 *  scala-exercises - exercises-circe
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package circelib

import circelib.helpers.ADTHelpers.{Event, Foo}
import org.scalacheck.ScalacheckShapeless._
import org.scalaexercises.Test
import org.scalatest.refspec.RefSpec
import org.scalatestplus.scalacheck.Checkers
import shapeless.HNil

class ADTSpec extends RefSpec with Checkers {

  def `generic derivation`() =
    check(
      Test.testSuccess(
        ADTSection.genericDerivation _,
        (Right(Foo(1000)): Either[String, Event]) :: """{"i":100}""" :: HNil
      )
    )

  def `shapes derivation`() =
    check(
      Test.testSuccess(
        ADTSection.shapesDerivation _,
        (Right(Foo(1000)): Either[String, Event]) :: """{"i":100}""" :: HNil
      )
    )

  def `generic extras ADT`() =
    check(
      Test
        .testSuccess(
          ADTSection.genericExtrasADT _,
          (Right(Foo(1000)): Either[String, Event]) :: HNil
        )
    )

}
