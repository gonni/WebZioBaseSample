package c.x.wzs.basic.hell

object Monad extends App {
	trait MyMonad[M[_]] {
		def pure[T](t: T): M[T]
		def map[A, B](ma: M[A])(f: A => B): M[B]
		def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B] 
	}
}
