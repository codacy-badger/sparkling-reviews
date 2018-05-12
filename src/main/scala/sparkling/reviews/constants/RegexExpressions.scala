package sparkling.reviews.constants

import sparkling.reviews.constants.StringConstants.EndLineChar

import scala.util.matching.Regex

private[sparkling] object RegexExpressions {

  final val extraWhiteSpaceRegex: Regex = "\\s+".r
  final val nonAlphaNumericWithSpaceRegex: Regex = "[^0-9a-zA-Z,?!\\s]".r
  final val webLinksRegex: Regex = "(http://[^\\s]*)|(www\\.[^\\s]*)".r
  final val endLineCharRegex: Regex = EndLineChar.toString.r
  final val NounFormsStrExpr: String = "NN|NNS|NNP|NNPS"

}
