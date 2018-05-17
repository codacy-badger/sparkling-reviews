package sparkling.reviews.constants

object DataConstants {

  /**
    * Column names in the original dataset.
    */
  final val UserID: String = "reviewerID"
  final val ProductID: String = "asin"
  final val Rating: String = "overall"
  final val Review: String = "reviewText"
  final val ReviewSummary: String = "summary"
  final val TimeStamp: String = "timeStamp"

  /**
    * Column names which will be generated while processing
    */
  final val CombinedText: String = "summary_plus_review"
  final val CleanText: String = "clean_text"
  final val SentimentValue: String = "sentiment_value"
  final val SentimentFactor: String = "sentiment_factor"
  final val SentimentCount: String = "sentiment_count"
  final val AllSentimentsCounts: String = "all_sentiment_counts"
  final val ReviewKeyWords: String = "key_words"
  final val ProductWordExploded: String = "review_word_exploded"
  final val ProductWordCount: String = "word_count_per_product"
  final val ProductWordRank: String = "word_product_rank"
  final val ListOfKeyWords: String = "list_key_words"
  final val SentimentCountTuple: String = "sentiment_count_tuple"
  final val SentimentCountCollection: String = "sentiment_count_collection"

  /**
    * Final output column names for the result
    */
  final val ProductSentimentFactor: String = "product_sentiment_factor"
  final val ProductSentiment: String = "product_sentiment"
  final val ProductKeyWords: String = "product_key_words"

  /**
    * Columns names of JohnSnowLabs outputs
    */
  final val SentimentsJSL: String = "sentiment"
  final val TextColumnJSL: String = "text"
  final val StemJSL: String = "stem"
  final val PosJSL: String = "pos"

  /**
    * Values which are fixed to the given data
    * e.g. Rating is out of 5, it could have been out of 10 too.
    */
  final val MaxRating: Double = 5.0

}
