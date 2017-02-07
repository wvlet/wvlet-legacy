/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package wvlet.core.tablet


trait TextTabletReader extends TabletReader {
  type Line = String

}


trait TextTypeConverter {

  def convert(v:String, target:Schema.ColumnType) = {



  }
}



class TSVTabletReader(schema:Schema) extends TextTabletReader {

  private val reader =

  def read: Option[Record]

  def readLine[U](line: Line)(body: TabletReader => U) {
    val cols = line.split("\t")

  }

}

/**
  *
  */
object TextTabletReader {

}
