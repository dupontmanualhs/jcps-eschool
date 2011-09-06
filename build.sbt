name := "JCPS eSchool"

version := "0.1"

organization := "net.jcpsky"

scalaVersion := "2.9.0-1"

checksums := Nil

resolvers += ScalaToolsSnapshots

resolvers += "java.net Maven2 Repo" at "http://download.java.net/maven/2/"

seq(webSettings: _*)

libraryDependencies ++= {
  val liftVersion = "2.4-SNAPSHOT"
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-wizard" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-mongodb-record" % liftVersion,
    "com.foursquare" %% "rogue" % "1.0.15")
}


libraryDependencies ++= {
  Seq(
    "org.eclipse.jetty" % "jetty-webapp" % "7.3.0.v20110203" % "jetty",
    "ch.qos.logback" % "logback-classic" % "0.9.26",
    "com.novocode" % "junit-interface" % "0.6" % "test->default",
    "org.scala-tools.testing" %% "specs" % "1.6.8" % "test->default",
    "org.scalatest" % "scalatest_2.9.0" % "1.6.1" % "test->default",
    "org.seleniumhq.selenium" % "selenium-java" % "2.0.0" % "test->default",
    "org.apache.poi" % "poi" % "3.8-beta3",
    "org.apache.poi" % "poi-ooxml" % "3.8-beta3"
  )
}

scalacOptions ++= Seq("-deprecation", "-unchecked") 
