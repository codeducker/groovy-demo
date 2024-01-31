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

def clpp = {name.toUpperCase()}
clpp.delegate = po 
assert clpp() == "JACK"
import static groovy.test.GroovyAssert.shouldFail
class Test {
  def x = 30
  def y = 40
  //这里是 默认策略 Closure.OWNER_FIRST
  void run(){
    def data = [x:10,y:20]
    def clp = {y = x+y }
    clp.delegate = data 
    clp()
    assert x == 30
    assert y == 70
    assert data == [x:10, y:20]
    assert clp.owner == this
  }

  //这里设置策略为 Closure.DELEGATE_FIRST
  void run2(){
    def data = [x:10,y:20]
    def clp = {y = x+y }
    clp.delegate = data
    clp.resolveStrategy = Closure.DELEGATE_FIRST
    clp()
    assert x == 30
    assert y == 40
    assert data == [x:10, y:30]
    assert clp.owner == this
    assert clp.delegate == data
  }

  //这里设置为 Closure.OWNER_ONLY
  void run3(){
    def data =[x:10,y:20,z:30]
    def clp = {y = x+y+z}
    clp.delegate = data 
    clp.resolveStrategy = Closure.OWNER_ONLY 
    shouldFail(groovy.lang.GroovyRuntimeException){
      clp()
      println x
      println y
      assert y == 40 //这里代理为 实例本身 因为未提供z所以会报错
      println data
    }
  }

  def c = 40
  //这里设置为 Closure.DELEGATE_ONLY
  void run4(){
    def data = [x:10,y:20]
    def clp = {y = x+y+c}
    clp.delegate = data 
    clp.resolveStrategy = Closure.DELEGATE_ONLY
    shouldFail(groovy.lang.GroovyRuntimeException){
      clp() //此时无法从data中获取到c的值.
      println x 
      println y
      println data
    }
  }
}

def t = new Test()
// t.run()
// t.run2()
// t.run3()
t.run4()
//闭包解决策略
//Closure.OWNER_FIRST
//Closure.DELEGATE_FIRST
//Closure.OWNER_ONLY
//Closure.DELEGATE_ONLY
//Closure.TO_SELF
