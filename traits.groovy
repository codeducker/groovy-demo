trait FlyingAbility {
   String fly() { "I'm flying!" }
}

interface FlyingAbilityInter {
   default String fly() { "I'm flying!" }

}

class Bird implements FlyingAbilityInter {

}

class AirPlane implements FlyingAbility{

}

def b = new Bird()
def p = new AirPlane()
assert b.fly() === p.fly()

//只允许支持 public / private 修饰 不支持 protected 或者 package private 
trait Greetable{
  abstract String name()
  String greeting() { "Hello ${name()}!"}
  private String helloName(){
    "hello private method"
  }
}

class Person implements Greetable{
    String name() {"Person"}
}

def person = new Person()
assert person.greeting() == "Hello Person!"

try{
    person.helloName()
}catch(Exception e){
  println e.getMessage()
}

interface Named {  
   String name()
}
trait GreetNamedAble implements Named {
   String username
   String greeting() { "Hello, ${name()}!" }
}
class NamedPerson implements GreetNamedAble {
   String name() { 'NamedPerson' }
}

def per = new NamedPerson()
assert per.greeting() == 'Hello, NamedPerson!'
assert per instanceof Named
assert per instanceof GreetNamedAble


class ProPerson implements GreetNamedAble {
  String name() { 'ProPerson' }
}

def proPerson = new ProPerson(username:"lucky")
assert proPerson.username == 'lucky'
assert proPerson.getUsername() == 'lucky'


trait DynamicObject {
   private Map props = [:]
   def methodMissing(String name, args) {
       name.toUpperCase()
   }
   def propertyMissing(String name) {
       props.get(name)
   }
   void setProperty(String name, Object value) {
       props.put(name, value)
   }
}

class Dynamic implements DynamicObject {
   String existingProperty = 'ok'
   String existingMethod() { 'ok' }
}

def dc = new Dynamic()
assert dc.existingProperty == 'ok'
assert dc.foo == null
dc.foo = 'bar'
assert dc.foo == 'bar'
assert dc.existingMethod() == 'ok'
assert dc.someMethod() == 'SOMEMETHOD'

//若多继承时 存在相同的方法,则最后继承 的那个方法起作用
trait Mea{
  String hello() {"A"}
  String gg() {"A"}
}
trait Bea {
  String hello() {"B"}
  String gg() {"B"}
}
class Cea implements Mea,Bea {
  //此时 由 我们自己 处理 具体调用 哪个父类处理
  String gg(){
    Mea.super.gg()
  }
}

def cea = new Cea()
assert cea.hello() == "B"
assert cea.gg() == "A"


interface Ad {
  default String like() {"Like"}
}
class Bd { 
  String tell(){ "B"}
}
def bd = new Bd() as Ad
assert bd.like() == 'Like'

//类与类之前强转
class Aog { 
  String say(){"Aog"}
}

class Dog {
  String say() {"Dog"}

  def asType(Class clz){
    return new Aog()
  }
}

def dog = new Dog() as Aog
println dog.say()


trait Just {
  void methodFromJust(){}
}

trait Aust {
  void methodFromAust(){}
}

class Cust {}

def cust = new Cust()
def custTraits = cust.withTraits Just, Aust 
custTraits.methodFromJust()
custTraits.methodFromAust()

interface MessageHandler {
   void on(String message, Map payload)
}

trait DefaultHandler implements MessageHandler {
   void on(String message, Map payload) {
       println "Received $message with payload $payload"
   }
}

class SimpleHandler implements DefaultHandler {}

class SimpleHandlerWithLogging implements DefaultHandler {
   void on(String message, Map payload) {
       println "Seeing $message with payload $payload"
       DefaultHandler.super.on(message, payload)
   }
}

trait LoggingHandler implements MessageHandler {
   void on(String message, Map payload) {
       println "Seeing $message with payload $payload"
       super.on(message, payload)
   }
}

class HandlerWithLogger implements DefaultHandler, LoggingHandler {}
def loggingHandler = new HandlerWithLogger()
loggingHandler.on('test logging', [:])

trait SayHandler implements MessageHandler {
   void on(String message, Map payload) {
       if (message.startsWith("say")) {
           println "I say ${message - 'say'}!"
       } else {
           super.on(message, payload)
       }
   }
}

class Handler implements DefaultHandler, SayHandler, LoggingHandler {}
def h = new Handler()
h.on('foo', [:])
h.on('sayHello', [:])


class AlternateHandler implements DefaultHandler, LoggingHandler, SayHandler {}
h = new AlternateHandler()
h.on('foo', [:])
h.on('sayHello', [:])

trait Filtering {
   StringBuilder append(String str) {
       def subst = str.replace('o','')
       println(subst)
       super.append(subst)
   }
   String toString() { super.toString() }
}

def sb = new StringBuilder().withTraits Filtering
sb.append('Groovy')
assert sb.toString() == 'Grvy'

trait Greeter {
   String greet() { "Hello $name" }
   abstract String getName()
}
//SAM single abstract method 可以使用如下方式实现该trait 
Greeter greeter = { 'Alice' } 
println greeter.greet()

void sat(Greeter g){println g.greet()}
sat {'march'}

trait Counter {
   private int count = 0
   int count() { count += 1; count }
}
class FooCounter implements Counter {}
def f = new FooCounter()
assert f.count() == 1
assert f.count() == 2

trait PublicNamed{
  public String name
  String email
}

class PublicPerson implements PublicNamed {

}

//避免钻石问题 
def publicPerson = new PublicPerson()
publicPerson.setEmail( 'lucky' )
println publicPerson.getEmail()
//此时访问方式为 完整路径名(package[_]className__fieldName)
assert null == publicPerson.PublicNamed__name

trait FlyingA {
  String fly(){ "I'am flying"}
}

trait SpeakingA{
  String speak() { "I'am speaking"}
}

class Duck implements FlyingA, SpeakingA {

}
def duck = new Duck()
assert duck.fly() == "I'am flying"
assert duck.speak() == "I'am speaking"

class ProDuck implements FlyingA,SpeakingA{
  String quack() {"Quack!"}
  String speak() {quack()}
}

def pd = new ProDuck()
assert pd.speak() == "Quack!"

trait Na {
  String name
}

trait Ena extends Na {
  String introduce() {"Hello ,my $name"}
}

class Cnea implements Ena{

}

def pp = new Cnea(name: "boy")
assert pp.introduce() == "Hello ,my boy"

//如果trait 存在多个继承  则此时需要定义为 implements 
trait Aa{}
trait Bb {}
trait Cc implements Aa,Bb{}

//支持在引入在实现中动态方法
trait SpeakDuck{
  String speak() {quack()}
}

class Ducker implements SpeakDuck{
  //因为trait调用 quack 方法,但是未定义,此时可以通过实现 定义 methodMissing来达到 动态代码
  String methodMissing(String name,args){
    "${name.capitalize()}!"
  }
}
def d = new Ducker()
assert d.speak() == 'Quack!'

class A {
    String methodFromA() { 'A' }
}
class B {
    String methodFromB() { 'B' }
}
A.metaClass.mixin B
def o = new A()
assert o.methodFromA() == 'A'
assert o.methodFromB() == 'B'   
assert o instanceof A
assert !(o instanceof B)
System.out.println("Hello world")
println("Locked")

import groovy.test.GroovyTestCase
import groovy.transform.CompileStatic
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.customizers.*

class SomeTest extends GroovyTestCase {

  def config 
  def shell 

  void setup(){
    config = new CompilerConfiguration()
    shell = new GroovyShell(config)
  }

  void testSomething(){
    assert shell.evaluate('1+1') == 2
  }

  void otherTest() { /**... */}
}


class AnotherTest extends SomeTest implements MyTestSupport{
  void setup(){
    config = new CompilerConfiguration()
    // config.addCompilationCustomizers(...)
    shell = new GroovyShell(config)
  }
}

class YetAnotherTest extends SomeTest implements MyTestSupport{
  void setup(){
    config = new CompilerConfiguration()
    // config.addCompilationCustomizers(...)
    shell = new GroovyShell(config)
  }
}

trait MyTestSupport{
  void setup(){
    config = new CompilerConfiguration()
    config.addCompilationCustomizers(new ASTTransformationCustomizer(CompileStatic))
    shell =new GroovyShell(config)
  }
}

trait TestHelper {
  public static boolean CALLED = false
  public static boolean FINE = false
  static void init(){
    CALLED = true
  }
  static void change(){
    FINE = true
  }
}
class Future implements TestHelper {

}

Future.init()
assert Future.TestHelper__CALLED


class Bar implements TestHelper{}
class Baz implements TestHelper{}
Bar.change()
assert Bar.TestHelper__FINE 
assert !Baz.TestHelper__FINE

trait IntCouple {
  int x = 1
  int y = 2
  int sum() { x + y }
}
class BaseElem implements IntCouple {
  int f() { sum() }
}

def bx = new BaseElem()
assert bx.f() == 3

class Elem implements IntCouple {
  int x = 3 
  int y = 4 
  int f() { sum() } //此时调用仍为 trait 里面的x+y 
}

def be = new Elem()
assert be.f() ==3 



trait IntGetCouple {
  int x = 1
  int y = 2
  int sum() { getX() + getY()} //此时 该方法由子类 复写
}

class BKIntGetCouple implements IntGetCouple{

  int x = 3

  int y = 4 

  int f() { sum() }
}

def big = new BKIntGetCouple()
assert big.f() == 7 

class CommunicationService {
  static void sendMessage(String from, String to,String message){
    println "the message from $from to $to and content is $message"
  }
}

class Device {
  String id 
}

trait Communicating {
  void sendMessage(Device to ,String message){
    CommunicationService.sendMessage(id,to.id,message)
  }
}

class MyDevice extends Device implements Communicating {

}

def bob = new MyDevice(id:"Bob")
def alice = new MyDevice(id:"Alice")
bob.sendMessage(alice,"secret")

class SecurityService {
  static void check(Device d) {
    if (d.id == null) throw new Exception("id is null")
  }
}

import groovy.transform.SelfType 

@SelfType(Device)
@CompileStatic
trait CommunicatingA {
  void sendMessage(Device to ,String message){
    SecurityService.check(to)
    CommunicationService.sendMessage(id,to.id,message)
  }
}
//此时 下述代码会报错 因为么有继承 SelfType规定的 Device 
// class SelfDevice implements CommunicatingA {}
import groovy.transform.Sealed

interface HasHeight{ double getHeight()}
interface HasArea { double getArea()}

@SelfType([HasHeight,HasArea])
@Sealed(permittedSubclasses=[UnitCylinder,UnitCube])
trait HasVolumn{
  double getVolumn() { height * area }
}
final class UnitCube implements HasVolumn,HasHeight,HasArea {
  double height = 1
  double area = 1
}
final class UnitCylinder implements HasVolumn,HasHeight,HasArea{
  double height =  1 
  double area = Math.PI * 0.5**2
}

assert new UnitCube().volumn == 1d 
assert new UnitCylinder().volumn == 0.7853981633974483d

//单实现类时 可以使用 @SelfType / @Sealed 
final class FooBoo implements FooBooTrait {

}

// @SelfType(FooBoo)
// trait FooBooTrait {}

@Sealed(permittedSubclasses=FooBoo)
trait FooBooTrait {}

//trait 不支持成员属性 自增 / 自减
