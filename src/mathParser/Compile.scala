/*
 * Copyright (C) 2017  Gregor Ihmor
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package mathParser

import mathParser.AbstractSyntaxTree.Node

import scala.util.Try

trait Compile[Lang <: Language] {
  def apply(v1: Variable)
           (term: Node[Lang]): Option[Lang#Skalar => Lang#Skalar]

  def apply(v1: Variable, v2: Variable)
           (term: Node[Lang]): Option[(Lang#Skalar, Lang#Skalar) => Lang#Skalar]


  private[mathParser] def compileAndCast[A](scalaCode:String):Try[A] =
    Try{
      import reflect.runtime.currentMirror
      import tools.reflect.ToolBox
      currentMirror.mkToolBox()
        .eval(currentMirror.mkToolBox().parse(scalaCode))
        .asInstanceOf[A]
    }
}
