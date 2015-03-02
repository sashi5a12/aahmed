package com.classes

class Foo {

  lazy val text = {
    var lines = ""
    try {
      lines = io.Source.fromFile("/etc/passwd").getLines().mkString
    } catch {
      case e: Exception => "Error Happened"
    }
    lines
  }

}