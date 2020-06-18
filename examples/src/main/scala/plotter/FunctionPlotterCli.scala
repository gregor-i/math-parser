package plotter

import java.io.File

import mathParser.MathParser
import org.jfree.chart.ChartPanel
import de.sciss.chart.api._

import mathParser.SpireImplicits._
import mathParser.algebra.compile.SpireCompiler.compilerDouble1

case class Config(term: String = null,
                  out: File = new File("chart.png"),
                  width: Int = ChartPanel.DEFAULT_WIDTH * 2,
                  height: Int = ChartPanel.DEFAULT_HEIGHT * 2)

object ConfigParser extends scopt.OptionParser[Config]("function plotter cli") {
  opt[File]('o', "out")
    .optional()
    .valueName("<file>")
    .action((x, c) => c.copy(out = x))

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
    .validate(term => Main.lang.parse(term)
      .toRight("Could not parse term").map(_ => ()))
    .action((x, c) => c.copy(term = x))
}

object X

object Main {
  val lang = mathParser.SpireLanguages.doubleLanguage
    .withVariables(List("x" -> X))

  def main(args: Array[String]): Unit = {
    ConfigParser.parse(args, Config()).foreach { config =>
      val parsed = lang.parse(config.term).get
      val f = lang.compile[Double => Double](parsed).get
      val `f'` = lang.compile[Double => Double](lang.derive(parsed)(X)).get

      val p1 = new XYSeries(s"f(x)": Comparable[_], false, true)
      val p2 = new XYSeries(s"f'(x)": Comparable[_], false, true)
      val ps = new XYSeriesCollection()
      ps.addSeries(p1)
      ps.addSeries(p2)

      for (x <- Range(-100, 100).map(_ * 0.1)) {
        p1.add(x, f(x))
        p2.add(x, `f'`(x))
      }

      val chart = XYLineChart(ps)
      chart.saveAsPNG(config.out.getAbsolutePath, (config.width, config.height))
    }
  }
}
