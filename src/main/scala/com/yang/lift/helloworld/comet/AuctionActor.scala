package com.yang.lift.helloworld.comet

import net.liftweb.http._
import net.liftweb.http.SHtml._
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JsCmds._
import net.liftweb.http.js.JE._
import _root_.net.liftweb.common._
import scala.xml.NodeSeq
import scala.xml.MetaData
import scala.actors.Actor
import com.yang.lift.helloworld.model._
import com.yang.lift.helloworld._
import controller._
import java.lang.Long

class AuctionActor extends CometActor {
    var highBid : TheCurrentHighBid = null
    override def defaultPrefix = Full("auction")
    val itemId = S.param("itemId").map(Long.parseLong(_)).openOr(0L)
    def render = {
        def itemView: NodeSeq = {
            val item = if (itemId > 0)
                ItemMetaData.findByKey(itemId).openOr(ItemMetaData.create)
                else ItemMetaData.create
            val currBid = item.highBid
            val bidAmt = if (currBid.user.isEmpty) 0L else currBid.amount.is
            highBid = TheCurrentHighBid(bidAmt, currBid.user.obj.openOr(User.currentUser.open_!))
            val minNewBid = highBid.amount + 1L
            val button = <button type="button">{S.?("Bid Now!")}</button> %
              ("onclick" -> ajaxCall(JsRaw("$('#newBid').attr('value')"), bid _))
            (<div>
                <strong>{item.name}</strong>
                <br/>
                <div>
                    Current Bid: ${highBid.amount} by {highBid.user.niceName}
                </div>
                <div>
                    New Bid (min: ${minNewBid}) :
                    <input type="text" id="newBid"/>
                    {button}
                </div>
                {item.description}<br/>
            </div>)
        }
        bind("foo" -> <div>{itemView}</div>)
    }

    def bid(s:String): JsCmd = {
        val user = User.currentUser.open_!
        Auctioneer ! BidOnItem(itemId, Long.parseLong(s), user)
        Noop
    }

    override def localSetup {
        Auctioneer !? AddListener(this, this.itemId) match {

            case Success(true) => println("Listener added")
            case _ => println("Other ls")
        }
    }

    override def localShutdown {
        Auctioneer ! RemoveListener(this, this.itemId)
    }

    override def lowPriority : PartialFunction[Any, Unit] = {
        case TheCurrentHighBid(a,u) => {
                highBid = TheCurrentHighBid(a,u)
                reRender(false)
        }
        case _ => println("Other lp")
    }
}
