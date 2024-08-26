package c.x.wzs.client

import java.util.UUID
import zio.json.*
import zio.schema._
import zio.schema.DeriveSchema._

case class KosdaqIndex(kosdaq3d: Float)

object KosdaqIndex:
  given Schema[KosdaqIndex] = DeriveSchema.gen[KosdaqIndex]
  implicit val decoder: JsonDecoder[KosdaqIndex] = DeriveJsonDecoder.gen[KosdaqIndex]
