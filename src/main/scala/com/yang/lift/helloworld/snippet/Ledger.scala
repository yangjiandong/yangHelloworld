package com.yang.lift.helloworld.snippet

/**
 * p69 example
 */
import _root_.net.liftweb.http._
import S._
import _root_.net.liftweb.util._
import Helpers._
import _root_.scala.xml._
import SHtml._

class Ledger{
	def add(xhtml : NodeSeq) : NodeSeq = {
		var desc = "";
		var amount = "0";

		def processEntryAdd(){

		}

		bind("entry", xhtml,
			"description" -> SHtml.text(desc, desc = _),
		  "amount"  -> SHtml.text(amount, amount = _),
		  "submit" -> SHtml.submit("Add", processEntryAdd)
		)
		
	}
}