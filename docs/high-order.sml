fun filter(f,xs) =
	case xs of
       [] => []
     | x::xs' => if f(x) 
     			 then x :: filter(f,xs') 
     			 else filter(f,xs')

fun even(n) = n mod 2 = 0
fun odd(n) = n mod 2 <> 0

fun map(f, xs) =
	case xs of
	   [] => []
	 | x::xs' => f(x) :: map(f, xs')


fun square(n) = n * n
fun factorial(n) = if n = 0 then 1 else n * factorial(n-1)

fun sum(x,y) = x + y
fun prod(x, y) = x * y

fun reduce(f, acc, xs) =
	case xs of
	   [] => acc
	 | x::xs' => reduce(f, f(acc,x), xs')
