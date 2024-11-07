package c.x.wzs.basic.json

import zio._
import zio.json._

object JsonMain {

  case class Person(name: String, age: Int)
  object Person {
    implicit val decoder: JsonDecoder[Person] = DeriveJsonDecoder.gen[Person]
    implicit val encoder: JsonEncoder[Person] = DeriveJsonEncoder.gen[Person]
  }

  sealed trait Fruit extends Product with Serializable
  case class Banana(curvature: Double) extends Fruit
  case class Apple (poison: Boolean)   extends Fruit

  object Fruit {
  implicit val decoder: JsonDecoder[Fruit] =
    DeriveJsonDecoder.gen[Fruit]

  implicit val encoder: JsonEncoder[Fruit] =
    DeriveJsonEncoder.gen[Fruit]
}

  def main(v: Array[String]) = {
    // println(Person("John", 42).toJson)
    val json =
    """
      |[
      |  {
      |    "Apple": {
      |      "poison": false
      |    }
      |  },
      |  {
      |    "Banana": {
      |      "curvature": 0.5
      |    }
      |  }
      |]
      |""".stripMargin

    val decoded = json.fromJson[List[Fruit]]
    // println(decoded)
    
    // ----
    val fruits = List(Apple(false), Banana(0.7))
    val json1 = fruits.toJson
    println(json1)
    
  }
}
