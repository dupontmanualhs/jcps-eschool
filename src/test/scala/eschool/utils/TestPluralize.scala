package eschool.utils

import org.scalatest.junit.JUnitSuite
import org.junit.Test
import eschool.utils.Helpers._

class TestPluralize extends JUnitSuite {
	@Test
  def operationStrings1() {
	  assert(pluralizeInformal(2, "mouse") === "two mice")
  }
  @Test
  def operationStrings2() {
	  assert(pluralizeInformal(2, "house") === "two houses")
  }
  @Test
  def operationStrings3() {
	  assert(pluralizeInformal(2, "louse") === "two lice")
  }
  @Test
  def operationStrings4() {
	  assert(pluralizeInformal(2, "knife") === "two knives")
  }
  @Test
  def operationStrings5() {
	  assert(pluralizeInformal(2, "wife") === "two wives")
  }
  @Test
  def operationStrings6() {
	  assert(pluralizeInformal(2, "cactus") === "two cacti")
  }
  @Test
  def operationStrings7() {
	  assert(pluralizeInformal(2, "thesis") === "two theses")
  }
  @Test
  def operationStrings8() {
	  assert(pluralizeInformal(2, "index") === "two indeces")
  }
  @Test
  def operationStrings9() {
	  assert(pluralizeInformal(2, "bureau") === "two bureaux")
  }
  @Test
  def operationStrings10() {
	  assert(pluralizeInformal(2, "man") === "two men")
  }
  @Test
  def operationStrings11() {
	  assert(pluralizeInformal(2, "woman") === "two women")
  }
  @Test
  def operationStrings12() {
	  assert(pluralizeInformal(2, "medium") === "two media")
  }
  @Test
  def operationStrings13() {
	  assert(pluralizeInformal(2, "foot") === "two feet")
  }
  @Test
  def operationStrings14() {
	  assert(pluralizeInformal(2, "vertebra") === "two vertebrae")
  }
  @Test
  def operationStrings15() {
	  assert(pluralizeInformal(2, "datum") === "two data")
  }
  @Test
  def operationStrings16() {
	  assert(pluralizeInformal(2, "moose") === "two moose")
  }
  @Test
  def operationStrings17() {
	  assert(pluralizeInformal(2, "fungus") === "two fungi")
  }
  @Test
  def operationStrings18() {
	  assert(pluralizeInformal(2, "corpus") === "two corpora")
  }
  @Test
  def operationStrings19() {
	  assert(pluralizeInformal(2, "genus") === "two genera")
  }
  @Test
  def operationStrings20() {
	  assert(pluralizeInformal(2, "tooth") === "two teeth")
  }
  @Test
  def operationStrings21() {
	  assert(pluralizeInformal(2, "goose") === "two geese")
  }
  @Test
  def operationStrings22() {
	  assert(pluralizeInformal(2, "fish") === "two fish")
  }
  @Test
  def operationStrings23() {
	  assert(pluralizeInformal(2, "half") === "two halves")
  }
  @Test
  def operationStrings24() {
	  assert(pluralizeInformal(2, "hoof") === "two hooves")
  }
  @Test
  def operationStrings25() {
	  assert(pluralizeInformal(2, "boot") === "two boots")
  }
  @Test
  def operationStrings26() {
	  assert(pluralizeInformal(2, "fungus") === "two fungi")
  }
  @Test
  def operationStrings27() {
	  assert(pluralizeInformal(2, "bus") === "two buses")
  }
  @Test
  def operationStrings28() {
	  assert(pluralizeInformal(2, "blouse") === "two blouses")
  }
  @Test
  def operationStrings29() {
	  assert(pluralizeInformal(2, "site") === "two sites")
  }
  @Test
  def operationStrings30() {
	  assert(pluralizeInformal(2, "syllabus") === "two syllabi")
  }
  @Test
  def operationStrings31() {
	  assert(pluralizeInformal(2, "tax") === "two taxes")
  }
  @Test
  def operationStrings32() {
	  assert(pluralizeInformal(2, "wharf") === "two wharves")
  }
  @Test
  def operationStrings33() {
	  assert(pluralizeInformal(2, "beach") === "two beaches")
  }
  @Test
  def operationStrings34() {
	  assert(pluralizeInformal(2, "cherry") === "two cherries")
  }
  @Test
  def operationStrings35() {
	  assert(pluralizeInformal(2, "elf") === "two elves")
  }
  @Test
  def operationStrings36() {
	  assert(pluralizeInformal(2, "motto") === "two mottoes")
  }
  @Test
  def operationStrings37() {
	  assert(pluralizeInformal(2, "nanny") === "two nannies")
  }
  @Test
  def operationStrings38() {
	  assert(pluralizeInformal(2, "potato") === "two potatoes")
  }
  @Test
  def operationStrings39() {
	  assert(pluralizeInformal(2, "thief") === "two thieves")
  }
  @Test
  def operationStrings40() {
	  assert(pluralizeInformal(2, "day") === "two days")
  }
  @Test
  def operationStrings41() {
	  assert(pluralizeInformal(2, "teacher") === "two teachers")
  }
  @Test
  def operationStrings42() {
	  assert(pluralizeInformal(2, "report") === "two reports")
  }
  @Test
  def operationStrings43() {
	  assert(pluralizeInformal(3, "grade") === "three grades")
  }
  @Test
  def operationStrings44() {
	  assert(pluralizeInformal(2, "type") === "two types")
  }
  @Test
  def operationStrings45() {
	  assert(pluralizeInformal(2, "dictionary") === "two dictionaries")
  }
  @Test
  def operationStrings46() {
	  assert(pluralizeInformal(2, "hero") === "two heroes")
  }
  @Test
  def operationStrings47() {
	  assert(pluralizeInformal(2, "life") === "two lives")
  }
  @Test
  def operationStrings48() {
	  assert(pluralizeInformal(2, "try") === "two tries")
  }
  @Test
  def operationStrings49() {
	  assert(pluralizeInformal(2, "mango") === "two mangoes")
  }
  @Test
  def operationStrings50() {
	  assert(pluralizeInformal(2, "copy") === "two copies")
  }
  @Test
  def operationStrings51() {
	  assert(pluralizeInformal(2, "eclipse") === "two eclipses")
  }
  @Test
  def operationStrings52() {
	  assert(pluralizeInformal(2, "focus") === "two foci")
  }
  @Test
  def operationStrings53() {
	  assert(pluralizeInformal(2, "copy") === "two copies")
  }
  @Test
  def operationStrings54() {
	  assert(pluralizeInformal(2, "family") === "two families")
  }
  @Test
  def operationStrings55() {
	  assert(pluralizeInformal(2, "fax") === "two faxes")
  }
  @Test
  def operationStrings56() {
	  assert(pluralizeInformal(2, "mess") === "two messes")
  }
  @Test
  def operationStrings57() {
	  assert(pluralizeInformal(2, "ticket") === "two tickets")
  }
  @Test
  def operationStrings58() {
	  assert(pluralizeInformal(2, "way") === "two ways")
  }
  @Test
  def operationStrings59() {
	  assert(pluralizeInformal(2, "hippopotamus") === "two hippopotami")
  }
  @Test
  def operationStrings60() {
	  assert(pluralizeInformal(2, "hoax") === "two hoaxes")
  }
  @Test
  def operationStrings61() {
	  assert(pluralizeInformal(2, "circus") === "two circuses")
  }
  @Test
  def operationStrings62() {
	  assert(pluralizeInformal(2, "echo") === "two echoes")
  }
  @Test
  def operationStrings63() {
	  assert(pluralizeInformal(2, "arch") === "two arches")
  }
  @Test
  def operationStrings64() {
	  assert(pluralizeInformal(2, "flush") === "two flushes")
  }
  @Test
  def operationStrings65() {
	  assert(pluralizeInformal(2, "year") === "two years")
  }
  @Test
  def operationStrings66() {
	  assert(pluralizeInformal(2, "quiz") === "two quizzes")
  }
  @Test
  def operationStrings67() {
	  assert(pluralizeInformal(2, "time") === "two times")
  }
  @Test
  def operationStrings68() {
	  assert(pluralizeInformal(2, "oasis") === "two oases")
  }
  @Test
  def operationStrings69() {
	  assert(pluralizeInformal(2, "color") === "two colors")
  }
  @Test
  def operationStrings70() {
	  assert(pluralizeInformal(2, "attempt") === "two attempts")
  }
  @Test
  def operationStrings71() {
	  assert(pluralizeInformal(2, "freak") === "two freaks")
  }
  @Test
  def operationStrings72() {
	  assert(pluralizeInformal(2, "attempt") === "two attempts")
  }
  @Test
  def operationStrings73() {
	  assert(pluralizeInformal(2, "person") === "two people")
  }
  @Test
  def operationStrings74() {
	  assert(pluralizeInformal(2, "object") === "two objects")
  }
  @Test
  def operationStrings75() {
	  assert(pluralizeInformal(2, "ram") === "two rams")
  }
  @Test
  def operationStrings76() {
	  assert(pluralizeInformal(2, "blob") === "two blobs")
  }
  @Test
  def operationStrings77() {
	  assert(pluralizeInformal(2, "mix") === "two mixes")
  }
  @Test
  def operationStrings78() {
	  assert(pluralizeInformal(2, "horizon") === "two horizons")
  }
  @Test
  def operationStrings79() {
	  assert(pluralizeInformal(2, "automobile") === "two automobiles")
  }
  @Test
  def operationStrings80() {
	  assert(pluralizeInformal(2, "stumble") === "two stumbles")
  }
  @Test
  def operationStrings81() {
	  assert(pluralizeInformal(2, "glass") === "two glasses")
  }
  @Test
  def operationStrings82() {
	  assert(pluralizeInformal(2, "vowel") === "two vowels")
  }
  @Test
  def operationStrings83() {
	  assert(pluralizeInformal(2, "pump") === "two pumps")
  }
  @Test
  def operationStrings84() {
	  assert(pluralizeInformal(2, "opera") === "two operas")
  }
  @Test
  def operationStrings85() {
	  assert(pluralizeInformal(2, "eye") === "two eyes")
  }
  @Test
  def operationStrings86() {
	  assert(pluralizeInformal(2, "embryotrophy") === "two embryotrophies")
  }
  @Test
  def operationStrings87() {
	  assert(pluralizeInformal(2, "cyclops") === "two cyclopses")
  }
  @Test
  def operationStrings88() {
	  assert(pluralizeInformal(2, "tenament") === "two tenaments")
  }
  @Test
  def operationStrings89() {
	  assert(pluralizeInformal(2, "palaeontologist") === "two palaeontologists")
  }
  @Test
  def operationStrings90() {
	  assert(pluralizeInformal(2, "swineherd") === "two swineherds")
  }
  @Test
  def operationStrings91() {
	  assert(pluralizeInformal(2, "shoe") === "two shoes")
  }
  @Test
  def operationStrings92() {
	  assert(pluralizeInformal(2, "fix") === "two fixes")
  }
  @Test
  def operationStrings93() {
	  assert(pluralizeInformal(2, "millipede") === "two millipedes")
  }
  @Test
  def operationStrings94() {
	  assert(pluralizeInformal(2, "boy") === "two boys")
  }
  @Test
  def operationStrings95() {
	  assert(pluralizeInformal(2, "board") === "two boards")
  }
  @Test
  def operationStrings96() {
	  assert(pluralizeInformal(2, "worm") === "two worms")
  }
  @Test
  def operationStrings97() {
	  assert(pluralizeInformal(2, "zygote") === "two zygotes")
  }
  @Test
  def operationStrings98() {
	  assert(pluralizeInformal(2, "bee") === "two bees")
  }
  @Test
  def operationStrings99() {
	  assert(pluralizeInformal(2, "food") === "two foods")
  }
  @Test
  def operationStrings100() {
	  assert(pluralizeInformal(2, "boob") === "two boobs")
  }
  @Test
  def operationStrings101() {
	  assert(pluralizeInformal(2, "booby") === "two boobies")
  }
  @Test
  def operationStrings102() {
	  assert(pluralizeInformal(2, "switch") === "two switches")
  }
  @Test
  def operationStrings103() {
	  assert(pluralizeInformal(2, "zombie") === "two zombies")
  }
  @Test
  def operationStrings104() {
	  assert(pluralizeInformal(2, "surface") === "two surfaces")
  }
  @Test
  def operationStrings105() {
	  assert(pluralizeInformal(2, "degree") === "two degrees")
  }
  @Test
  def operationStrings106() {
	  assert(pluralizeInformal(2, "pass") === "two passes")
  }
  @Test
  def operationStrings107() {
	  assert(pluralizeInformal(2, "answer") === "two answers")
  }
  @Test
  def operationStrings108() {
	  assert(pluralizeInformal(2, "sandlot") === "two sandlots")
  }
  @Test
  def operationStrings109() {
	  assert(pluralizeInformal(2, "batch") === "two batches")
  }
  @Test
  def operationStrings110() {
	  assert(pluralizeInformal(2, "omnibus") === "two omnibuses")
  }
  @Test
  def operationStrings111() {
	  assert(pluralizeInformal(2, "country") === "two countries")
  }
  @Test
  def operationStrings112() {
	  assert(pluralizeInformal(2, "monkey") === "two monkeys")
  }
  @Test
  def operationStrings113() {
	  assert(pluralizeInformal(2, "elk") === "two elks")
  }
  @Test
  def operationStrings114() {
	  assert(pluralizeInformal(1, "country") === "one country")
  }
}