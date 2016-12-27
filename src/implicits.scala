/*
 * Copyright (C) 2016  Gregor Ihmor
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

import operators.{BooleanOperators, ComplexOperators, DoubleOperators, Operators}
import spire.algebra.{Field, IsReal, NRoot, Trig}

object implicits {
  implicit val doubleHasOperators:Operators[Double] = DoubleOperators
  implicit val booleanHasOperators:Operators[Boolean] = BooleanOperators
  implicit def spireComplexHasOperators[A: Field : Trig : NRoot : IsReal]: ComplexOperators[A] = new ComplexOperators()
}
