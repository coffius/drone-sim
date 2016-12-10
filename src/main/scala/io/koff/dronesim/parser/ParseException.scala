package io.koff.dronesim.parser

/**
  * Parsing exception
  */
class ParseException(action: String, line: String) extends Exception(s"Error during action[$action] in line[$line]")
