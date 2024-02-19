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

class PersonAc {
  String name
  int age 
  def fetchAge = {age}
}

class ThingAc {
  String name
  def propertyMissing(String name) {-1}
}
def pp = new PersonAc(name:"luck",age:12)
def tt = new ThingAc(name:"lickThing")
def clo = pp.fetchAge
clo.resolveStrategy=Closure.DELEGATE_ONLY
clo.delegate = pp 
assert clo() == 12 
clo.delegate = tt 
assert clo() == -1
def xx = 1
def gs = "x=${xx}"
assert gs == "x=1"
//此时这里的${xx}只是个表达式,当GString创建时该值就已经确定
// xx = 2 
// assert gs == "x=2"

def xx1 = "x=${->xx}"
assert xx1 == "x=1"
xx = 2 
assert xx1 == "x=2"


class Cert{
  String name 
  String toString() {name}
}
def cr1 = new Cert(name:"luck")
def cr2 = new Cert(name:"back")
def clso = cr1         
def gso = "Name: ${clso}"                   
assert gso == 'Name: luck'                
clso = cr2                                
assert gso == 'Name: luck'                
cr1.name = 'Lucy'                       
assert gso == 'Name: Lucy'    

def nCopies = { int n, String str -> str*n }    
def twice = nCopies.curry(2)                    
assert twice('bla') == 'blabla'                 
assert twice('bla') == nCopies(2, 'bla') 

def blah = nCopies.rcurry('bla')
assert blah(2) == 'blabla'
assert blah(2) == nCopies(2,'bla')

def volume = {double x ,double y ,double z -> 1 * x + 2 * y +3*z}
def fixedY = volume.ncurry(1,2d)
assert fixedY(1,3) == volume(1,2,3)

def fixedYZ = volume.ncurry(1,3,4)
assert fixedYZ(5) == volume(5,3,4)

def fib 
fib = {long n -> n <2 ? n : fib(n-1)+fib(n-2)}
println fib(25)

fib = {long y -> y < 2 ? y : fib(y-1) + fib(y-2)}.memoize()
println fib(25)

//缓存优化设置
//memoizeAtMost 最多n值
//memoizeAtLeast 最少n值
//memoizeBetween 介于n 和 m 之间

def plus2 = {it +2}
def time3 = {it *3}
def time3plus2 = plus2 << time3 
assert time3plus2(3) == 11
assert time3plus2(4) == plus2(time3(4))

def plus2time3 = time3 << plus2 
assert plus2time3(4) == 18
assert plus2time3(5) == time3(plus2(5))

assert time3plus2(5) == (time3 >> plus2)(5)

def factorial
factorial = { int n, def accu = 1G ->
    if (n < 2) return accu
    //使用trampoline 进行弹性控制
    factorial.trampoline(n - 1, n * accu)
}
factorial = factorial.trampoline()

println factorial(1) //输出：1
println factorial(3)  // 输出：6
println factorial(10) // 输出 3628800

//创建一个接口对象
interface Predicate<T> {
    boolean accept(T obj)
}
//创建一个单一抽象方法类
abstract class Greeter {
    abstract String getName()
    void greet() {
        println "Hello, $name"
    }
}

Predicate filter = { it.contains 'G' } as Predicate
assert filter.accept('Groovy') == true

Greeter greeter = { 'Groovy' } as Greeter
greeter.greet()

interface FooBar{
  int foo()
  void bar()
}
//此时接口内所有方法都使用 闭包 实现
def impl = {println 'ok'; 123 }  as FooBar
assert impl.foo() == 123 
impl.bar()
