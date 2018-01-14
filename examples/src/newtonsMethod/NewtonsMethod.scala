package newtonsMethod

sealed trait NewtonResponse
case class Converged(steps: Int, root: Double, epsilon: Double) extends NewtonResponse
case class Diverged() extends NewtonResponse

case class NewtonsMethod(maximumSteps: Int, epsilon: Double) {
  def solve(f: Double => Double, `f'`: Double => Double)(x0: Double): NewtonResponse = {
    var x = x0
    for(i <- 0 until maximumSteps) {
      if(f(x).abs < epsilon)
        return Converged(i, x, f(x).abs)
      else
        x = x -  f(x) / `f'`(x)
    }
    return Diverged()
  }
}
