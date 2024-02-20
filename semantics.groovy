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

import groovy.transform.Immutable

@Immutable
class Coordinates {
  double latitude
  double longitude

  double getAt(int index){
    if(index == 0) {
      return latitude
    }else if(index == 1){
      return longitude
    }else throw new Exception('Wrong Coordinates index')
  }
}

def coordinates =  new Coordinates(latitude:32.123,longitude:125.212)
def (lat,lot) = coordinates
assert lat == 32.123
assert lot == 125.212


def xv = 1.23
def result = ""
switch(xv){
  case "foo":{
    break
  }
  case 12..30 :{
    break
  }
  case [4,5,6] :{
    break
  }
  case Integer:{
    break 
  }
  case ~/fo*/ :{
    break 
  }
  case (xv < 0):{
    break
    }
}

def baNums = []
for (def (String u,int v) = ['bar',23]; v< 25 ; u++,v++){
  baNums << "$u $v"
}
println baNums

def xli = 0
for (ic in 0..10){
  print " "+ ( xli + ic )
}

println ""
for (ibn in [1,2,3,44,5]){
  print " " + ibn
}
println ""

for (xo in (3..15).toArray()){
  print " " + xo
}
println ""

for(e in ['key':12,'map':23,'lk':34]){
  print " "+e.value
}
println ""

def map = ['a':1,'b':2]
for(xp in map.values()){
  print " " + xp
}
println ""

import java.io.*
class FromResource extends ByteArrayInputStream {
  @Override
  public void close() throws IOException{
    super.close()
    println "FromResource Close"
  }
  FromResource(String input){
    super(input.toLowerCase().bytes)
  }
}

class ToResource extends ByteArrayOutputStream {
  @Override
  public void close() throws IOException {
    super.close()
    println "ToResource Close"
  }
}

def writeSheet(s){
  try (
    FromResource fr = new FromResource(s)
    ToResource tr = new ToResource()
  ){
    tr << fr 
    return tr.toString()
  }
}

def writeSheet2(s){
  FromResource fr = new FromResource(s)
  try(fr; ToResource tr = new ToResource()){
    tr << fr 
    return tr.toString()
  }
}

assert writeSheet("iam").contains('i')
assert writeSheet2('iam').contains('a')


def xn = 2 
def yn = 7
def zn = 5
def calc = {av,bv -> av*bv+1}
// assert calc(xn,yn) == [xn,zn].sum() : "Incorrect Results"

for(xu in 0..10){
  for(mu = 0 ; mu < xu; mu++){
    println "mu=$mu"
    if(mu == 5) {
      break exit
    }
  }
  exit: println "xu=$xu"
}


void aMthodFoo() {println "This is aMethodFoo."}
assert ['aMthodFoo'] == this.class.methods.name.grep(~/.*Foo/)
