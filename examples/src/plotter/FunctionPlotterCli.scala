package plotter

import mathParser.MathParser

import scalax.chart.api._

case class Config(term: String = null)

object ConfigParser extends scopt.OptionParser[Config]("scopt") {
  arg[String]("term")
    .required()
    .validate(term => MathParser.doubleLanguage('x).parse(term)
      .toRight("Could not parse term").map(_ => ()))
    .action((x, c) => c.copy(term = x))
}


object Main {
  def main(args: Array[String]): Unit = {
    ConfigParser.parse(args, Config()).foreach{ config =>
      val lang = MathParser.doubleLanguage('x)
      val parsed = lang.parse(config.term).get
      val f = lang.compile1(parsed).get

      val p1 = new XYSeries(s"f(x)": Comparable[_], false, true)
      val ps = new XYSeriesCollection()
      ps.addSeries(p1)

      for (x <- -10d to 10d by 0.1) {
        p1.add(x, lang.evaluate(parsed){case 'x => x})
      }

      val chart = XYLineChart(ps)
      chart.title = s"f(x) = ${config.term}"
      chart.show()
      //    chart.saveAsPNG(config.out.getAbsolutePath, (config.width, config.height))
    }
  }
}