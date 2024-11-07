error id: file://<WORKSPACE>/src/main/scala/c/x/wzs/basic/hell/TypedHello.scala:[157..160) in Input.VirtualFile("file://<WORKSPACE>/src/main/scala/c/x/wzs/basic/hell/TypedHello.scala", "package c.x.wzs.basic.hell

import scala.meta.contrib.implicits.implicits

object TypedHello {
	
	trait Adder[T] {
		def add(a: T, b: T): T
	}

	object 



	def addData[T](a: T, b: T)(implicit typedAdd: Adder[T]) = {

	}
	
	
	
	def main(v: Array[String]) = {

	}
}
")
file://<WORKSPACE>/src/main/scala/c/x/wzs/basic/hell/TypedHello.scala
file://<WORKSPACE>/src/main/scala/c/x/wzs/basic/hell/TypedHello.scala:15: error: expected identifier; obtained def
	def addData[T](a: T, b: T)(implicit typedAdd: Adder[T]) = {
 ^
#### Short summary: 

expected identifier; obtained def