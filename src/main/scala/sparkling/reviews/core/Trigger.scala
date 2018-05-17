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

import java.time.Instant
import java.util.concurrent.TimeUnit

import sparkling.reviews.utils.Logs

object Trigger extends Logs {

  /**
    * Main function which is the starting point for the application.
    *
    * @param args Command line arguments.
    */
  def main(args: Array[String]): Unit = {

    val startTime: Long = Instant.now().toEpochMilli

    if (args.length != 2) {
      log.error(s"Number of arguments provided ${args.length}. But expected 2 :- \n" +
        s"1. Data input path\n" +
        s"2. Result output path\n")
      sys.exit(1)
    } else {
      val dataPath: String = args(0)
      val resultPath: String = args(1)
      // Start the data processing
      val dataFlow = DataFlow(dataPath, resultPath)
      dataFlow.execute()
    }
    val endTime: Long = Instant.now().toEpochMilli
    val totalTime: Long = endTime - startTime
    val hours: Long = TimeUnit.MILLISECONDS.toHours(totalTime)
    val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(totalTime) - TimeUnit.HOURS.toMinutes(hours)
    val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(totalTime) - TimeUnit.MINUTES.toSeconds(minutes)
    log.info(s"Total time taken is $hours:$minutes:$seconds (hours:minutes:seconds).")
  }

}
