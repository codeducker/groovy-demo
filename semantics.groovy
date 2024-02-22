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

import groovy.xml.XmlSlurper
def xmlText = """
              | <root>
              |   <level>
              |      <sublevel id='1'>
              |        <keyVal>
              |          <key>mykey</key>
              |          <value>value 123</value>
              |        </keyVal>
              |      </sublevel>
              |      <sublevel id='2'>
              |        <keyVal>
              |          <key>anotherKey</key>
              |          <value>42</value>
              |        </keyVal>
              |        <keyVal>
              |          <key>mykey</key>
              |          <value>fizzbuzz</value>
              |        </keyVal>
              |      </sublevel>
              |   </level>
              | </root>
              """
def root = new XmlSlurper().parseText(xmlText.stripMargin())
assert root.level.size() == 1 
assert root.level.sublevel.size() == 2 
assert root.level.sublevel.findAll { it.@id == 1 }.size() == 1 
assert root.level.sublevel[1].keyVal[0].key.text() == 'anotherKey'

interface Predicate<T>{
  boolean accept(T t);
}

boolean doFilter(String s) { s.contains('G')}
Predicate predicate = this.&doFilter
assert predicate.accept('Groovy') == true


abstract class Greater{
  abstract String getName()

  void greet(){
    println "Hello $name"
  }
}

Greater greater = GroovySystem.&getVersion 
greater.greet()

public <T> List<T> filter(List<T> sources,Predicate<T> predicate){
  sources.findAll { predicate.accept(it) }
}
assert filter(['Java','Groovy'], {it.contains('G')} as Predicate) == ['Groovy']


class FooBar{
  int foo() {1}
  void bar() {println 'bar'}
}
def impl = {println 'ok'; 123} as FooBar 
assert impl.foo() == 123 
impl.bar()


def mapData 
mapData = [
  i:10,
  hasNext: { mapData.i > 10} ,
  next: { mapData.i --},
]
import java.util.function.Consumer
def itr = mapData as Iterator
itr.forEachRemaining({ println it} as Consumer)

interface X {
  void f()
  void g(int n )
  void h(String s ,int n )
}
xre = [f: {println "f called"} , methodMissing: {(String name,args) ->println "method missing"}] as X 
xre.f()
// xre.g(1)
// xre.g()
// xre.h("hi",5)

enum State {
  up ,
  down
}

def val = "up"
State st = "${val}"
assert st == State.up

// State s = "not a state enum"
State switchState(State st){
  switch(st){
    case "up" :
        return State.down
    case "down": 
        return "up"
  }
}
println switchState(State.down)

Class clazz = Class.forName('Greater')

// {println "hello"} as clazz

gtt = { println "hello"}.asType(clazz)
gtt.greet()

class Color {
  String name
  boolean asBoolean(){
    name == "green" ? true :false
  }
}
assert new Color(name:'green')

class Pers {
  String lastName
  String firstName
}
def px = new Pers (firstName: "err",lastName:"juh")
px.metaClass.getFormattedName = { -> "$delegate.firstName $delegate.lastName" }
println px.getFormattedName()
assert px.formattedName == "err juh"

def shell = new GroovyShell()
shell.evaluate '''
  class SentenceBuilder {
   
    StringBuilder sb = new StringBuilder()
    
    def methodMissing(String name, args) {
      if (sb) sb.append(' ')
      sb.append(name)
      this 
    }

    def propertyMissing(String name) {
       if (sb) sb.append(' ')
       sb.append(name)
       this
    }
    
    String toString() { sb }
  }

  import groovy.transform.*

  //这个注解可以放在类或者方法上面
  @TypeChecked
  class Calculator {
    int sum(int x , int y ) { x+ y }
  }

  @TypeChecked
  class GreetingService {
    String greeting(){
     doGreet()
    }
  
    @TypeChecked(TypeCheckingMode.SKIP)
    private String doGreet(){
      def b = new SentenceBuilder()
      b.Hello.my.name.is.John
      b
    }
  }

  def s = new GreetingService()
  assert s.greeting() == "Hello my name is John"
'''
@groovy.transform.TupleConstructor
class Sofa {
  String name
  String email
}

Sofa sofa = new Sofa()
sofa = new Sofa('fili','flili@163.com')
sofa = ['mark','mark@163.com']
sofa = [name:"july",email:'july@qq.com']
// sofa = [name:'jack',email:'jack@qq.com',age:13]//此时会报错显示 未知属性 age

//加上如下注解,则在编译时就检查是否存在printline方法
// @groovy.transform.TypeChecked 
class MyService {
  void something(){
    printline "My Service"
  }
}
def service = new MyService()
// service.something()

class Duck {
  void quark(){
    println "Quark!"
  }
}

class QuarkBird {
  void quark(){
    println "Quark!"
  }
}

// @groovy.transform.TypeChecked
void accept(quarker){
  quarker.quark()
}
accept(new Duck())

interface Gre { void g()}
interface Hre { void h()}

class Ag implements Gre,Hre {
  void g() { println "Ag G" }
  void h() { println "Ag H" }
}

class Bg implements Gre,Hre {
  void g() { println "Bg G" }
  void h() { println "Bg H" }
}

def list = [new Ag() , new Bg()]
list.each {
  it.g()
  it.h()
  // it.exit()
}

@groovy.transform.TypeChecked
void flowTyping(){
  def o = "foo"
  o = o.toUpperCase()
  o = 9d 
  o = Math.sqrt(o)

  List lista = ['a','b','c']
  lista = lista*.toUpperCase()//此时指定类型为List<String>
  // lista = 'foo' //此时不能将String赋值给List类型


//此时会报错因为lista定义为List<String>类型
  // lista.add(1)

  List<? extends Serializable> listx = []
  listx.addAll(['a','b','c'])
  listx.add(1)

}
//这里和Java的区别,此时会根据运行时的变量类型,从而调用对应类型的方法
int compute(String st) { st.length()}
String compute(Object st) { "Nope" }
Object o = "lihua"
def result1 =  compute(o)
println result1


class Top {
  void top(){}
}
class Buttom extends Top {
  void buttom(){}
}
// def oo = new Object()
// if(oo instanceof Top){
  // oo = new Top()
// }else{
  // oo == new Buttom()
// }
// oo.top()
// oo.buttom()

// @groovy.transform.TypeChecked
class More {
  int age
  def compute () {'Some compute'}
  def computeFully(){
    compute().toUpperCase()
  }
}
@groovy.transform.TypeChecked
class OneMore extends More{
  def compute() {123}
}

void inviteIf(More more,Closure<Boolean> predicate){
  if(predicate.call(more)){
    println "invite ..."
  }
}
@groovy.transform.TypeChecked
void failCompilation() {
  More more = new More()
  inviteIf(more) {
   More it->it.age >= 18 
  }
}

import groovy.transform.stc.FirstParam
void inviteIf2(More more, @groovy.transform.stc.ClosureParams(FirstParam)Closure<Boolean> predicate){
  if(predicate.call(more)){
    println "invite2..."
  }
}

@groovy.transform.TypeChecked
void failCompilation2(){
  More more = new More()
  inviteIf2(more) {
    it.age >= 18
  }
}

import groovy.transform.stc.FirstParam
import groovy.transform.stc.ClosureParams
public <T> void doSomeThing(List<T> strings,@ClosureParams(FirstParam.FirstGenericType) Closure c) {
  strings.each {
    c(it)
  }
}
doSomeThing(['foo','bar']) { println it.toUpperCase()}
doSomeThing([1,2,3]) { println it *2}

import groovy.transform.stc.SimpleType

public <T> void doSomeThing2(@ClosureParams(value=SimpleType,options=['java.lang.String','int'])
Closure c){
  c('foo',3)
}
doSomeThing2 {
  str,len -> assert str.length() == len 
}
import groovy.transform.stc.MapEntryOrKeyValue
public <K,V> void doSomeThing3(Map<K,V> map, @ClosureParams(MapEntryOrKeyValue)Closure c){
  c()
}

import groovy.transform.stc.FromAbstractTypeMethods
abstract class Too {
  abstract void firstSignauture(int x,int y)
  abstract void secondSignature(String str)
}
void doSomeThing4(@ClosureParams(value =FromAbstractTypeMethods,options=['Too'])Closure c){
  //
}
doSomeThing4 {aac,bac->aac+bac}
doSomeThing4(s->s.toUpperCase())
