import scala.xml.{PrettyPrinter, XML}

val doc = XML.loadFile(args(0))
val pp = new PrettyPrinter(200, 2)
println(pp.formatNodes(doc))

