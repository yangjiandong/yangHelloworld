package com.yang.lift.helloworld.snippet

import com.yang.lift.helloworld.model._
import _root_.net.liftweb.common._
import _root_.net.liftweb.util.Helpers._
import _root_.scala.xml._
import _root_.java.util.Date

/**
 * Created by IntelliJ IDEA.
 * User: yang
 * Date: 2010-5-9
 * Time: 16:52:30
 * To change this template use File | Settings | File Templates.
 */

class HomePage{
	def	 summary(xhtml : NodeSeq) : NodeSeq = User.currentUser match {
		case Full(user) =>{
			val entries: NodeSeq = user.allAccounts match{
				case Nil => Text("You have no accounts set up")
				case accounts => accounts.flatMap({account =>
					bind("acct", chooseTemplate("account", "entry", xhtml),
						   "name" -> <a href={"/account/" + account.name.is}>{account.name.is}</a>,
						   "balance" -> Text(account.balance.toString))
				})
			}
			bind("account", xhtml, "entry" ->entries)
		}
		case _ => <lift:embed what="welcome_msg"/>
	}
}