seq(webSettings :_*)

organization := "Lift"

name := "seventhings"

version := "0.3"

scalaVersion := "2.10.3"

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  val liftVersion = "2.6"
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion,
    "net.liftweb" %% "lift-wizard" % liftVersion,
    "org.mortbay.jetty" % "jetty" % "6.1.22" % "container; test",
    "junit" % "junit" % "4.10" % "test",
    "ch.qos.logback" % "logback-classic" % "1.0.6",
    "org.specs2" %% "specs2" % "1.11" % "test",
    "com.h2database" % "h2" % "1.3.167",
    "com.github.jsimone" % "webapp-runner" % "7.0.57.2" excludeAll(ExclusionRule("spy"))
  )
}

val stage = taskKey[Unit]("Stage task")

val Stage = config("stage")

stage := {
  (packageWar in Compile).value
  (update in Stage).value.allFiles.foreach { f =>
    if (f.getName.matches("webapp-runner-[0-9\\.]+.jar")) {
      println("copying " + f.getName)
      IO.copyFile(f, baseDirectory.value / "target" / "webapp-runner.jar")
    }
  }
}
