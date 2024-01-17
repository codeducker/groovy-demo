def i = 1
assert i instanceof Integer

i  = Integer.MAX_VALUE
assert i instanceof Integer

i = i+3

println(i)
assert i instanceof Integer

def c = 2147483648
assert c instanceof Long

int a = 10000
assert  a == 10_000

BigInteger bi = BigInteger.valueOf(1)
println(bi +1)

BigDecimal ci = BigDecimal.ONE;
println(ci +1.2)

a =5
b =3
println(5 / 3)
println()

println(bi / 3)
float ac = 1.23
println(ac)

assert .321 == new BigDecimal('.321')

float sx = 0.9
BigDecimal sc = BigDecimal.valueOf(0.8)
Double ab = sx +sc
assert ab instanceof Double

int ai = 8
int bc = 3
println(ai/bc)
println(ai.intdiv(bc))


println((0.5 ** -0.3f) instanceof Double)


 //BigDecimal / BigInteger G / g
 //Long L / l 
 //Integer I / i 
 // Double D / d 
 // Float F / f 
assert 0.12D.class == Double.class


assert 2 ** 3 instanceof Integer
