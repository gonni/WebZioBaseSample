package c.x.wzs.service

import zio.*


trait KeyValueStore[K, V, E, F[_, _]] {
  def get(key: K): F[E, V]
  def set(key: K, value: V): F[E, V]
  def remove(key: K): F[E, Unit]
}


import zio._

object KeyValueStore {
  def get[K: Tag, V: Tag, E: Tag](key: K): ZIO[KeyValueStore[K, V, E, IO], E, V] =
    ZIO.serviceWithZIO[KeyValueStore[K, V, E, IO]](_.get(key))

  def set[K: Tag, V: Tag, E: Tag](key: K, value: V): ZIO[KeyValueStore[K, V, E, IO], E, V] =
    ZIO.serviceWithZIO[KeyValueStore[K, V, E, IO]](_.set(key, value))

  def remove[K: Tag, V: Tag, E: Tag](key: K): ZIO[KeyValueStore[K, V, E, IO], E, Unit] =
    ZIO.serviceWithZIO(_.remove(key))
}

//object KeyValueStore {
//  def get[K: Tag, V: Tag, E: Tag, F[_, _] : TagKK](key: K): ZIO[KeyValueStore[K, V, E, F], Nothing, F[E, V]] =
//    ZIO.serviceWith[KeyValueStore[K, V, E, F]](_.get(key))
//
//  def set[K: Tag, V: Tag, E: Tag, F[_, _] : TagKK](key: K, value: V): ZIO[KeyValueStore[K, V, E, F], Nothing, F[E, V]] =
//    ZIO.serviceWith[KeyValueStore[K, V, E, F]](_.set(key, value))
//
//  def remove[K: Tag, V: Tag, E: Tag, F[_, _] : TagKK](key: K): ZIO[KeyValueStore[K, V, E, F], E, Unit] =
//    ZIO.serviceWith(_.remove(key))
//}

case class InmemoryKeyValueStore(map: Ref[Map[String, Int]])
  extends KeyValueStore[String, Int, String, IO] {

  override def get(key: String): IO[String, Int] =
    map.get.map(_.get(key)).someOrFail(s"$key not found")

  override def set(key: String, value: Int): IO[String, Int] =
    map.update(_.updated(key, value)).map(_ => value)

  override def remove(key: String): IO[String, Unit] =
    map.update(_.removed(key))
}

object InmemoryKeyValueStore {
  def layer: ULayer[KeyValueStore[String, Int, String, IO]] =
    ZLayer {
      Ref.make(Map[String, Int]()).map(InmemoryKeyValueStore.apply)
    }
}


object MainApp extends ZIOAppDefault {

  val myApp: ZIO[KeyValueStore[String, Int, String, IO], String, Unit] =
    for {
      _ <- KeyValueStore.set[String, Int, String]("key1", 3).debug
      _ <- KeyValueStore.get[String, Int, String]("key1").debug
      _ <- KeyValueStore.remove[String, Int, String]("key1")
      _ <- KeyValueStore.get[String, Int, String]("key1").either.debug
    } yield ()

  def run = myApp.provide(InmemoryKeyValueStore.layer)

}

// Output:
// 3
// 3
// not found