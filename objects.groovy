package com.loern.groovy

class Foo {
  def call(){
   println "Foo initial..."
   this
  }
}

def foo = new com.loern.groovy.Foo()

foo()

// Java默认导入包 groovy.lang.* / groovy.util.*  / java.math.BigInteger / java.math.Bigdecimal 


//静态引入
import static java.lang.Boolean.FALSE 

assert !FALSE 

//引入别名
import static java.lang.System.out as out 
out.println("static alisa")

import static java.lang.String.format

import java.util.Date as SqlDate

class SomeClass {

   String format(Integer i) {
       i.toString()
   }
   def talk(){
    assert format('String') == 'String'
    assert this.format(Integer.valueOf(1)) == '1'
   }
}

new SomeClass().talk()
def sqlDate = new SqlDate(1000)
println sqlDate

class Foo2 {
  static int i 
}

assert Foo2.class.getDeclaredField('i').type == int.class
assert Foo2.i.class != int.class && Foo2.i.class == Integer.class

class Outer{

  private String privateStr


  def callInnerMethod(){
    new Inner().methodA()
  }

  def callAsyncInnerMethod(){
    new Thread(new Inner2()).start()
  }

  class Inner{
    def methodA(){
      println "${privateStr}"
    }
  }

  class Inner2 implements Runnable{
    void run(){
      println "${privateStr}"
    }
  }
}

new Outer(privateStr:"hello").callInnerMethod()
new Outer(privateStr: "neck").callAsyncInnerMethod()

class Computer{
  class Cpu{
    int coreNumber

    Cpu(int coreNumber){
      this.coreNumber = coreNumber
    }
  }
}

assert 4 == new Computer().new Cpu(4).coreNumber

interface Greeter{
  void greet(String name);

  default void say(){
    println "say"
  }
}

class SystemGreater implements Greeter {
  void greet(String name){
    println "Hello $name"
  }
}

def greeter = new SystemGreater()
greeter.greet("like ")
assert greeter instanceof Greeter

interface ExtendedGreeter extends Greeter {
  void sayGoods()
}

class DefaultGreeter {
  void greet(String name) { println "defaut $name"}
}
def defaultGreeter = new DefaultGreeter()
assert !(defaultGreeter instanceof Greeter)


class PersonConstructor{
  String name
  Integer age 
  PersonConstructor (name,age){
    this.name = name
    this.age = age 
  }
}

def person1 = new PersonConstructor('Marie',1)
def person2 = ['Marie',2] as PersonConstructor
PersonConstructor person3 = ['Marie',3]

class PersonWoConstructor{
  String name
  Integer age 

  def someMethod(){"method called"}
  String anotherMethod() {"another method called"}
  def thirdMethod(params) {"$params exxcuted"}
  static String fourthMethod(params) {"static $params called "}
}
def person4 = new PersonWoConstructor()
def person5 = new PersonWoConstructor(name:"Marie")
def person6 = new PersonWoConstructor(age:1)

def fooMap(Map args){
  "${args.age}, ${args.name}"
}
println fooMap(name:"Marie", age:1)
println fooMap(name:"puot")
def fooMixMap(Map map ,Integer num){
  "$map.age and $map.name is the number $num"
}

println fooMixMap(name: "more" , age:2,23)
println fooMixMap(12,name:"yuki",age:4)

def fooSome(Integer number,Map args){
  "$args.name not $args.age is $number"
}

// 如果存在map函数,那么此函数map参数必须要在第一位,否则名称参数方式调用会抛出异常

// fooSome(name:'just',age:13,23)
fooSome(23,[name:'foo',age:10])

def defaultValue(int id = 0, String name, String address) {
   println "${id} : ${name} : ${address}"
}

defaultValue( "hello", "lock")


def foo2(String par1, Integer par2 = 1) { [name: par1, age: par2] }
assert foo2('Marie').age == 1

def baz(a = 'a', int b, c = 'c', boolean d, e = 'e') { "$a $b $c $d $e" }

assert baz(42, true) == 'a 42 c true e'
assert baz('A', 42, true) == 'A 42 c true e'
assert baz('A', 42, 'C', true) == 'A 42 C true e'
assert baz('A', 42, 'C', true, 'E') == 'A 42 C true E'


def fooMethod(Object... args) { args.length }
def fooMethod(Object x) { 2 }
assert fooMethod() == 0
assert fooMethod(1) == 2
assert fooMethod(1, 2) == 2

def fooArgs(Object[] args) {args.length}
assert fooArgs() == 0
assert fooArgs(1) == 1 
assert fooArgs(2,3) == 2
// assert fooArgs(null) == null //这边会空指针异常

def method(Object o1, Object o2) { 'o/o' }
def method(Integer i, String  s) { 'i/s'}
def method(String  s, Integer i) { 's/i' }

assert method('foo' ,42) == 's/i'

List<List<Object>> pairs = [['foo', 1], [2, 'bar'], [3, 4]]
assert pairs.collect { a, b -> method(a, b) } == ['s/i', 'i/s', 'o/o']

interface A {}
class B {}
class C extends B implements A {}
def method(A a){ 
   println("interface")
}

def method(B b){
   println("Super Class")
}

method(new C())

def methodC(Date d, Object o) { 'd/o' }
def methodC(Object o, String s) { 'o/s' }


import static groovy.test.GroovyAssert.shouldFail
def ex = shouldFail {
  println methodC(new Date(), 'baz')
}
assert ex.message.contains('Ambiguous method overloading')


class Data{
  private int id 
  protected String description
  public static final boolean DEBUG = false 
  final String name
  Data(String name){
    this.name = name
  }
}

class Person {
   String name
   void name(String name) {
       this.name = "Wonder $name"
   }
   String title() {
       this.name
   }
}
def p = new Person()
p.name = 'Diana'
assert p.name == 'Diana'

p.name('Woman')
p.name = 'Wonder Woman'
assert p.title() == 'Wonder Woman'

for (final def ps in p.properties.keySet() ) {
   println(ps)
}

class PseudoProperties {
   // a pseudo property "name"
   void setName(String name) {}
   String getName() { "lucky"}
}

def pi = new PseudoProperties()
pi.name = 'Foo'
// println(pi.@name) //这里因为未定义name属性,所以会报异常
println(pi.name)//lucky 使用getName获取

import groovy.transform.*

class Animal {
   int lowerCount = 0
   @Lazy String name = { lower().toUpperCase() }()
   @PackageScope String address
   @Synchronized int getAge(){} 
   String lower() { lowerCount++; 'sloth' }
}

def a = new Animal()
assert a.lowerCount == 0
assert a.name == 'SLOTH'
assert a.lowerCount == 1

