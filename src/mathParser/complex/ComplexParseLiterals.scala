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

package mathParser.complex

import mathParser.ParseLiterals
import mathParser.double.DoubleParseLiterals
import spire.math.Complex

object ComplexParseLiterals extends ParseLiterals[Complex[Double]]{
  def tryToParse(s:String): Option[Complex[Double]] = DoubleParseLiterals.tryToParse(s).map(Complex(_, 0))
}
