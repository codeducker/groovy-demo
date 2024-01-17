
String[] array = ['hello', "world"]
assert array instanceof String[]

def array2 = ["hello", "world"] as String[]
assert array2 instanceof String[]

assert array2.length == array2.size()

def array3 = new Integer[3][3]
array3[0][0] = 3
array3[0][1] = 4
assert array3.length == 3

array3 = [[1,2,3],[4,5,6],[7,8,9]]
assert array3.size() == 3
println(array3.sum(t->t.sum())) //执行闭包加法

def primes = new int[] {1,2,3,4}
assert primes.length == 4
println(primes.class.name)//[I获取数组元素类型

def stValues = ["hello", " o ", "world"] as String[]
assert  stValues.sum() == "hello o world"

assert stValues.every({it.contains("o")})

def int_arr = [1,2,3] as int[]
assert !( int_arr instanceof List )
assert int_arr instanceof int[]
