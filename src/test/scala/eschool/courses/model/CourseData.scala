package eschool.courses.model

import model.Course
import net.liftweb.http.js.JE.ValById
import eschool.users.model.User
import xml.NodeSeq
import org.apache.derby.iapi.sql.dictionary.UniqueSQLObjectDescriptor


object CourseData {
  //COURSES ------------------------------------------------------------------------------------------------------------
  val intro = Course.createRecord.name("Intro to Computer Programming").stateId("123456").objectives(List()).content(List())
  val apCS = Course.createRecord.name("AP Computer Science A").stateId("234567").objectives(List()).content(List())

  //SECTIONS ------------------------------------------------------------------------------------------------------------
  val introR3 = Section.createRecord.course.id.get(intro).content(List()).terms(List()).teachers(OBTeachers).students(introR3Students)
  val introR4 = Section.createRecord.course.id.get(intro).content(List()).terms(List()).teachers(OBTeachers).students(introR4Students)
  val apCSR1 = Section.createRecord.course.id.get(apCS).content(List()).terms(List()).teachers(OBTeachers).students(apCSR1Students)
  val apCSW3 = Section.createRecord.course.id.get(apCS).content(List()).terms(List()).teachers(OBTeachers).students(apCSW3Students)

  //STUDENTS -----------------------------------------------------------------------------------------------------------
  val apCSR1Students = List[DbUser](
    new DbUser("sallen13", "Samuel", "G", "Allen", "Sam", true, "mike@jcpsky.net", "Sallen13", "G4"),
    new DbUser("zbrewer13", "Zachary", "Robert", "Brewer", "Zach", true, "bob@jcpsky.net", "Zbrewer13", "G1"),
    new DbUser("tchea13", "Tiffany", "K", "Chea", null, false, "mary@stu.jcpsky.net", "Tchea13", "G2"),
    new DbUser("achen13", "Ava", "E", "Chen", null, false, "steve@stu.jcpsky.net", "Achen13", "G3"),
    new DbUser("dchabra13", "Dishant", "Y", "Chhabra", null, true, "steve@stu.jcpsky.net", "Dchabra13", "G3"),
    new DbUser("mchire13", "Mitali", "G", "Chire", null, false, "steve@stu.jcpsky.net", "Mchire13", "G3"),
    new DbUser("pchuong13", "Phillip", null, "Chuong", "Phil", true, "steve@stu.jcpsky.net", "Pchuong13", "G3"),
    new DbUser("jfairfax13", "Jonathan", "T", "Fairfax", "John", true, "steve@stu.jcpsky.net", "Jfairfax13", "G3"),
    new DbUser("dferguso13", "David", "L", "Ferguson", null, true, "steve@stu.jcpsky.net", "Dferguso13", "G3"),
    new DbUser("iferre13", "Ian", "D", "Ferre", null, true, "steve@stu.jcpsky.net", "Iferre13", "G3"),
    new DbUser("pfroula13", "Patrick", "M", "Froula", "Pat", true, "steve@stu.jcpsky.net", "Pfroula13", "G3"),
    new DbUser("bgatson13", "Benjamin", "T", "Gatson", "Ben", true, "steve@stu.jcpsky.net", "Bgatson13", "G3"),
    new DbUser("jglore13", "Jade", "M", "Glore", null, false, "steve@stu.jcpsky.net", "Jglore13", "G3"),
    new DbUser("ahamm13", "Andrew", "T", "Hamm", "Andy", true, "steve@stu.jcpsky.net", "Ahamm13", "G3"),
    new DbUser("ahorstma13", "Austin", "T", "Horstman", "Auzzer", true, "steve@stu.jcpsky.net", "Ahorstma13", "G3"),
    new DbUser("kjackson13", "Kenny", "K", "Jackson", "Ken", true, "steve@stu.jcpsky.net", "Kjackson13", "G3"),
    new DbUser("mkanupar12", "Meghamsh", null, "Kanuparthy", "MegaMoose", true, "steve@stu.jcpsky.net", "Mkanupar12", "G3"),
    new DbUser("jking13", "John", "F", "King", "Johnny-Boy", true, "steve@stu.jcpsky.net", "Jking13", "G3"),
    new DbUser("emcknigh13", "Eric", "G", "McKnight", null, true, "steve@stu.jcpsky.net", "Emcknigh13", "G3"),
    new DbUser("cmiller13", "Christopher", "A", "Miller", "Chris", true, "steve@stu.jcpsky.net", "Cmiller13", "G3"),
    new DbUser("aolson13", "Andrew", "J", "Olson", "Andy", true, "steve@stu.jcpsky.net", "Aolson13", "G3"),
    new DbUser("hseo13", "Hanna", "W", "Seo", "Hanna-Banana", false, "steve@stu.jcpsky.net", "Hseo13", "G3"),
    new DbUser("dstroupe13", "Daniel", "J", "Stroupe", "Pat", true, "steve@stu.jcpsky.net", "Dstroupe13", "G3"),
    new DbUser("jsulliva13", "Joshua", "S", "Sullivan", "Josh", true, "steve@stu.jcpsky.net", "Jsulliva13", "G3"),
    new DbUser("jvittito13", "Jacob", "A", "Vittitow", "Jake", true, "steve@stu.jcpsky.net", "Jvittito13", "G3"),
    new DbUser("avo13", "Alan", "T", "Vo", "Big Al", true, "steve@stu.jcpsky.net", "Avo13", "G3"),
    new DbUser("jwarner13", "Jeffrey", "B", "Warner", "Jeff", true, "steve@stu.jcpsky.net", "Jwarner13", "G3"),
    new DbUser("pwells13", "Patrick", "W", "Wells", "Chicken Patty", true, "steve@stu.jcpsky.net", "Pwells13", "G3")
    )

  val apCSW3Students = List[DbUser](
    new DbUser("jalexand13", "James", "N", "Alexander", null, true, "mike@jcpsky.net", "Jalexand13", "G4"),
    new DbUser("mbecker13", "Mitchell", "H", "Becker", "Mitch", true, "mike@jcpsky.net", "Mbecker13", "G4"),
    new DbUser("aboss13", "Allen", "T", "Boss", "Boss", true, "mike@jcpsky.net", "Aboss13", "G4"),
    new DbUser("acoomer13", "Abigail", "M", "Coomer", "Abby", false, "mike@jcpsky.net", "Acoomer13", "G4"),
    new DbUser("sfoo13", "Shunhua", "S", "Foo", "Sam", true, "mike@jcpsky.net", "Acoomer13", "G4"),
    new DbUser("mgraves13", "Matthew", "J", "Graves", "Matt", true, "mike@jcpsky.net", "Mgraves13", "G4"),
    new DbUser("chainlin13", "Casey", "L", "Hainling", "Chainlink", false, "mike@jcpsky.net", "Chainlin13", "G4"),
    new DbUser("khayes13", "Ken", "M", "Hayes", null, true, "mike@jcpsky.net", "Khayes", "G4"),
    new DbUser("zhigdon13", "Zackery", "R", "Higdon", "ZackAttack", true, "mike@jcpsky.net", "Zhigdon13", "G4"),
    new DbUser("shughes13", "Stephen", "P", "Hughes", "Steve", true, "mike@jcpsky.net", "Shughes13", "G4"),
    new DbUser("smalegao13", "Shrinivas", "V", "Malegaonkar", "Gautan", true, "mike@jcpsky.net", "Shughes13", "G4"),
    new DbUser("smoorin13", "Samantha", "S", "Moorin", "Sammy", false, "mike@jcpsky.net", "Smoorin13", "G4"),
    new DbUser("iokorie12", "Ijeoma", "C", "Okorie", "Ija-mama", false, "mike@jcpsky.net", "Iokorie12", "G4"),
    new DbUser("spark13", "Sang Jun", null, "Park", "Sparky", true, "mike@jcpsky.net", "Spark13", "G4"),
    new DbUser("mpearson13", "Matthew", "A", "Pearson", "Matt", true, "mike@jcpsky.net", "Mpearson13", "G4"),
    new DbUser("iprats13", "Izaak", "J", "Prats", "Izzy Pop", true, "mike@jcpsky.net", "Iprats13", "G4"),
    new DbUser("yschaal13", "Yuval", null, "Schaal", null, false, "mike@jcpsky.net", "Yschaal13", "G4"),
    new DbUser("kschroll13", "Kevin", "P", "Schroll", "Big K", true, "mike@jcpsky.net", "Kschroll13", "G4"),
    new DbUser("escruton13", "Elizabeth", "K", "Scruton", "Eliza", false, "mike@jcpsky.net", "Acoomer13", "G4"),
    new DbUser("ascutchf13", "Alex", "D", "Scutchfield", null, true, "mike@jcpsky.net", "Ascutchf13", "G4"),
    new DbUser("mvanzyl", "Michiel", null, "Van Zyl", "Mich", true, "mike@jcpsky.net", "Acoomer13", "G4"),
    new DbUser("jyang13", "John", "J", "Yang", "Jingleheimerschmidt", true, "mike@jcpsky.net", "Jyang13", "G4")
  )

  val introR3Students = List[DbUser](
    new DbUser("naviles14", "Nevin", "R", "Aviles", null, true, "mike@jcpsky.net", "Naviles14", "G4"),
    new DbUser("kbabbarw14", "Karan", null, "Babbarwal", null, true, "mike@jcpsky.net", "Kbabbarwa14", "G4"),
    new DbUser("mbarley14", "Mariah", "A", "Barley", null, false, "mike@jcpsky.net", "Mbarley14", "G4"),
    new DbUser("acanpbel14", "Allison", "N", "Canpbell", "Ali", false, "mike@jcpsky.net", "Acanpbel14", "G4"),
    new DbUser("jchen14", "Joyce", null, "Chen", null, false, "mike@jcpsky.net", "Jchen14", "G4"),
    new DbUser("sdearing14", "Samantha", "J", "Dearing", "Sammy", false, "mike@jcpsky.net", "Sdearing14", "G4"),
    new DbUser("delzy", "Dakota", "R", "Elzy", null, false, "mike@jcpsky.net", "Mbarley14", "G4"),
    new DbUser("lfairfie14", "Logan", "w", "Fairfield", null, true, "mike@jcpsky.net", "Lfairfield14", "G4"),
    new DbUser("cfolz14", "Caroline", "C", "Folz", null, false, "mike@jcpsky.net", "Cfolz14", "G4"),
    new DbUser("mfraser14", "Madison", "K", "Fraser", "Maddy", false, "mike@jcpsky.net", "Mfraser14", "G4"),
    new DbUser("jhenry14", "Jeffrey", "M", "Henry", "Jeff", true, "mike@jcpsky.net", "Jhenry14", "G4"),
    new DbUser("hjortani14", "Haleh", "A", "Jortani", null, false, "mike@jcpsky.net", "HJortani14", "G4"),
    new DbUser("skoby14", "Samuel", "B", "Koby", "Sam", true, "mike@jcpsky.net", "Skoby14", "G4"),
    new DbUser("kmckee14", "Kory", "J", "McKee", null, false, "mike@jcpsky.net", "Kmckee14", "G4"),
    new DbUser("knewman14", "Kyle", "A", "Newman", null, true, "mike@jcpsky.net", "Knewman14", "G4"),
    new DbUser("woverstr14", "Weasley", "S", "Overstreet", null, true, "mike@jcpsky.net", "Woverstr14", "G4"),
    new DbUser("jpawlak14", "Jacob", "C", "Pawlak", "Jake", true, "mike@jcpsky.net", "Jpawlak14", "G4"),
    new DbUser("jrepisht14", "Julia", "L", "Repishti", null, false, "mike@jcpsky.net", "Jrepisht14", "G4"),
    new DbUser("mreyes", "Miguel", "A", "Reyes", "The king", true, "mike@jcpsky.net", "Mreyes14", "G4"),
    new DbUser("ssatterl14", "Spencer", "A", "Satterly", "Spence", true, "mike@jcpsky.net", "Ssatterl14", "G4"),
    new DbUser("rshah14", "Ryan", "A", "Shah", null, true, "mike@jcpsky.net", "Rshah14", "G4"),
    new DbUser("asummers14", "Ashley", "E", "Summers", "Ash", false, "mike@jcpsky.net", "Asummer14", "G4"),
    new DbUser("jtufaro14", "Joseph", "A", "Tufaro", "Joe", true, "mike@jcpsky.net", "Jtufaro14", "G4"),
    new DbUser("bvillato14", "Brian", "J", "Villatoro", "Brain", true, "mike@jcpsky.net", "Bvillato14", "G4"),
    new DbUser("awiley14", "Anna", "C", "Wiley", "Anna Bannana", false, "mike@jcpsky.net", "Awiley14", "G4"),
    new DbUser("jwolz14", "Jordan", "S", "Wolz", null, true, "mike@jcpsky.net", "Jwolz14", "G4"),
    new DbUser("jyi14", "Jonathan", "J", "Yi", "JJ", true, "mike@jcpsky.net", "Jyi14", "G4")
  )

  val introR4Students = List[DbUser](
    new DbUser("hbeach14", "Hannah", "S", "Beach", "Hannah Banana", false, "mike@jcpsky.net", "Hbeach14", "G4"),
    new DbUser("dcobourn14", "Daniel", "P", "Cobourn", "Danny", true, "mike@jcpsky.net", "Dcobourn14", "G4"),
    new DbUser("ecollins14", "Erica", "L", "Collins", null, false, "mike@jcpsky.net", "Ecollins14", "G4"),
    new DbUser("jdeacon14", "Jacob", "T", "Deacon", "Jake", true, "mike@jcpsky.net", "Jdeacon14", "G4"),
    new DbUser("ddeadwyl14", "Desmond", "G", "Deadwyler", null, true, "mike@jcpsky.net", "Ddeadwyl14", "G4"),
    new DbUser("hdesai14", "Harsh", "K", "Desai", null, true, "mike@jcpsky.net", "Hdesai14", "G4"),
    new DbUser("cfitzpat14", "Connor", "J", "Fitzpatrick", null, true, "mike@jcpsky.net", "Cfitzpat14", "G4"),
    new DbUser("bflaniga14", "Benjamin", "N", "Flanigan", "Ben", true, "mike@jcpsky.net", "Bflaniga14", "G4"),
    new DbUser("jfrancis14", "John", "W", "Franciso", "Johnny Boy", true, "mike@jcpsky.net", "Jfrancis14", "G4"),
    new DbUser("ahajela14", "Avni", null, "Hajela", null, false, "mike@jcpsky.net", "Ahajela14", "G4"),
    new DbUser("ahelm14", "Alex", "M", "Helm", null, true, "mike@jcpsky.net", "Ahelm14", "G4"),
    new DbUser("skarimi14", "Sharzaud", "A", "Karimi", "Charzard", false, "mike@jcpsky.net", "Skarimi14", "G4"),
    new DbUser("ikresse14", "Isaac", "J", "Kresse", "Izzy Pop", true, "mike@jcpsky.net", "Ikresse14", "G4"),
    new DbUser("jle14", "John", "M", "Le", "Jelly", true, "mike@jcpsky.net", "Jle14", "G4"),
    new DbUser("elee14", "Elizabeth", "I", "Lee", "Lizzy", false, "mike@jcpsky.net", "Elee14", "G4"),
    new DbUser("alotspei14", "Austin", "J", "Lotspeich", "Auzzer", true, "mike@jcpsky.net", "Alotspei14", "G4"),
    new DbUser("smashiat14", "Saiara", null, "Mashiat", null, false, "mike@jcpsky.net", "Smashiat14", "G4"),
    new DbUser("jmiller14", "James", "T", "Miller", null, true, "mike@jcpsky.net", "Jmiller14", "G4"),
    new DbUser("tneuteuf14", "Thomas", "E", "Neuteufel", "Nutella", true, "mike@jcpsky.net", "Tneuteuf14", "G4"),
    new DbUser("nnguyen14", "Nga", "T", "Nguyen", null, false, "mike@jcpsky.net", "Nnguyen14", "G4"),
    new DbUser("inimma14", "Induja", "R", "Nimma", null, false, "mike@jcpsky.net", "Inimma14", "G4"),
    new DbUser("mnocon14", "Mathea Nadine", "F", "Nocon", "MNFN", false, "mike@jcpsky.net", "Mnocon14", "G4"),
    new DbUser("tsetser14", "Trey", "R", "Setser", "Tester", true, "mike@jcpsky.net", "Tsetser14", "G4"),
    new DbUser("jshelbur14", "Jesse", null, "Shelburne", null, true, "mike@jcpsky.net", "Jshelbur14", "G4"),
    new DbUser("ashnider14", "Austin", "D", "Shnider", "Auz", true, "mike@jcpsky.net", "Ashnider14", "G4"),
    new DbUser("atezel14", "Alangoya", null, "Tezel", "Al", true, "mike@jcpsky.net", "Atezel14", "G4"),
    new DbUser("ttran14", "Tommy", "Q", "Tran", "QT", true, "mike@jcpsky.net", "Ttran14", "G4"),
    new DbUser("byuen14", "Brian", "C", "Yuen", null, true, "mike@jcpsky.net", "Dyuen14", "G4"),
    new DbUser("ezeng14", "Emily", null, "Zeng", "EZ", false, "mike@jcpsky.net", "Ezeng14", "G4")
  )


  //TEACHERS -----------------------------------------------------------------------------------------------------------
  val OBTeachers = List[DbUser](
    new DbUser("tobryan", "Todd", "A", "OBryan", "OB", true, "mike@jcpsky.net", "Tobryan", "G1234")
  )

}




object CourseList {
  def render = ".userRow *" #> allTeachers().map(renderUser(_))

  def renderUser(user: User) = {
    val email = user.email.get match {
      case Some(address) => <a href={ "mailto:" + address }>{ address }</a>
      case _ => NodeSeq.Empty
    }
    ".name *" #> user.formalName &
    ".email *" #> email
  }

  def allCourses(): List[User] = {
    User.findAll.sortWith((u1: User, u2: User) => u1.formalName.toLowerCase < u2.formalName.toLowerCase)
  }


         teacher->  uniqueid -> teacherlists-> sections-> courses!!!!

