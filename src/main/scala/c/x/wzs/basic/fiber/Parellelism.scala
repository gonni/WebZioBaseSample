package c.x.wzs.basic.fiber

import c.x.wzs.basic.utils.*
import zio.*
import java.io.FileWriter
import java.io.File

object Parellelism extends ZIOAppDefault {

  val meaningOfLife = ZIO.succeed(42)
  val favLang = ZIO.succeed("Scala")
  val combined = meaningOfLife.zip(favLang)

  val combinePar = meaningOfLife.zipPar(favLang)

  /**
    * -start each on fibers
    * what if one fails? the other should be interrupted
    * what if one is interrupted? the entire thing should be interrupted
    * what if the whole thing is interrupted? need to interrupt both effects
    */

  def myZipPar[R,E,A,B](zioa: ZIO[R,E,A], ziob:ZIO[R,E,B]): ZIO[R,E,(A,B)] = {
    val exits = for {
      fiba <- zioa.fork
      fibb <- ziob.fork
      exita <- fiba.await
      exitb <- exita match {
        case Exit.Success(value) => fibb.await
        case Exit.Failure(_) => fibb.interrupt
      }
    } yield (exita, exitb)

    exits.flatMap {
      case (Exit.Success(a), Exit.Success(b)) => ZIO.succeed((a, b))
      case (Exit.Success(_), Exit.Failure(cause)) => ZIO.failCause(cause)
      case (Exit.Failure(cause), Exit.Success(_)) => ZIO.failCause(cause)
      case (Exit.Failure(c1), Exit.Failure(c2)) => ZIO.failCause(c1 && c2)
    }
  }

  val effects: Seq[ZIO[Any, Nothing, Int]] = (1 to 10).map(i => ZIO.succeed(i).debugThread)
  val collectedValue: ZIO[Any, Nothing, Seq[Int]] = ZIO.collectAllPar(effects)  // traverse

  val printlnParallel = ZIO.foreachPar((1 to 10).toList)(i => ZIO.succeed(println(i)))
  
  // reduceAllPar, mergeAllPar
  val sumPar = ZIO.reduceAllPar(ZIO.succeed(0), effects)(_ + _)
  val sumPar_v2 = ZIO.mergeAllPar(effects)(0)(_ + _)

  def generateRandomFile(path: String): Unit = {
    val random = scala.util.Random
    val chars = 'a' to 'z'
    val nWords = random.nextInt(2000)

    val content = (1 to nWords)
      .map(_ => (1 to random.nextInt(10)).map(_ => chars(random.nextInt(26))).mkString)
      .mkString(" ")

    val writer = new FileWriter(new File(path))
    writer.write(content)
    writer.flush()
    writer.close()
  }

  def countWords(path: String): UIO[Int] = 
    ZIO.succeed {
      val source = scala.io.Source.fromFile(path)
      val nWords = source.getLines().mkString(" ").split(" ").count(_.nonEmpty)
      println(s"Counted $nWords in $path")
      source.close()
      nWords
    }
  
  val app =for {
		lst <- ZIO.foreachPar((1 to 10).toList)(i => ZIO.succeed(countWords(s"samples/testfile_$i.txt")))
		cnt <- ZIO.reduceAllPar(ZIO.succeed(0), lst)(_ + _)
	} yield cnt
  override def run: ZIO[Any & (ZIOAppArgs & Scope), Any, Any] = app.debug
    // ZIO.succeed((1 to 10).foreach(i => generateRandomFile(s"samples/testfile_$i.txt")))
}
