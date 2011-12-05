/*
import sbt._
import Keys._

import org.datanucleus.enhancer.tools.EnhancerTask
import org.apache.tools.ant.types.FileSet

object MyBuild extends Build {
  object Enhancer {
	def run() {
      val task: EnhancerTask = new EnhancerTask()
      val fileSet: FileSet = new FileSet()
      fileSet.setIncludes("")
    
      task.setVerbose(true)
      task.addFileSet(fileSet)
      task.execute()
    }
  }
  
  val enhance = TaskKey[Unit]("enhance", "run the DataNucleus enhancer on model classes")

}
*/