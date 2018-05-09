package reviews.analysis.core

import org.apache.log4j.{Level, LogManager, Logger}

object Trigger extends {

  /**
    * Main function which is the starting point for the application.
    *
    * @param args Command line arguments.
    */
  def main(args: Array[String]): Unit = {

    /**
      * Manage logging so that is does not blots the standard output.
      * Setting the applications logging level to warning.
      */
    val log: Logger = LogManager.getRootLogger
    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("reviews.analysis").setLevel(Level.WARN)

    if (args.length != 1) {
      log.error(s"ReviewAnalysis :: ${args.length} number of arguments passed. Expected 1 :- \n" +
        s"ReviewAnalysis :: 1. Data path\n")
      sys.exit(1)
    } else {
      val dataPath: String = args(0)
      // Start the data processing
      val dataFlow = DataFlow(dataPath)
      dataFlow.execute()
    }
  }

}
