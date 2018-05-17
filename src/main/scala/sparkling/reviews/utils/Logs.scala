package sparkling.reviews.utils

import org.apache.log4j.{Level, LogManager, Logger}

private[sparkling] trait Logs {

  /**
    * Manage logging so that is does not blots out the standard output.
    * Setting the applications logging level to warning.
    */
  Logger.getLogger("org").setLevel(Level.ERROR)
  Logger.getLogger("product.reviews").setLevel(Level.DEBUG)
  protected val log: Logger = LogManager.getLogger("product.reviews")

}
