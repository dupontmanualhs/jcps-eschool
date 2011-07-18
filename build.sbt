name := "JCPS eSchool"

version := "0.1"

organization := "net.jcpsky"

scalaVersion := "2.9.0-1"

seq(WebPlugin.webSettings: _*)

libraryDependencies ++= {
  val liftVersion = "2.4-M2"
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-wizard" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-mongodb-record" % liftVersion)
}


libraryDependencies ++= {
  Seq(
    "org.eclipse.jetty" % "jetty-webapp" % "7.3.0.v20110203" % "jetty",
    "ch.qos.logback" % "logback-classic" % "0.9.26",
    "com.novocode" % "junit-interface" % "0.6" % "test->default",
    "org.scala-tools.testing" %% "specs" % "1.6.8" % "test->default",
    "org.scalatest" % "scalatest_2.9.0" % "1.6.1" % "test->default",
    "org.seleniumhq.selenium" % "selenium-java" % "2.0.0" % "test->default"
  )
}

scalacOptions ++= Seq("-deprecation", "-unchecked") 
