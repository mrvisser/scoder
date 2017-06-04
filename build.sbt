
name := "scoder-root"

organization in ThisBuild := "ca.mrvisser"

lazy val commonScalacOptions = Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-unchecked",
  "-Xfatal-warnings",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture"
)

lazy val publishSettings = Seq(
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  pomIncludeRepository := { _ => false },
  useGpg := true,
  usePgpKeyHex("D2851404")
)

lazy val noPublishSettings = Seq(
  publish := (),
  publishLocal := (),
  publishArtifact := false
)

lazy val commonSettings = Seq(
  crossScalaVersions := Seq("2.10.6", "2.11.11", "2.12.2"),
  scalacOptions ++= commonScalacOptions,
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  ),
  libraryDependencies ++= Seq(
    "org.scalacheck" %% "scalacheck" % "1.13.4" % "test"
  ),
  fork in test := true,
  parallelExecution in Test := false
)

lazy val core = project
  .settings(commonSettings)
  .settings(publishSettings)

lazy val root = project.in(file("."))
  .aggregate(core)
  .settings(commonSettings)
  .settings(noPublishSettings)
