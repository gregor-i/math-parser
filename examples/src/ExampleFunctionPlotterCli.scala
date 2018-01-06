import java.io.File

import mathParser.MathParser

import scalax.chart.api._

case class Config(term: String = null,
                  out: File = new File("chart.png"),
                  title: String = "")

object ConfigParser extends scopt.OptionParser[Config]("scopt") {
  opt[File]('o', "out")
    .optional()
    .valueName("<file>")
    .action((x, c) => c.copy(out = x))

  opt[String]('t', "title")
    .optional()
    .valueName("<title>")
    .action((x, c) => c.copy(title = x))

  arg[String]("term")
    .required()
    .action((x, c) => c.copy(term = x))
}


object Main {

  def execute(config: Config): Unit = {
    val p1 = new XYSeries(config.title, false, true)

    val lang = MathParser.doubleLanguage('x)
    val f = lang.compile1(lang.parse(config.term).get).get

    val graph = for (x <- -10d to 10d by 0.1)
      yield (x, f(x))

    val chart = XYLineChart(graph)
    chart.title = config.title
    chart.saveAsPNG(config.out.getAbsolutePath)
  }

  def main(args: Array[String]): Unit = {
    val parsedConfig = ConfigParser.parse(args, Config())
    println(parsedConfig)
    parsedConfig.foreach(execute)
  }
}