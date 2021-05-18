package plotter

import de.sciss.chart.api._
import mathParser.SpireImplicits.given
import org.jfree.chart.ChartPanel

import java.io.File

case class Config(
    term: String = null,
    out: File = new File("chart.png"),
    width: Int = ChartPanel.DEFAULT_WIDTH * 2,
    height: Int = ChartPanel.DEFAULT_HEIGHT * 2
)

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
    .validate(
      term =>
        Main.lang
          .parse(term)
          .toRight("Could not parse term")
          .map(_ => ())
    )
    .action((x, c) => c.copy(term = x))
}

object X

object Main {
  val lang = mathParser.SpireLanguages.doubleLanguage
    .withVariables(List("x" -> X))

  def main(args: Array[String]): Unit = {
    ConfigParser.parse(args, Config()).foreach { config =>
      val parsed = lang.parse(config.term).get
      val derived = lang.derive(parsed)(X)
      val f      = (x: Double) => lang.evaluate(parsed){ case X => x }
      val `f'`   = (x: Double) => lang.evaluate(derived){ case X => x }

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
