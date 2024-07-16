package c.x.wzs.json

import zio._
import zio.json._
import zio.schema.{DeriveSchema, Schema}

case class Person(name: String, age: Int)

object Person {
  implicit val schema: Schema[Person] =
    DeriveSchema.gen
  implicit val jsonCodec: zio.json.JsonCodec[Person] =
    zio.schema.codec.JsonCodec.jsonCodec(schema)
}

object Main extends ZIOAppDefault {
  def run = for {
    _ <- ZIO.debug("JSON Codec Example:")
    person: Person  = Person("John", 42)
    encoded: String = person.toJson
    _       <- ZIO.debug(s"person object encoded to JSON string: $encoded")
    decoded <- ZIO.fromEither(Person.jsonCodec.decodeJson(encoded))
    _       <- ZIO.debug(s"JSON object decoded to Person class: $decoded")
  } yield ()
}
