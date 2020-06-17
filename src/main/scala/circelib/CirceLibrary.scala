/*
 * Copyright 2016-2020 47 Degrees Open Source <https://www.47deg.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package circelib

import org.scalaexercises.definitions

/**
 * circe (pronounced SUR-see, or KEER-kee in classical Greek) is a JSON library for Scala (and Scala.js).
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
