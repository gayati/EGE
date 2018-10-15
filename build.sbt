name := """Demo_App"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

libraryDependencies += guice

libraryDependencies += "com.typesafe.play" %% "play-test" % "2.6.18" % Test

libraryDependencies ++= Seq(
  "org.reactivemongo" %% "play2-reactivemongo" % "0.16.0-play26"
)


libraryDependencies ++= Seq(
  	  	"com.github.seratch"	%%  "awscala" 			% "0.5.+"
)

libraryDependencies += "com.sksamuel.scrimage" %% "scrimage-core" % "3.0.0-alpha4"

libraryDependencies += "com.sksamuel.scrimage" %% "scrimage-io-extra" % "3.0.0-alpha4"

libraryDependencies += "com.sksamuel.scrimage" %% "scrimage-filters" % "3.0.0-alpha4"



libraryDependencies +=  "org.apache.tika" % "tika-core" % "1.4"
libraryDependencies +=  "org.apache.tika" % "tika-parsers" % "1.4"