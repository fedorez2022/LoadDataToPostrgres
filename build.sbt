ThisBuild / version := "0.1"

ThisBuild / scalaVersion := "2.11.8"

lazy val sparkVersion = "2.4.3"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.postgresql" % "postgresql" % "42.6.0"
)

lazy val root = (project in file("."))
  .settings(
    name := "LoadData"
  )