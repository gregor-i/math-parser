package mathParser.double

import spire.implicits._
import mathParser.algebra.SpireLanguage

class DoubleLanguage(freeVariables: Seq[Symbol])
  extends SpireLanguage[Double](freeVariables)
