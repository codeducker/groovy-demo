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

////        def time = t("")
////        add(1,1)
////        def t = from("2023-06-05 00:00:00")
////        add(1l,2l)
//        assert now().class == Calendar.getInstance().class
//        d(9,Random.newInstance())
////        add(1l,2l)
////        sin()
//        parseLong("1")
//        t(11,new Random())
//        SqlDate date = new SqlDate(1000)
   // }
// }

//class Foo {
//    static int i
//}
//
//assert Foo.class.getDeclaredField('i').type == int.class //原始类型
//assert Foo.i.class != int.class && Foo.i.class == Integer.class //包装类型
//
//
//class Outer {
//    private String privateStr
//
//    def callInnerMethod() {
//        new Inner().methodA()
//    }
//
//    class Inner {
//        def methodA() {
//            println "${privateStr}."
//        }
//    }
//}

//def inner = new Outer().new Inner()

//interface Fao {
//    void check()
//}
//
//interface Gao {
//
//}
//
//abstract class A implements Fao, Gao {
//
//}
//
//class LastGao {
//    void check() {
//        println("check")
//    }
//}

//def gao = new LastGao()
//def foo = gao as Fao
//assert gao instanceof  Fao

//def a = new BigInteger(1) as Long //运行时
//assert a instanceof Long
//
//class PersonConstructor {
//    String name
//    Integer age
//
//    PersonConstructor(name, age) {
//        this.name = name
//        this.age = age
//    }
//}
//
//def person1 = new PersonConstructor('Marie', 1)
//def person2 = ['Marie', 2] as PersonConstructor
//PersonConstructor person3 = ['Marie', 3]
//
//
//class See {
//
//}
//
//def see = new See()
//def see2 = [] as See
//See see3 = []
//
//class Joke {
//    String name
//    int id
//
//    Joke(int id, String name) {
//        this.id = id
//        this.name = name
//    }
//}
//
//String name = "andy"
//int id = 3
//
//def joke = new Joke(id, name)
//def joke1 = [id, name] as Joke
//Joke joke2 = [id, name]
//
//class User {
//    Long id
//    String name
//}
//
//def user = new User(id: 1, name: 'Alex')
//
//class Take {
//    Map lock
//    String name
//}
//
//def take = new Take(lock: ["id": 1])
//
//def someMethod() { 'method called' }
//
//def method = someMethod()
//println(method)
//
//def foo(Map args) { "${args.name}: ${args.age}" }
//
//foo(name: 'Marie', age: 1)
//
//def fooLook(int id, String name) {
//    "${id}----${name}"
//}
//
//fooLook(1, "hello")
//
//def foo(Map args, int id) { println "${id} : ${args.name}: ${args.age} " }
//
//foo(name: 'Marie', age: 1, 1)
//foo(name: 'Marie', 1, age: 1)
//foo(1, name: 'Marie', age: 12)
//
//def foo(String id, Map args) { println "second=> ${id} : ${args.name}: ${args.age} " }
//
//def map = [name: 'Marie', age: 11]
//foo("1", map)
//foo( name: 'Marie', age: 1, 1)

//def defaultValue(int id = 0, String name, String address) {
//    println "${id} : ${name} : ${address}"
//}
//
//defaultValue( "hello", "lock")
//
//
//def foo2(String par1, Integer par2 = 1) { [name: par1, age: par2] }
//
//assert foo2('Marie').age == 1
//
//def baz(a = 'a', int b, c = 'c', boolean d, e = 'e') { "$a $b $c $d $e" }
//
//assert baz(42, true) == 'a 42 c true e'
//assert baz('A', 42, true) == 'A 42 c true e'
//assert baz('A', 42, 'C', true) == 'A 42 C true e'
//assert baz('A', 42, 'C', true, 'E') == 'A 42 C true E'
//
//
//def bas(ax = 'a', int b, c = 'c', boolean d, e = 'e', boolean f = false) {
//    println "$ax $b $c $d $e $f"
//}
//
//bas(42, 1,  false, true,1,true)

//assert  == 'a 42 c true e'
//assert bas('A', 42, true) == 'A 42 c true e'
//assert bas('A', 42, 'C', true) == 'A 42 C true e'
//assert bas('A', 42, 'C', true, 'E') == 'A 42 C true E'

//def foo(Object... args) { 1 }
//def foo(Object x) { 2 }
//assert foo() == 1
//assert foo(1) == 2
//assert foo(1, 2) == 1

//def method(Object o1, Object o2) { 'o/o' }
//def method(Integer i, String  s) { 'i/s' }
//def method(String  s, Integer i) { 's/i' }
//
//List<List<Object>> pairs = [['foo', 1], [2, 'bar'], [3, 4]]
//assert pairs.collect { a, b -> method(a, b) } == ['s/i', 'i/s', 'o/o']
//
//pairs = [['foo', 1], [2, 'bar'], [3, "true"]]
//pairs.collect { a, b -> println "${a} ${b}"}
//assert pairs.collect { a, b -> method(a, b) } == ['s/i', 'i/s', 'i/s']

//interface A {
//
//}
//class B {
//
//}
//class C extends B implements A {
//
//}
//def method(A a){
//    println("interface")
//}
//
//def method(B b){
//    println("Super Class")
//}
//
//method(new C())
//
//def method(Date d, Object o) { 'd/o' }
//def method(Object o, String s) { 'o/s' }
//
//println method(new Date(), 'baz')

//assert method(new Date(), (Object)'baz') == 'd/o'
//assert method((Object)new Date(), 'baz') == 'o/s'

//class Person {
//    String name
//    void name(String name) {
//        this.name = "Wonder $name"
//    }
//    String title() {
//        this.name
//    }
//}
//def p = new Person()
//p.name = 'Diana'
//assert p.name == 'Diana'
//
//
//
////p.name('Woman')
//p.name = 'Wonder Woman'
//assert p.title() == 'Wonder Woman'
//
//for (final def ps in p.properties.keySet() ) {
//    println(ps)
//}

//class PseudoProperties {
//     String name
//
//    String joo
//    String getJoo() {
//        return joo
//    }
//
//    void setJoo(String joo) {
//        this.joo = joo
//    }
//
//    String getFoo(){ "foo"}
//
//    void setFoo(String foo){this.foo = foo}
//
//    // a pseudo property "name"
//    void setName(String name) {this.name =name}
//    String getName() { "lucky"}
//
//    // a pseudo read-only property "age"
//    int getAge() { 42 }
//
//    // a pseudo write-only property "groovy"
//    void setGroovy(boolean groovy) {  }
//}
//def p = new PseudoProperties()
//p.name = 'Foo'
//println(p.@name)
//println(p.name)//lucky 使用getName获取
//assert p.age == 42
//p.groovy = true
//println(p.foo)
//println(p.Foo)


//class Animal {
//    int lowerCount = 0
//    @Lazy String name = { lower().toUpperCase() }()
//    String lower() { lowerCount++; 'sloth' }
//}
//
//def a = new Animal()
//assert a.lowerCount == 0
//assert a.name == 'SLOTH'
//assert a.lowerCount == 1

