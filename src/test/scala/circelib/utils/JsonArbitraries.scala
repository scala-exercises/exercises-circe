/*
 *  scala-exercises - exercises-circe
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package circelib.utils

import io.circe.Json
import io.circe.syntax._
import org.scalacheck.Arbitrary
import org.scalacheck.Gen._

object JsonArbitraries {

  implicit def arbJson: Arbitrary[Json] = Arbitrary(alphaStr map (_.asJson))

  implicit def arbStringJsonPair: Arbitrary[(String, Json)] =
    Arbitrary(alphaStr map (string => (string, string.asJson)))
}
