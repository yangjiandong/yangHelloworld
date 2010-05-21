package com.yang.lift.helloworld.controller

import scala.actors.Actor
import scala.actors.Actor._
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import com.yang.lift.helloworld.model._
import net.liftweb.mapper.By

// messages
case class AddListener(listener: Actor, itemId: Long)
case class RemoveListener(listener: Actor, itemId: Long)
case class BidOnItem(itemId: Long, amount: Long, user: User)
case class GetHighBid(item: Item)
case class TheCurrentHighBid(amount: Long, user: User)
case class Success(success: Boolean)

object Auctioneer extends Actor {
	val listeners = new HashMap[Long, ListBuffer[Actor]]

	def notifyListeners(itemId: Long) = {
		if (listeners.contains(itemId)) {
			listeners(itemId).foreach((actor) => {
				val item = ItemMetaData.findByKey(itemId).open_!
				actor ! highBid(item)
			})
		}
	}

	def act = {
		loop {
			react {
				case AddListener(listener: Actor, itemId: Long) =>
					if (!listeners.contains(itemId)) {
						listeners(itemId) = new ListBuffer[Actor]
					}
					listeners(itemId) += listener
					reply(Success(true))
				case RemoveListener(listener: Actor, itemId: Long) =>
					listeners(itemId) -= listener
				case GetHighBid(item: Item) =>
					reply(highBid(item))
				case BidOnItem(itemId: Long, amount: Long, user: User) =>
					val item =
					ItemMetaData.findAll(By(ItemMetaData.id, itemId)).firstOption.get
					val bid = BidMetaData.create
					bid.amount(amount).item(item).user(user).save
					notifyListeners(item.id)
			}
		}
	}

	def highBid(item: Item): TheCurrentHighBid = {
		val highBid = item.highBid
		val user = highBid.user.obj.open_!
		val amt = highBid.amount.is
		TheCurrentHighBid(amt, user)
	}
	start
}
