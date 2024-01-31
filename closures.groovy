def items = 1
println {items++}

println {-> ++items}

def cl = {String x,y -> println "x:${x} and y: ${y}"}
cl("hao","hello")
cl.call("你","号")


def lister = {e-> println "Clicked on $e.source"}
assert lister instanceof Closure


def defaultClosure = {int x ,int y = 4 -> println "${x} + ${y} = ${x + y}"}
defaultClosure(2)

//默认参数it
def clp = {println "${it.toUpperCase()}"}
clp("jack")

def multiContact = {int n ,String ... values -> values.join('')*n}
println multiContact(2,"like", "be")


class Enclosing{
  void run(){
    def whatIsThisObject = {getThisObject()}
    assert whatIsThisObject() == this 
    def whatIsThis = {this}
    assert whatIsThis() == this
  
    def whatIsThisOwner = { getOwner() }
    assert whatIsThisOwner() == this

    def whatOwner = {owner}
    assert whatOwner() == this
  }
}

def enc = new Enclosing()
enc.run()

class EnclosedInInnerClass{
  class Inner{
    Closure cl = {this}
    Closure clz = {owner}
  }
  void run(){
    def inner = new Inner()
    assert inner.cl() == inner
    assert inner.clz() == inner
  }
}

def cp = new EnclosedInInnerClass()
cp.run()

class NestedClosures{
  void run(){
    def nestedClosures = {
      def cl = {this}
      cl()
    }
    assert nestedClosures() == this

    def nestedOwnClosures = {
      def clz = {owner}
      clz()
    }
    //这里存在区别，仅闭包本身
    nestedOwnClosures() == nestedOwnClosures
  }
}

def nc = new NestedClosures()
nc.run()

class Person{
  String name
  int age
  String toString() { "this is to String values ($name,$age) " }

  String dump(){
    def cl = {
      String message = this.toString()
      println message
      message
    }
    cl()
  }
}
def p = new Person(name:"Jance",age:17)
assert p.dump() == "this is to String values (Jance,17) "

class Enclosed {
  void run(){
    def cl = {getDelegate()}
    def cl2 = {delegate}
    assert cl() == cl2()
    assert cl() == this
    def enclosed = {
      {-> delegate}.call()
    }
    assert enclosed() == enclosed
  }
}
def el = new Enclosed()
el.run()

class Per {
  String name
}
class Stu {
  String name
}
def po = new Per(name:"Jack")
def to = new Stu(name: "Lucy")

def upperCaseName = {delegate.name.toUpperCase()}

upperCaseName.delegate = po 
assert upperCaseName() == "JACK"

upperCaseName.delegate = to
assert upperCaseName() == "LUCY"

