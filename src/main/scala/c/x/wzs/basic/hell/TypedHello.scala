package c.x.wzs.basic.hell

object TypedHello {
	
	trait Adder[T] {
		def add(a: T, b: T): T
	}

	implicit object StringAdder extends Adder[String] {
		override def add(a: String, b: String): String = s"$a, $b"
	}

	given intAdder: Adder[Int] with {
		override def add(a: Int, b: Int): Int = a + b
	}

	def addData[T](a: T, b: T)(implicit typedAdd: Adder[T]): T = {
		typedAdd.add(a, b)
	}

	def main(v: Array[String]) = {
		println(addData("Hell", "out of World"))
		println(addData(1,2))
		
	}
}
