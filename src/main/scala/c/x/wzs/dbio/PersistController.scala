package c.x.wzs.dbio

import zio.*
import zio.http.*
import zio.schema.codec.JsonCodec.schemaBasedBinaryCodec

case class PersistController private(persistUserRepo: PersistentUserRepo) {

  def routes: Routes[Any, Response] = Routes(
    Method.GET / "dbUsers" -> handler { (req: Request) =>
      println("dbUsers fired ..")
      persistUserRepo.users(0)
        .mapBoth(
          fail =>
            println()
            fail.printStackTrace()
            Response.internalServerError(s"Error :" + fail.getMessage),
          users => Response(body = Body.from(users))
        )
    }
  )

}

object PersistController {
  val live = ZLayer.derive[PersistController]
}