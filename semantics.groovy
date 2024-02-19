x= 1
println x
x= 2.3
println x
x = false
println x
x = "GH"
println x

def (a,b,c) = [1,2.4,false]
assert a == 1 && b == 2.4  && !c

def (int i ,String j ) = [1,"hi"]
assert i ==1  && j == "hi"


def nums = [1,3,5]
def (xx,yy,zz) = nums
assert xx == 1 && yy == 3 && zz == 5


def (_,mm,nn) = "18th years old".split()
assert mm == "years" && nn == "old"


def (cc,bb,aa) = [1,.2]
assert cc == 1 && bb == .2

def (xy,yz) = [1,2,3]
assert xy == 1 && yz == 2
