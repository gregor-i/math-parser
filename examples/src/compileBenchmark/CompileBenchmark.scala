package compileBenchmark

import mathParser.MathParser
import mathParser.slices.AbstractSyntaxTree
import org.jfree.data.xy.{XYSeries, XYSeriesCollection}

import scalax.chart.api._
object Main {

  def astSize[Lang <: AbstractSyntaxTree](node: Lang#Node): Int =
    node.fold[Int](
      _ => 1,
      (op, size) => size + 1,
      (op, left, right) => left + right + 1,
      _ => 1)

  def timed[A](op: =>A): (A, Double) = {
    val threadMXBean = java.lang.management.ManagementFactory.getThreadMXBean
    op
    op
    System.gc()
    val start = threadMXBean.getCurrentThreadCpuTime
    val res = op
    val end = threadMXBean.getCurrentThreadCpuTime
    (res, end - start)
  }

  def main(args:Array[String]): Unit = {
    val lang = MathParser.doubleLanguage('x)

    val evaluationTime = new XYSeries("evaluation time": Comparable[_], true, true)
    val compileTime = new XYSeries("compile time": Comparable[_], true, true)
    val compiledEvaluationTime = new XYSeries("evalution time of compiled ast": Comparable[_], true, true)
    val chart1 = new XYSeriesCollection()
    val chart2 = new XYSeriesCollection()
    chart1.addSeries(evaluationTime)
    chart1.addSeries(compiledEvaluationTime)
    chart2.addSeries(compileTime)

    for{
      function <- Data.functions
      ast <- lang.parse(function)
      size = astSize(ast)
    } {
      val (compiled, compileDuration) = timed(lang.compile1(ast))
      val x = Math.random()*10 - 5
      val (_, evaluateDuration) = timed(
        for(_ <- 0 until 10000000)
          lang.evaluate(ast){case 'x => x}
      )
      val (_, compileEvaluateDuration) = timed(
        for(_ <- 0 until 10000000)
          compiled.get(x)
      )
      evaluationTime.add(size, evaluateDuration)
      compileTime.add(size, compileDuration)
      compiledEvaluationTime.add(size, compileEvaluateDuration)

      println(size, compileDuration, compileEvaluateDuration, evaluateDuration)
    }

    {
      val chart = XYLineChart(chart1)
      chart.title = s"Benchmarking evaluation time"
      chart.saveAsPNG("benchmark_evaluation_time.png")
    }
    {
      val chart = XYLineChart(chart2)
      chart.title = s"Benchmarking compilation time"
      chart.saveAsPNG("benchmark_compilation_time.png")
    }
  }
}

private object Data{
  val functions = Seq("x^(3+10)",
    "x^(3+10) -1",
    "x^(10) -1",
    "x^(3) -1",
    "x^(x) -1",
    "x^(x) +1",
    "x^(2) +x^(2) +1",
    "x^(-x) +1",
    "x*x*x - 5",
    "x*x*x+5",
    "x^3-x^2+x-1",
    "sin(x) + x^3+1",
    "cos(x)+1",
    "cos(x)-1",
    "x*x*x+5",
    "x*x*x*x+5",
    "sin(x) + (x+1)*(x-1)+5",
    "x^x - 0.99",
    "x^x*(x+2)",
    "(x+2)*(x-0.5)*(x+log(x))*(x-5)",
    "(x+2)*(x-0.5)*(x+sin(x))*(x-2)*(x+2)*(x-0.5)*(x+1)*(x-2)",
    "(x+2)*(x-0.5)*(x+sin(x))*(x-2)*(x-0.5)*(x+3)",
    "sin(x)*sin(5*x)",
    "sin(x - 1)*sin(3*x) + 0.1",
    "sin(x - 1)*sin(-5*x) - 0.1",
    "x^sin(x) + 1",
    "x^sin(x) - 1 - sin(x)^x + 1",
    "sin(x)*cos(5*x) + 0.5",
    "sin(x*10)*cos(9*x*10) + 0.5",
    "sin(x*5)*cos(11*x*5)",
    "sin(5*x*5)*cos(x*5) + 0.5",
    "e^-(x*x) + e^(-5*x*x)",
    "sin(x)^cos(x) - 0.1",
    "x^(25+x)",
    "e^x - 5",
    "e^(3*x) - 9*0.5",
    "e^(x*x) - 5*0.5",
    "e^(x+x*x) - x*0.5",
    "(x-1)^4*(x)^4",
    "35*x^9-180*x^7+378*x^5-420*x^3+315*x",
    "35*x^9-180*x^7+3798*x^5-420*x^3+315+x")
}
