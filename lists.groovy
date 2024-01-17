def numbers = [1,2,3]
//println(numbers)
for (final def f in numbers) {
//    println(f)
}
//numbers.forEach {s->println(s)}
for (i in 0..<numbers.size()) {
//    println(numbers.get(i))
}


def objects = [1,"2", 'c' , BigDecimal.valueOf(2), BigInteger.valueOf(1), true,
               (1+2.3)]
for (i in 0..<objects.size()) {
   println(objects[i])
}

def linkedList = [2, 3, 4] as LinkedList
assert linkedList instanceof LinkedList

LinkedList otherLinked = [3, 4, 5]
assert  otherLinked instanceof LinkedList

otherLinked << 6 //相当于append
println(otherLinked)

println(otherLinked[1,2])//4,5


def max_list = [4,8,10]
max_list.every(it -> it % 2 == 0 )
max_list.every({it % 2 == 0})

println(otherLinked[2..3]) //5,6

def mut = [[0,1],[2,3]]
assert mut[0][1] == 1
