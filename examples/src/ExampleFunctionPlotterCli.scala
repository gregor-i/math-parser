import java.io.File

import mathParser.MathParser
import org.jfree.chart.ChartPanel

import scalax.chart.api._

case class Config(term: String = null,
                  out: File = new File("chart.png"),
                  width: Int = ChartPanel.DEFAULT_WIDTH,
                  height: Int = ChartPanel.DEFAULT_HEIGHT,
                  derive: Boolean = false)

object ConfigParser extends scopt.OptionParser[Config]("scopt") {
  opt[File]('o', "out")
    .optional()
    .valueName("<file>")
    .action((x, c) => c.copy(out = x))

  opt[Boolean]('d', "derive")
    .optional()
    .valueName("<boolean>")
    .action((x, c) => c.copy(derive = x))

  opt[Int]('w', "width")
    .optional()
    .valueName("<width in px>")
    .action((x, c) => c.copy(width = x))

  opt[Int]('h', "height")
    .optional()
    .valueName("<height in px>")
    .action((x, c) => c.copy(height = x))

  arg[String]("term")
    .required()
    .validate(term => MathParser.doubleLanguage('x).parse(term)
      .toRight("Could not parse term").map(_ => ()))
    .action((x, c) => c.copy(term = x))
}


object Main {

  def execute(config: Config): Unit = {
    val lang = MathParser.doubleLanguage('x)
    val parsed = lang.parse(config.term).get
    val f = lang.compile1(parsed).get
    val `f'` = lang.compile1(lang.derive(parsed)('x)).get

    val p1 = new XYSeries(s"f(x)": Comparable[_], false, true)
    val p2 = new XYSeries("f'(x)": Comparable[_], false, true)
    val ps = new XYSeriesCollection()
    ps.addSeries(p1)
    if (config.derive)
      ps.addSeries(p2)

    for (x <- -10d to 10d by 0.1) {
      p1.add(x, f(x))
      p2.add(x, `f'`(x))
    }


    val chart = XYLineChart(ps)
    chart.title = s"f(x) = ${config.term}"
    chart.saveAsPNG(config.out.getAbsolutePath, (config.width, config.height))
  }

  def main(args: Array[String]): Unit = {
    val parsedConfig = ConfigParser.parse(args, Config())
    parsedConfig.foreach(execute)
  }
}