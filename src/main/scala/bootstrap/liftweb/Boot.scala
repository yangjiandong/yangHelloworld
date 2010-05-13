package bootstrap.liftweb

import net.liftweb.util._
import net.liftweb.http._
import net.liftweb.sitemap._
import net.liftweb.sitemap.Loc._
import _root_.net.liftweb.common._
import Helpers._
import net.liftweb.mapper.{DB, ConnectionManager, Schemifier, DefaultConnectionIdentifier, ConnectionIdentifier}
import java.sql.{Connection, DriverManager}
import com.yang.lift.helloworld.model._
import com.yang.lift.helloworld.api._
import com.yang.lift.helloworld.util.{Charting, Image}

/* Connect Lucene/Compass for search */
class Boot {
  def boot {
    LiftRules.early.append {
      _.setCharacterEncoding("UTF-8")
    }

    if (!DB.jndiJdbcConnAvailable_?) DB.defineConnectionManager(DefaultConnectionIdentifier, DBVendor)
    LiftRules.addToPackages("com.yang.lift.helloworld")
    Schemifier.schemify(true, Log.infoF _, User, Tag, Account, AccountAdmin, AccountViewer, AccountNote, Expense, ExpenseTag)

    LiftRules.setSiteMap(SiteMap(MenuInfo.menu: _*))

    LiftRules.dispatch.prepend(RestAPI.dispatch)

    // Set up some rewrites
    LiftRules.rewrite.append {
      case RewriteRequest(ParsePath(List("account", acctName), _, _, _), _, _) =>
        RewriteResponse("viewAcct" :: Nil, Map("name" -> urlDecode(acctName)))
      case RewriteRequest(ParsePath(List("account", acctName, tag), _, _, _), _, _) =>
        RewriteResponse("viewAcct" :: Nil, Map("name" -> urlDecode(acctName), "tag" -> urlDecode(tag)))
    }

    // Custom dispatch for graph and receipt image generation
    LiftRules.dispatch.append {
      case Req(List("graph", acctName, "history"), _, _) =>
        () => Charting.history(acctName)
      case Req(List("graph", acctName, "tagpie"), _, _) =>
        () => Charting.tagpie(acctName)
      case Req(List("graph", acctName, "tagbar"), _, _) =>
        () => Charting.tagbar(acctName)
      case Req(List("image", expenseId), _, _) =>
        () => Full(Image.viewImage(expenseId))
    }

    import scala.xml.Text
    val m = Title(if (User.loggedIn_?) {Text("a")} else {Text("b")})

    Log.info("Bootstrap up")
  }
}

object MenuInfo {
  import Loc._
  val IfLoggedIn = If(() => User.currentUser.isDefined, "You must be logged in")

  def menu: List[Menu] = Menu(Loc("home", List("index"), "首页")) ::
      Menu(Loc("manageAccts", List("manage"), "Manage Accounts", IfLoggedIn)) ::
      Menu(Loc("addAcct", List("editAcct"), "Add Account", Hidden, IfLoggedIn)) ::
      Menu(Loc("viewAcct", List("viewAcct") -> true, "View Account", Hidden, IfLoggedIn)) ::
      Menu(Loc("help", List("help", "index"), "帮助?")) ::
      User.sitemap
}

object DBVendor extends ConnectionManager {
  def newConnection(name: ConnectionIdentifier): Box[Connection] = {
    try {
      /**Uncomment if you really want Derby
       *
      Class.forName("org.apache.derby.jdbc.EmbeddedDriver")
      val dm = DriverManager.getConnection("jdbc:derby:pca_example;create=true")
       */

      Class.forName("org.h2.Driver")
      val dm = DriverManager.getConnection("jdbc:h2:pca_example")
      Full(dm)
    } catch {
      case e: Exception => e.printStackTrace; Empty
    }
  }

  def releaseConnection(conn: Connection) {conn.close}
}


