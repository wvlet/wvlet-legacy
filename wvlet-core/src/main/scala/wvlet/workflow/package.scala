package wvlet

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

import wvlet.workflow.macros.TaskMacros._

import scala.language.implicitConversions

/**
  *
  */
package object workflow {

  import scala.language.experimental.macros

  def task[U](body: => U): Task = macro mNewTask

  //def task[U](body: TaskContext => U): Task = macro mNewTaskWithContext
  def shell(command: => String): Task = macro mNewShellTask

  implicit class ShellCommandString(val sc: StringContext) {
    def c(args: Any*): Task = macro mNewShellTaskStr
    //def td(args: Any*): Task = macro mNewShellTaskStr
    def commandTemplate: String = sc.parts.mkString("{}")

    def commandStr(args: Any*): String = {
      sc.parts.mkString("%s").format(args: _*)
    }
  }

}
