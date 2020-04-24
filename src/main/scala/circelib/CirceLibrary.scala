/*
 *  scala-exercises - exercises-circe
 *  Copyright (C) 2015-2019 47 Degrees, LLC. <http://www.47deg.com>
 *
 */

package circelib

import org.scalaexercises.definitions

/** circe (pronounced SUR-see, or KEER-kee in classical Greek) is a JSON library for Scala (and Scala.js).
 *
 * @param name circe
 */
object CirceLibrary extends definitions.Library {
  override def owner      = "scala-exercises"
  override def repository = "exercises-circe"

  override def color = Some("#996666")

  override def sections =
    List(
      JsonSection,
      TraversingSection,
      EncodingDecodingSection,
      CustomCodecsSection,
      ADTSection,
      OpticsSection
    )

  override def logoPath = "circe"
}
