package c.x.wzs.basic.hell

object FunctionStudy extends App {
	// lambda
	def exec(f: (Int, Int) => Int, x: Int, y: Int) = f(x, y)
	println(exec((x, y) => x + y, 2, 3))	// 5
	println(exec((x, y) => x - y, 3, 2))	// 1
	println(exec(_ + _, 3, 2))	// 5

	// currying
	def filter(xs: List[Int], p: Int => Boolean): List[Int] =
		if (xs.isEmpty) xs
		else if (p(xs.head)) xs.head :: filter(xs.tail, p) 
		else filter(xs.tail, p)

	def modN(n: Int)(x: Int) = ((x % n) == 0)
	val nums = List(1, 2, 3, 4, 5, 6, 7, 8)

	println(filter(nums, modN(2)))    // List(2, 4, 6, 8)
	println(filter(nums, modN(3)))    // List(3, 6)

	// closure 
	def divide(n:Int) = (x:Int) => {
		x/n
	}
	def divideFive = divide(5)
	println(divideFive(10)) // 2
}
