import ProjectPlugin.autoImport._

val scalaExercisesV = "0.6.0-SNAPSHOT"

def dep(artifactId: String) = "org.scala-exercises" %% artifactId % scalaExercisesV

lazy val `circe` = (project in file("."))
  .enablePlugins(ExerciseCompilerPlugin)
  .settings(
    name := "exercises-circe",
    libraryDependencies ++= Seq(
      dep("exercise-compiler"),
      dep("definitions"),
      "io.circe" %% "circe-core"           % V.circe,
      "io.circe" %% "circe-generic"        % V.circe,
      "io.circe" %% "circe-parser"         % V.circe,
      "io.circe" %% "circe-generic-extras" % V.circeGenericExtras,
      "io.circe" %% "circe-shapes"         % V.circe,
      "io.circe" %% "circe-optics"         % V.circeOptics,
      %%("shapeless", V.shapeless),
      %%("cats-core", V.cats),
      %%("scalatest", V.scalatest),
      %%("scalacheck", V.scalacheck),
      "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % V.scalacheckShapeless,
      "org.scalatestplus"          %% "scalatestplus-scalacheck"  % V.scalatestplusScheck
    )
  )

// Distribution

pgpPassphrase := Some(getEnvVar("PGP_PASSPHRASE").getOrElse("").toCharArray)
pgpPublicRing := file(s"$gpgFolder/pubring.gpg")
pgpSecretRing := file(s"$gpgFolder/secring.gpg")
