/*
 * scala-exercises - exercises-circe
 * Copyright (C) 2015-2016 47 Degrees, LLC. <http://www.47deg.com>
 */

package circelib.utils

import io.circe.syntax._
import io.circe.{Encoder, Json}
import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Gen._

object JsonArbitraries {

  implicit def arbJson: Arbitrary[Json] = Arbitrary(alphaStr map (_.asJson))

  implicit def arbStringJsonPair: Arbitrary[(String, Json)] =
    Arbitrary(alphaStr map (string => (string, string.asJson)))
}
