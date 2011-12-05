name := "JCPS eSchool"

version := "0.1"

organization := "net.jcpsky"

scalaVersion := "2.9.1"

seq(webSettings: _*)

libraryDependencies ++= {
  val liftVersion = "2.4-M4"
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default",
    "net.liftweb" %% "lift-wizard" % liftVersion % "compile->default")
}


libraryDependencies ++= {
  Seq(
    "org.eclipse.jetty" % "jetty-webapp" % "7.3.0.v20110203" % "jetty",
    "javax.servlet" % "servlet-api" % "2.5" % "provided->default",
    "ch.qos.logback" % "logback-classic" % "0.9.26",
    "com.novocode" % "junit-interface" % "0.6" % "test->default",
    "org.scalatest" % "scalatest_2.9.1" % "1.6.1" % "test->default",
    "org.seleniumhq.selenium" % "selenium-java" % "2.0.0" % "test->default",
    "org.apache.poi" % "poi" % "3.8-beta3",
    "org.apache.poi" % "poi-ooxml" % "3.8-beta3"
  )
}

// JDO dependencies
libraryDependencies ++= Seq(
  "org.datanucleus" % "datanucleus-core" % "3.0.3",
  "org.datanucleus" % "datanucleus-api-jdo" % "3.0.3",
  "org.datanucleus" % "datanucleus-enhancer" % "3.0.1",
  "org.apache.ant" % "ant" % "[1.7, )",
  "org.datanucleus" % "datanucleus-jdo-query" % "3.0.1",
  "asm" % "asm" % "3.3.1",
  "javax.jdo" % "jdo-api" % "3.0",
  "org.datanucleus" % "datanucleus-rdbms" % "3.0.3",
  "org.datanucleus" % "datanucleus-jodatime" % "3.0.1"
)

// H2 dependencies
libraryDependencies ++= Seq(
  "com.h2database" % "h2" % "1.3.160"
)

//enhance <<= Enhancer.run()

scalacOptions ++= Seq("-deprecation", "-unchecked") 

compileOrder := CompileOrder.JavaThenScala
