package com.yang.lift.helloworld.util

import _root_.net.liftweb.http._
import _root_.net.liftweb.util._
import _root_.net.liftweb.mapper._
import S._
import Helpers._
import _root_.net.liftweb.common._

import com.yang.lift.helloworld.model._

object Image {
  def viewImage(id : String) : LiftResponse =
    try {
      User.currentUser match {
  case Full(user) => {
    Expense.find(id.toLong) match {
      case Full(e) => {
        if (e.owner == User.currentUser) {
    InMemoryResponse(e.receipt.is, List("Content-Type" -> e.receiptMime.is), Nil, 200)
        } else {
    ForbiddenResponse()
        }
      }
      case _ => PlainTextResponse("No such expense item", Nil, 404)
    }
  }
  case _ => RedirectResponse("/user_mgt/login")
      }
    } catch {
      case nfe : NumberFormatException => PlainTextResponse("Invalid expense ID", Nil, 400)
    }
}
