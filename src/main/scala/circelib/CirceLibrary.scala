package circelib


import org.scalaexercises.definitions

/** circe (pronounced SUR-see, or KEER-kee in classical Greek) is a JSON library for Scala (and Scala.js).
  *
  * @param name circe
  */
object CirceLibrary extends definitions.Library {
  override def owner = "scala-exercises"
  override def repository = "exercises-circe"

  override def color = Some("#996666")

  override def sections = List(JsonSection)
}
