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
package wvlet.workflow

import wvlet.log.LogSupport
import wvlet.workflow.macros.{CodeBlock, CodeRef}

import scala.collection.mutable
import scala.language.dynamics

sealed trait TaskState {
  def isDone: Boolean
}

object TaskState {

  case object Queued extends TaskState {
    def isDone = false
  }

  case object Running extends TaskState {
    def isDone = false
  }

  case object Finished extends TaskState {
    def isDone = true
  }

  case object Failed extends TaskState {
    def isDone = true
  }


}

object TaskConfig {
  def empty = new TaskConfig(mutable.Map.empty)

  def apply(params: (String, Any)*) = new TaskConfig(mutable.Map(params: _*))
}

object TaskContext {
  val empty = TaskContext(TaskConfig.empty)
}

case class TaskContext(config: TaskConfig)

case class TaskConfig(param: mutable.Map[String, Any] = mutable.Map.empty) extends Dynamic {
  /**
    * Merge two config parameters and return a new TaskConfig object.
    * @param other
    * @return
    */
  def merge(other: TaskConfig): TaskConfig = new TaskConfig(this.param ++ other.param)

  def selectDynamic(key: String) = param(key)

  private[wvlet] def updateDynamic(key: String)(v: Any) { param.put(key, v) }

  override def toString = param.map { case (k, v) => s"${ k }:${ v }" }.mkString(", ")
}

/**
  * Task is a special case of Flow which does not pass any data object
  */
class Task(val name: String,
           val config: TaskConfig,
           var dependencies: Seq[Task] = Seq.empty) { self =>
//
//  def apply() : Unit = { runSolely() }
//
//  /**
//    * Run this task locally without processing its dependent tasks
//    */
//  def runSolely(context: TaskContext = TaskContext.empty): Unit = block.runSolely(context)

  /**
    * Add dependencies to this task.
    *
    * @param task
    * @return
    */
  def dependsOn(task: Task*): self.type = { dependencies ++= task.seq; self }

  def shortName = Option(name.split("\\.")).map(a => a(a.length - 1)).getOrElse(name)

  override def toString() = s"Task(${ shortName }, ${ config })"

  def objectId: String = "%8x".format(super.hashCode())
}

object Task extends LogSupport {

  def apply(name: String, config: TaskConfig = TaskConfig())(body: => Unit): Task = new Task(name, config, Seq.empty[Task])

  def newTask(codeRef: CodeRef, body: => Unit, config: TaskConfig = TaskConfig.empty): Task =
    new Task(codeRef.name, config.merge(TaskConfig("codeRef" -> codeRef)), Seq.empty)

  def newTaskWithContext(codeRef: CodeRef, config: TaskConfig = TaskConfig.empty): Task =
    new Task(codeRef.name, config.merge(TaskConfig("codeRef" -> codeRef)), Seq.empty)

  def fromCommand(codeRef: CodeRef, command: String): Task =
    newTaskWithContext(codeRef, TaskConfig("command" -> command))

}

