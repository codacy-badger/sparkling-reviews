package sparkling.reviews.constants

import StringConstants.EndLineChar

import scala.util.matching.Regex

private[sparkling] object RegexExpressions {

  final val extraWhiteSpaceRegex: Regex = "\\s+".r
  final val nonAlphaNumericWithSpaceRegex: Regex = "[^0-9a-zA-Z,?!\\s]".r
  final val webLinksRegex: Regex = "(http://[^\\s]*)|(www\\.[^\\s]*)".r
  final val endLineCharRegex: Regex = EndLineChar.toString.r

}
