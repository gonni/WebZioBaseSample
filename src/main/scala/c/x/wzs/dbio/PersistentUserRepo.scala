package c.x.wzs.dbio

import io.getquill.context.ZioJdbc.DataSourceLayer
import io.getquill.{Escape, H2ZioJdbcContext}
import io.getquill.jdbczio.Quill
import io.getquill.*
import zio.*
import zio.Task
import zio.json.{DeriveJsonDecoder, DeriveJsonEncoder, JsonCodec, JsonDecoder, JsonEncoder}
import zio.schema._
import zio.schema.DeriveSchema._

case class UserTable(uuid: String, name: String, age: Int)// derives JsonCodec
object UserTable:
  given Schema[UserTable] = DeriveSchema.gen[UserTable]

class PersistentUserRepo(quill: Quill.Mysql[SnakeCase]) {
  import quill._

  private inline def qryUserTable = quote(querySchema[UserTable](entity = "USER_TABLE"))

  def uuid(): String = java.util.UUID.randomUUID().toString

  def register(name: String, age: Int): ZIO[Any, Throwable, String] = {
    (for{
      id <- Random.nextUUID
      _ <- run(qryUserTable.insertValue(lift(UserTable(id.toString, name, age))))
    } yield id.toString).mapError {
      case _ => new Exception()
    }
  }

  def lookup(id: String): Task[Option[UserTable]] =
    run(qryUserTable.filter(c => c.uuid == lift(id))).map(_.headOption)

  def users(page: Int): Task[List[UserTable]] = {
    run(qryUserTable.drop(lift(10 * page)).take(10))
  }

  extension [R, A](task: RIO[R, A])
    // [SQLITE_CONSTRAINT_UNIQUE] A UNIQUE constraint failed (UNIQUE constraint failed: contacts.email)
    def mapDatabaseException(message: String = "Unknown error when connecting to database"): ZIO[R, RuntimeException, A]
      = task.mapError(e => new RuntimeException(s"${message}: e.getMessage"))
}

object PersistentUserRepo {
  val live: ZLayer[Quill.Mysql[SnakeCase], Nothing, PersistentUserRepo] =
    ZLayer.fromFunction(new PersistentUserRepo(_))
}