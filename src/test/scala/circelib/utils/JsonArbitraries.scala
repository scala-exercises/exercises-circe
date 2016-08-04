package circelib.utils

import io.circe.syntax._
import io.circe.{Encoder, Json}
import org.scalacheck.{Arbitrary, Gen}
import org.scalacheck.Gen._

object JsonArbitraries {

    implicit def arbJson: Arbitrary[Json] = Arbitrary(alphaStr map (_.asJson))

    implicit def arbStringJsonPair: Arbitrary[(String, Json)] = Arbitrary(alphaStr map (string => (string, string.asJson)))
}
