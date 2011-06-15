package eschool.sites.model

import xml.NodeSeq

import eschool.users.model.UserData
import net.liftweb.common.Empty
import net.liftweb.mapper._

object SiteData {
  def create() {
    createSites()
    createMarysPages()
    createBobsPages()
  }

  // Steve has no sites, Mary has one, and Bob has three
  def createSites() {
    val fldhky = Site.create.ident("fldhky").name("Field Hockey").owner(UserData.mary)
    fldhky.save
    val courses = Site.create.ident("courses").name("Bob's Courses").owner(UserData.bob)
    courses.save
    val hobbies = Site.create.ident("hobbies").name("Bob's Hobbies").owner(UserData.bob)
    hobbies.save
    val soccer = Site.create.ident("soccer").name("Soccer").owner(UserData.bob)
    soccer.save
  }

  // Mary's Field Hockey site has two pages: a roster and a schedule, both at the top-level
  def createMarysPages() {
    val fldhky1: NodeSeq = {
      <h1>Roster</h1>
      <table>
        <tr><th>Player</th><th>Position</th></tr>
        <tr><td>Jane</td><td>Forward</td></tr>
        <tr><td>Sue</td><td>Backward</td></tr>
        <tr><td>Paula</td><td>Goalie</td></tr>
      </table>
    }
    val fldhky2: NodeSeq = {
      <h2>Schedule</h2>
      <table>
        <tr><th>Date</th><th>Opponent</th></tr>
        <tr><td>Sept 10</td><td>West High School</td></tr>
        <tr><td>Sept 17</td><td>East High School</td></tr>
        <tr><td>Sept 20</td><td>North High School</td></tr>
      </table>
    }
    val fldhky: Site = Site.find(By(Site.ident, "fldhky")).open_!
    val maryPage1 = Page.create.site(fldhky).ident("roster").title("Field Hockey Roster").content(fldhky1).parent(Empty)
    maryPage1.save
    val maryPage2 = Page.create.site(fldhky).ident("schedule").title("Field Hockey Schedule").content(fldhky2).parent(Empty)
    maryPage2.save
  }

  // Bob's Soccer page has a roster and a schedule, both at the top level
  // His Hobbies page has
  def createBobsPages() {
    val content1: NodeSeq = {
      <h1>Welcome to Bob's Home Page</h1>
      <p>Things Bob likes:
        <ul>
          <li>burgers</li>
          <li>chips</li>
          <li>movies with Jean-Claude van Damme</li>
        </ul>
      </p>
    }
    val content2: NodeSeq = {
      <h2>These are Bob's Friends</h2>
      <ol>
        <li>Mary</li>
        <li>Steve</li>
      </ol>
    }
    val hobbies = Site.find(By(Site.ident, "hobbies")).open_!
    val page1 = Page.create.site(hobbies).ident("home").title("Bob's Home Page").content(content1).parent(Empty)
    page1.save()
    val page2 = Page.create.site(hobbies).ident("friends").title("Bob's Friends").content(content2).parent(Empty)
    page2.save()
  }
}