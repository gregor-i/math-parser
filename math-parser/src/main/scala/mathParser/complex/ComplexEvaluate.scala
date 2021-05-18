package mathParser.complex

import mathParser.Evaluate

import ComplexBinaryOperator.*
import ComplexUnitaryOperator.*

object ComplexEvaluate {

  import Math._

  def apply[V]: Evaluate[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] =
    new Evaluate[ComplexUnitaryOperator, ComplexBinaryOperator, Complex, V] {
      override def executeUnitary(uo: ComplexUnitaryOperator, a: Complex): Complex = uo match {
        case Neg  => complexNeg(a)
        case Sin  => complexSin(a)
        case Cos  => complexCos(a)
        case Tan  => complexTan(a)
        case Asin => complexAsin(a)
        case Acos => complexAcos(a)
        case Atan => complexAtan(a)
        case Sinh => complexSinh(a)
        case Cosh => complexCosh(a)
        case Tanh => complexTanh(a)
        case Exp  => complexExp(a)
        case Log  => complexLog(a)
      }

      override def executeBinaryOperator(bo: ComplexBinaryOperator, a: Complex, b: Complex): Complex =
        bo match {
          case Plus    => complexPlus(a, b)
          case Minus   => complexMinus(a, b)
          case Times   => complexTimes(a, b)
          case Divided => complexDivided(a, b)
          case Power   => complexPower(a, b)
        }
    }

  @inline private final def sq(a: Double): Double = a * a

  @inline private final def abs(c: Complex): Double = Math.sqrt(sq(c.real) + sq(c.imag))

  @inline private final def complexSqrt(z: Complex): Complex = {
    val length_z: Double = abs(z)
    val b                = sqrt((length_z + z.real) / 2.0)
    val c                = sqrt((length_z - z.real) / 2.0)
    if (z.imag < 0.0)
      Complex(b, -c)
    else
      Complex(b, c)
  }
  @inline private def complexPlus(a: Complex, b: Complex) =
    Complex(a.real + b.real, a.imag + b.imag)

  @inline private def complexMinus(a: Complex, b: Complex) =
    Complex(a.real - b.real, a.imag - b.imag)

  @inline private def complexTimes(a: Complex, b: Complex) =
    Complex(a.real * b.real - a.imag * b.imag, a.real * b.imag + a.imag * b.real)

  @inline private def complexDivided(a: Complex, b: Complex) = {
    val denom = sq(b.real) + sq(b.imag)
    Complex((a.real * b.real + a.imag * b.imag) / denom, (a.imag * b.real - a.real * b.imag) / denom)
  }

  @inline private def complexPower(a: Complex, b: Complex) = {
    val length_a = abs(a)
    if (abs(b) == 0d) {
      Complex(1d, 0d)
    } else if (length_a == 0d) {
      Complex(0d, 0d)
    } else {
      val arg_a     = atan2(a.imag, a.real);
      val magnitude = pow(length_a, b.real) / exp(arg_a * b.imag)
      val angle     = arg_a * b.real + log(length_a) * b.imag
      Complex(magnitude * cos(angle), magnitude * sin(angle))
    }
  }

  @inline private def complexNeg(a: Complex) =
    Complex(-a.real, -a.imag)

  @inline private def complexSin(a: Complex) =
    Complex(sin(a.real) * cosh(a.imag), cos(a.real) * sinh(a.imag))

  @inline private def complexCos(a: Complex) =
    Complex(cos(a.real) * cosh(a.imag), -sin(a.real) * sinh(a.imag));

  @inline private def complexTan(a: Complex) = {
    val r2 = a.real + a.real
    val i2 = a.imag + a.imag
    val d  = cos(r2) + cosh(i2)
    new Complex(sin(r2) / d, sinh(i2) / d)
  }

  @inline private def complexAsin(z: Complex) = {
    val z2 = complexTimes(z, z)
    val s  = complexSqrt(Complex(1.0 - z2.real, -z2.imag))
    val l  = complexLog(Complex(s.real - z.imag, s.imag + z.real))
    Complex(l.imag, -l.real);
  }

  @inline private def complexAcos(z: Complex) = {
    val z2 = complexTimes(z, z)
    val s  = complexSqrt(Complex(1.0 - z2.real, -z2.imag))
    val l  = complexLog(Complex(z.real + s.imag, z.real + s.imag))
    Complex(l.real, -l.imag)
  }

  @inline private def complexAtan(z: Complex) = {
    val n = Complex(z.real, z.imag + 1.0);
    val d = Complex(-z.real, 1.0 - z.imag);
    val l = complexLog(complexDivided(n, d));
    Complex(l.imag / -2.0, l.real / 2.0);
  }

  @inline private def complexSinh(z: Complex) =
    Complex(sinh(z.real) * cos(z.imag), cosh(z.real) * sin(z.imag))

  @inline private def complexCosh(z: Complex) =
    Complex(cosh(z.real) * cos(z.imag), sinh(z.real) * sin(z.imag))

  @inline private def complexTanh(z: Complex) = {
    val r2 = z.real + z.real
    val i2 = z.imag + z.imag
    val d  = cos(r2) + cosh(i2)
    Complex(sinh(r2) / d, sin(i2) / d)
  }

  @inline private def complexExp(a: Complex) = {
    val f = exp(a.real)
    Complex(f * cos(a.imag), f * sin(a.imag))
  }

  @inline private def complexLog(a: Complex) =
    if (abs(a) == 0.0)
      Complex(Double.NaN, Double.NaN)
    else
      Complex(log(abs(a)), atan2(a.imag, a.real))

}
