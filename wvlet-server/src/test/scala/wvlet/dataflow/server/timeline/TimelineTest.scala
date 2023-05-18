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
package wvlet.dataflow.server.timeline

import wvlet.airframe.Design
import wvlet.airframe.codec.{JDBCCodec, MessageCodec}
import wvlet.airframe.control.Resource
import wvlet.airspec.AirSpec
import wvlet.dataflow.api.v1.timeline.TimeInterval

import java.sql.{Connection, DriverManager}
import java.util.Properties
import scala.util.Using

class TimelineTest extends AirSpec {

  if (inCI) {
    skip("Local only tests")
  }

  Class.forName("org.duckdb.DuckDBDriver")

  // TODO Remove this path
  private val filename = "~/work/td/2023-05-15-parquet/all_query_jobs/*.parquet"

  protected override def design: Design = {
    Design.newDesign.bind[Connection].toInstance {
      val props = new Properties()
      // props.setProperty("duckdb.read_only", "true")
      DriverManager.getConnection(s"jdbc:duckdb:", props)
    }
  }

  test("read data from duckdb") { (conn: Connection) =>
    val codec = MessageCodec.of[TimeInterval]
    val jobs = Using.resource(conn.createStatement()) { stmt =>
      Using.resource(
        stmt.executeQuery(
          s"""select
             | td_resource_id as name,
             | job_created_at as startAt,
             | job_finished_at as endAt
             |from '${filename}'
             |where job_created_at is not null and job_finished_at is not null
             |and job_type = 'presto' and account_id = 1
             |limit 1000""".stripMargin
        )
      ) { rs =>
        val lst = JDBCCodec(rs).toJsonSeq.map { json =>
          codec.fromJson(json)
        }
        lst.toIndexedSeq
      }
    }
    TimelineAnalysis.findPeakTimes(jobs)
  }
}
