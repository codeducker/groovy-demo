//trait FlyingAbility {
//    String fly() { "I'm flying!" }
//}
//
//interface FlyingAbilityInter {
//    default String fly() { "I'm flying!" }
//
//}
//
//class Bird implements FlyingAbilityInter {
//
//}
//
//class AirPlane implements FlyingAbility{
//
//}
//
//def b = new Bird()
//def p = new AirPlane()
//assert b.fly() === p.fly()
//
//trait Greeter {
//    private String greetingMessage() {
//        'Hello from a private method!'
//    }
//    String greet() {
//        def m = greetingMessage()
//        println m
//        m
//    }
//}
//class GreetingMachine implements Greeter {}
//def g = new GreetingMachine()
//assert g.greet() == "Hello from a private method!"
//try {
//    assert g.greetingMessage()
//} catch (MissingMethodException e) {
//    println "greetingMessage is private in trait"
//}

//trait IntroSpector {
//    def whoAmI() { this }
//}
//class Foo implements IntroSpector {}
//def foo = new Foo()
//def instance = foo.whoAmI()
//println( instance)
//

//interface Named {
//    String name()
//}
//trait GreetAble implements Named {
//    String greeting() { "Hello, ${name()}!" }
//}
//class Person implements GreetAble {
//    String name() { 'Bob' }
//}
//
//def per = new Person()
//assert per.greeting() == 'Hello, Bob!'
//assert per instanceof Named
//assert per instanceof GreetAble

//
//trait Named {
//    String name
//}
//class Person implements Named {}
//def per = new Person(name: 'Bob')
//assert per.name == 'Bob'
//assert per.getName() == 'Bob'
//trait Named {
//    public String name
//    def say(){ "Named"}
//}
//
//trait  A {
//    public String name
//
//    def say(){ "A"}
//}
//
//
//class Person implements A,Named{}
//def p = new Person()
//p.Named__name = 'Bob'
//p.A__name = 'Bob'
//println p.say()

//trait SpeakingDuck {
//    String speak() { quack() }
//}
//class Duck implements SpeakingDuck {
//    String methodMissing(String name, args) {
//        "${name.capitalize()}!"
//    }
//}
//def d = new Duck()
//assert d.speak() == 'Quack!'

//trait DynamicObject {
//    private Map props = [:]
//    def methodMissing(String name, args) {
//        name.toUpperCase()
//    }
//    def propertyMissing(String name) {
//        props.get(name)
//    }
//    void setProperty(String name, Object value) {
//        props.put(name, value)
//    }
//}
//
//class Dynamic implements DynamicObject {
//    String existingProperty = 'ok'
//    String existingMethod() { 'ok' }
//}
//def d = new Dynamic()
//assert d.existingProperty == 'ok'
//assert d.foo == null
//d.foo = 'bar'
//assert d.foo == 'bar'
//assert d.existingMethod() == 'ok'
//assert d.someMethod() == 'SOMEMETHOD'

//class A  {
//    def propertyMissing(String name) {
//        name.toUpperCase()
//    }
//}
//
//def a = new A()
//println a.lock

//class A {}
//def a = new A()
//trait B {}
//def d = a as B
//trait C {}
//def e = a.withTraits B,C

//interface MessageHandler {
//    void on(String message, Map payload)
//}
//
//trait DefaultHandler implements MessageHandler {
//    void on(String message, Map payload) {
//        println "Received $message with payload $payload"
//    }
//}

//class SimpleHandler implements DefaultHandler {}
//
//class SimpleHandlerWithLogging implements DefaultHandler {
//    void on(String message, Map payload) {
//        println "Seeing $message with payload $payload"
//        DefaultHandler.super.on(message, payload)
//    }
//}
//
//trait LoggingHandler implements MessageHandler {
//    void on(String message, Map payload) {
//        println "Seeing $message with payload $payload"
//        super.on(message, payload)
//    }
//}
//class HandlerWithLogger implements DefaultHandler, LoggingHandler {}
//def loggingHandler = new HandlerWithLogger()
//loggingHandler.on('test logging', [:])
//
//trait SayHandler implements MessageHandler {
//    void on(String message, Map payload) {
//        if (message.startsWith("say")) {
//            println "I say ${message - 'say'}!"
//        } else {
//            super.on(message, payload)
//        }
//    }
//}

//class Handler implements DefaultHandler, SayHandler, LoggingHandler {}
//def h = new Handler()
//h.on('foo', [:])
//h.on('sayHello', [:])

//
//class AlternateHandler implements DefaultHandler, LoggingHandler, SayHandler {}
//h = new AlternateHandler()
//h.on('foo', [:])
//h.on('sayHello', [:])

//trait Filtering {
//    StringBuilder append(String str) {
//        def subst = str.replace('o','')
//        println(subst)
//        super.append(subst)
//    }
//    String toString() { super.toString() }
//}
//def sb = new StringBuilder().withTraits Filtering
//sb.append('Groovy')
//assert sb.toString() == 'Grvy'

//trait Greeter {
//    String greet() { "Hello $name" }
//    abstract String getName()
//}
//
//Greeter greeter = { 'Alice' }
//println greeter.greet()
//
//
//class Sec implements Greeter {
//    @Override
//    String getName() {
//        return "lucky"
//    }
//}
//
//def sec = new Sec()
//println sec.greet()

//trait Counter {
//    private int count = 0
//    int count() { count += 1; count }
//}
//class Foo implements Counter {}
//def f = new Foo()
//assert f.count() == 1
//assert f.count() == 2

//
//class Person {
//    String name
//}
//trait Bob {
//    String getName() { 'Bob' }
//}
//
//def p = new Person(name: 'Alice')
//assert p.name == 'Alice'
//def p2 = p as Bob
//assert p2.name == 'Bob'

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