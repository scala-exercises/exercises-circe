val scalaExerciesV = "0.4.0-SNAPSHOT"
val circeVersion = "0.7.0"

def dep(artifactId: String) = "org.scala-exercises" %% artifactId % scalaExerciesV

lazy val `circe` = (project in file("."))
  .enablePlugins(ExerciseCompilerPlugin)
  .settings(
    name         := "exercises-circe",
    libraryDependencies ++= Seq(
      dep("exercise-compiler"),
      dep("definitions"),
      %%("circe-core"),
      %%("circe-generic"),
      %%("circe-parser"),
      "io.circe" %% "circe-optics" % circeVersion,
      %%("shapeless"),
      %%("cats-core"),
      %%("scalatest"),
      %%("scalacheck"),
      %%("scheckShapeless")
    )
  )

// Distribution

pgpPassphrase := Some(getEnvVar("PGP_PASSPHRASE").getOrElse("").toCharArray)
pgpPublicRing := file(s"$gpgFolder/pubring.gpg")
pgpSecretRing := file(s"$gpgFolder/secring.gpg")
