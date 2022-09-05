import com.jsuereth.sbtpgp.PgpKeys.publishSigned

ThisBuild / organization       := "org.scala-exercises"
ThisBuild / githubOrganization := "47degrees"
ThisBuild / scalaVersion       := "2.13.8"

// Required to prevent errors for eviction from binary incompatible dependency
// resolutions.
// See also: https://github.com/scala-exercises/exercises-cats/pull/267
ThisBuild / libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % "always"

// This is required by the exercises compiler:
publishLocal  := (publishLocal dependsOn compile).value
publishSigned := (publishSigned dependsOn compile).value

addCommandAlias("ci-test", "scalafmtCheckAll; scalafmtSbtCheck; test")
addCommandAlias("ci-docs", "github; documentation/mdoc; headerCreateAll")
addCommandAlias("ci-publish", "github; ci-release")

lazy val exercises = (project in file("."))
  .settings(name := "exercises-circe")
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-exercises"        %% "exercise-compiler"         % "0.7.1",
      "org.scala-exercises"        %% "definitions"               % "0.7.1",
      "org.typelevel"              %% "cats-core"                 % "2.8.0",
      "io.circe"                   %% "circe-core"                % "0.14.2",
      "io.circe"                   %% "circe-generic"             % "0.14.2",
      "io.circe"                   %% "circe-parser"              % "0.14.2",
      "io.circe"                   %% "circe-generic-extras"      % "0.14.2",
      "io.circe"                   %% "circe-shapes"              % "0.14.2",
      "io.circe"                   %% "circe-optics"              % "0.14.1",
      "com.chuusai"                %% "shapeless"                 % "2.3.9",
      "org.scalatest"              %% "scalatest"                 % "3.2.13",
      "org.scalacheck"             %% "scalacheck"                % "1.16.0",
      "org.scalatestplus"          %% "scalacheck-1-14"           % "3.2.2.0",
      "com.github.alexarchambault" %% "scalacheck-shapeless_1.15" % "1.3.0"
    ),
    scalacOptions += "-Ymacro-annotations",
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full)
  )
  .enablePlugins(ExerciseCompilerPlugin)

lazy val documentation = project
  .settings(mdocOut := file("."))
  .settings(publish / skip := true)
  .enablePlugins(MdocPlugin)
