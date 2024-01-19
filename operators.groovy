import java.util.regex.Matcher

assert 1 +2 == 3 
assert 4 -1 ==3 
assert 3 * 4 == 12 
assert 4 / 2 == 2 
assert 4 % 2 == 0 
assert 3 ** 3 == 27 

assert +3 ==  3 
assert -3 == 0-3
assert -(-3) == 3 

def a1 = 2 
def b1 = a1++ * 3 
assert a1 == 3 && b1 == 6 

def a2  = 3 
def b2 = ++a2 * 3 
assert a2 == 4 && b2 ==12

def ac  = 2 
def bc = 2 
println ac === bc

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class Creature {
  String type
}

def cat = new Creature(type:"cat")
def copycat = cat 
def lion = new Creature(type:"cat")
assert cat.equals(lion)
assert cat == lion

assert cat.is(copycat)
assert cat === copycat
assert cat !== lion

int a = 0b00101010
assert a == 42

int b = 0b00001000
assert b == 8 
assert (a & a ) == a 
assert (a & b) == b
assert (a | b) == a 
assert (a | b ) == a 

int mask = 0b11111111
assert ((a ^ a) & mask) == 0b00000000 
assert ((a ^ b) & mask) == 0b00100010
assert ((~a) & mask) == 0b11010101

assert 12.equals(3 << 2 )

def doSomeThing(String x) {x.toUpperCase()}
def doSomeThing(int x) {x + 45}
def referenceM = this.&doSomeThing
assert referenceM("hello") == "HELLO"
assert referenceM(2) == 47

def foo2 = BigInteger.&new
def forTyTwo  = foo2('42')
assert forTyTwo == 42

import java.util.regex.Pattern

def reg = ~/p/
assert reg instanceof Pattern

reg = ~'foo'
assert reg instanceof Pattern
reg = ~"foo"
assert reg instanceof Pattern
reg = ~$/dollar/slashy $ string/$
assert reg instanceof Pattern

pattern = /track/
reg = ~"${pattern}"
assert reg instanceof Pattern

def text = "some text to match"
def m = text ==~ /match/ //返回 Matcher 相当于  if(m.find(0))

if (!m) {
   // throw new RuntimeException("Should not reach that point!"
   println("Should not reach that point!")
}

class Car {
  String make
  String model
}

//*展开标志符
def cars = [
  new Car(make:"Peugeot",model:"508"),
  new Car(make:"Renault",model :"Clio")
]
//若此时列表元素中存在对应属性值为null,此时不会抛出异常,而是直接显示null值元素
def makes = cars*.make 
assert makes == ["Peugeot", "Renault"]
assert cars.collect{it.make} == ["Peugeot", "Renault"]

cars[2] = null

assert cars*.make == ["Peugeot", "Renault", null]

assert null*.make == null

class StuUser {
   String name
   int sex
   StuUser(String name){ this.name = name}
   String getName(){
       "name:$name"
   }
}

def user = new StuUser("hello")
println(user.sex ? user.sex : "0")
user.with {sex?=2} //sex为空则赋值2
println(user.sex ?: "0") //当sex为空时,则打印为 0 
assert user.name == 'name:hello'//调用getXxx方法
assert user.@name  == 'hello' //调用原始属性值

import groovy.transform.ToString

@ToString(includePackage = false)
class Element {
  String name
  int atomicNumber
}

def he = new Element(name:"Helium")
he.with {
  name = name ?: "Hydrogen"
  atomicNumber ?=2 
}
assert he.toString() == 'Element(Helium, 2)'

def strValue = "hello is world"
def funValue = strValue.&toUpperCase //函数指针操作符
println strValue + ":" + funValue()
assert funValue() == strValue.toUpperCase()

import static java.util.stream.Collectors.toList

assert 6G == [1G, 2G, 3G].stream().reduce(0G, BigInteger::add)

assert [4G, 5G, 6G] == [1G, 2G, 3G].stream().map(3G::add).collect(toList())

assert [1G, 2G, 3G] == [1L, 2L, 3L].stream().map(BigInteger::valueOf).collect(toList())

assert [1G, 2G, 3G] == [1L, 2L, 3L].stream().map(3G::valueOf).collect(toList())

class Person {
   int id
   String name
   int age
}

def persons = [new Person()]

def person = persons.find {it -> it.id == 123}
println person?.toString()
def name = person?.name 
assert name == null

def transform(List elements, Closure action) {
   def result = []
   elements.each {
       result << action(it)
   }
   result
}
String describe(Person p) {
   "$p.name is $p.age"
}
def action = this.&describe
def list = [
       new Person(name: 'Bob',   age: 42),
       new Person(name: 'Julia', age: 35)]
assert transform(list, action) == ['Bob is 42', 'Julia is 35']

assert 'two words' ==~ /\S+\s+\S+/
assert 'two words' ==~ /^\S+\s+\S+$/
assert !(' leading space' ==~ /\S+\s+\S+/)

def m3 = 'now three words' =~ /\S+\s+\S+/
println m3.getClass()
println(m3.groupCount())

class Component {
   Integer id
   String name
}
class CompositeObject implements Iterable<Component> {
   def components = [
           new Component(id: 1, name: 'Foo'),
           new Component(id: 2, name: 'Bar')]

   @Override
   Iterator<Component> iterator() {
       components.iterator()
   }
}
def composite = new CompositeObject()
assert composite*.id == [1,2]
assert composite*.name == ['Foo','Bar']

import static java.util.stream.Collectors.toList
pd = composite.stream().map (p->p.id).collect(toList())
println(pd)

import groovy.transform.Canonical
class Make {
  String name
  List<Model> models
}

@Canonical
class Model {
  String name
  List<String> values
}

def defineCars = [
  new Make(name:"Peugeot",models :[
    new Model(name:"408",values: ["D","O"]), new Model(name:"508",values:["L","M"])
  ]),
  new Make(name:"Renault",models :[
    new Model(name:"Clio",values:["P","K"]),new Model(name:"Captur",values:["G","B"])
  ])
]
def defineMakes = defineCars*.name
assert defineMakes == ["Peugeot", "Renault"]

def defineModels = defineCars*.models*.name 
assert defineModels == [["408", "508"], ["Clio", "Captur"]]

defineModels.add([["more"],["qiehao"]])

//合并第一层元素
println defineModels.sum()
//合并多层元素
println defineModels.flatten()

def netCars = [
       [
               new Car(make: 'Peugeot', model: '408'),
               new Car(make: 'Peugeot', model: '508')
       ], [
               new Car(make: 'Renault', model: 'Clio'),
               new Car(make: 'Renault', model: 'Captur')
       ]
       // ,null
]
def netModels = netCars*.model // 此时遇到null不会报错
// def netModels = netCars.collectNested{ it.model }//此时遇到空值 会报控指针异常

assert netModels == [['408', '508'], ['Clio', 'Captur']]

//展开参数
def func (int x ,int y,int z ){x+y+z}
def args = [1,34,23]
println(func(*args))

//展开元素
def m1 = [1,2,3]
def m2 = [*m1,4,5,6]
assert m2 == [1,2,3,4,5,6]

//展开map元素
def mp = [c:3,d:4]
def ma3 = [a:1,*:mp]
assert ma3 == [a:1, c:3,d:4]

//范围操作符
def range = 0..5
assert range.collect() == [0,1,2,3,4,5]
range = 0..<5 
assert range.collect() == [0,1,2,3,4]
range = 0<..<5
assert range.collect() == [1,2,3,4]

assert range instanceof List
assert range.size() == 4

assert ('a'..'d') == ['a','b','c','d']

//三路比较运算符
assert (1 <=> 1) == 0 
assert ('a' <=> 'z') == -1

//下标运算符
def arrList = [1,2,3,4,5,6]
assert arrList[0] == 1 
arrList[6] = 7
assert arrList[6] == 7 
assert arrList[0..2] == [1,2,3]

//当自定义对象未定义 getAt / putAt方法时 是无法通过 对象实例[下标]
class User {
   Long id
   String name
   def getAt(int i) {
       switch (i) {
           case 0: return id
           case 1: return name
       }
       throw new IllegalArgumentException("No such element $i")
   }
   void putAt(int i, def value) {
       switch (i) {
           case 0: id = value; return
           case 1: name = value; return
       }
       throw new IllegalArgumentException("No such element $i")
   }
}
def user1 = new User(id: 1, name: 'Alex')
assert user1[0] == 1 //此时 调用对象getAt方法
assert user1[1] == 'Alex'
user1[1] = 'Bob' //此时调用对象 putAt方法

//安全下标操作符
String[] array  = ['a','b']
assert 'b' == array?[1]
array = null
assert null == array?[0] //此时会发现arraay对象为空,所以对应取值或者赋值操作都是被忽略
array?[1] = 'c'

//同样适用于map
def personinfo = [name:"lucky","location":"shanghai"]
assert personinfo?['name'] == 'lucky'
personinfo = null
assert null == personinfo?['name']


def list1 = ['Groovy 1.8','Groovy 2.0','Groovy 2.3']
def list2 = ['Groovy 1.8','Groovy 2.0','Groovy 2.3']
//这里 in 相当于 list.contains() / list.isCase() 方法
assert ( "Groovy 1.8" in list1 )

assert list1 == list2 // 相当于equals方法
assert !list1.is(list2)// is 比较对象引用 
assert list1 !== list2

String input = '42'
 // def num = (Integer) input // 此时会抛出异常  
def num = input as Integer
assert num instanceof Integer

//若是对象 也需要进行 as 强转 才是要在类中定义 asType方法

class Identifiable {
    String name
}

import groovy.lang.Closure
import java.util.Map 
class MapUser {
   Long id
   String name

   def asType(Class target){
       if ( target == Identifiable ){
           return new Identifiable(name:name)
       }else{
           return null
       }
   }

   def call(){
       id += 1
   }

   def call(final Closure closure){
     closure this
   }

   def call(final Map data){
     id = data.get('id')
     name = data.get('name')
     this
   }
    
   def plus(int x){
     id + x
   }

   String toString(){
      [id:id,name:"${name}"]
   }
}

def u = new MapUser(id:1,name:"li")
def p = u  as Identifiable
assert p instanceof Identifiable


def user3 = new MapUser(id: 1)
assert user3.call() == 2 
assert user3() == 3


//这里传入闭包为 + 3 此时需要再类型 重载+ plus方法
assert user3{
  it+3
} == 6

//这里调用toString方法显示
assert user3([id:4,name:"hello"]).id == 4

//操作符 重载 
class Bnu {
    int size
    Bnu(int size){
        this.size = size
    }
    Bnu plus( Bnu other){
        return new Bnu(this.size + other.size)
    }
}
def bnu1 = new Bnu(1)
def bnu2 = new Bnu(2)
println(( bnu1 + bnu2 ).size)

/**
  常用操作符重载
  +   plus      a[b] / a[b]= c   getAt / putAt
  -   minus     a in b           isCase
  *   multiply  <<  /  >>        leftShift / rightShift
  /   div       >>>              rightShiftUnsigned            
  %   mod       ++   / --        next  /  previous
  **  power     +a / -a / ~a     positive  /  negative  / butwiseNegate 
  |   or        
  &   and 
  ^   xor
  as  asType 
  a() call  
*/
