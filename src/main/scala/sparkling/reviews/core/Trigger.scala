package sparkling.reviews.core

/**
  * Copyright 2018 Pratik Barhate
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  * http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing,
  * software distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */

import org.apache.log4j.{Level, LogManager, Logger}

object Trigger extends {

  /**
    * Main function which is the starting point for the application.
    *
    * @param args Command line arguments.
    */
  def main(args: Array[String]): Unit = {

    /**
      * Manage logging so that is does not blots out the standard output.
      * Setting the applications logging level to warning.
      */
    val log: Logger = LogManager.getRootLogger
    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("reviews.analysis").setLevel(Level.WARN)

    if (args.length != 1) {
      log.error(s"Review Analysis :: ${args.length} number of arguments passed. Expected 1 :- \n" +
        s"Review Analysis :: 1. Data path\n")
      sys.exit(1)
    } else {
      val dataPath: String = args(0)
      // Start the data processing
      val dataFlow = DataFlow(dataPath)
      dataFlow.execute()
    }
  }

}
