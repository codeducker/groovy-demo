import groovy.transform.CompileStatic
import groovy.transform.TypeCheckingMode
import org.codehaus.groovy.ast.*

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.Target
import java.lang.annotation.RetentionPolicy
import java.lang.reflect.Modifier
import groovy.transform.AnnotationCollector
import org.codehaus.groovy.transform.AnnotationCollectorTransform
import org.codehaus.groovy.control.*
import org.codehaus.groovy.ast.expr.*


//groovy 不支持ElementType.TYPE_PARAMETER 
@Retention(RetentionPolicy.RUNTIME)
@interface OnlyIf {
   Class value() default Void.class
}

class Tasks {
   Set result = []
   void alwaysExecuted() {
       result << 1
   }
   @OnlyIf({ jdk>=6 })
   void supportedOnlyInJDK6() {
       result << 'JDK 6'
   }
   @OnlyIf({ jdk>=7 && windows })
   void requiresJDK7AndWindows() {
       result << 'JDK 7 Windows'
   }
}

class Runner {
   static <T> T run(Class<T> taskClass) {
       def tasks = taskClass.newInstance()
       def params = [jdk: 6, windows: false]
       tasks.class.declaredMethods.each { m ->
           if (Modifier.isPublic(m.modifiers) && m.parameterTypes.length == 0) {
               def onlyIf = m.getAnnotation(OnlyIf)
               if (onlyIf) {
                   println onlyIf.value()
                   Closure cl = onlyIf.value().newInstance(tasks,tasks)
                   cl.delegate = params
                   if (cl()) {
                       m.invoke(tasks)
                   }
               } else {
                   m.invoke(tasks)
               }
           }
       }
       tasks
   }
}
def tasks = Runner.run(Tasks)
assert tasks.result == [1, 'JDK 6'] as Set

def cl= {jdk > 7 && windows}.class
cl.declaredConstructors.each(){
  m->println m
}

def params = [jdk:6,windows:false]

def ts = new Tasks()
def cl2 = cl.newInstance(this,this)
def cl1 = cl.newInstance(ts,ts)
cl1.delegate = params 
cl2.delegate = params
assert cl1.call() == cl1()
assert cl2.call() == cl2()


import java.lang.annotation.* 


@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Aoo {

}

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface Foo{

}

import groovy.transform.*

@Documented
//第一种方式在 AnnotataionContector中申明
// @Aoo
// @Foo
// @AnnotationCollector([Aoo,Foo])

//第二种方式直接引入注解
@Aoo
@Foo
@AnnotationCollector
@interface MixAooFoo{}


@MixAooFoo
class TestMix{}

println TestMix.class.annotations.size()
def annotations = TestMix.class.annotations*.annotationType()
println annotations
assert Aoo in annotations
assert Foo in annotations




@AnnotationCollector([ToString,EqualsAndHashCode])
@interface Simple {}


@Simple
class User{
  String username
  int age
}
def user = new User(username:"lucky",age:28)
assert user.toString() == 'User(lucky, 28)'
assert User.class.annotations.size() == 2 

@Simple(excludes ='street')
class Address{
  String street,town
}
def address = new Address(street:'yunxinlu',town:'field')
assert address.toString() == 'Address(field)'

//使用自定义处理器来处理注解属性
import groovy.transform.CompileStatic

import groovy.transform.TypeCheckingMode
import org.codehaus.groovy.ast.AnnotatedNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.AnnotationCollectorTransform

@CompileStatic(TypeCheckingMode.SKIP)
class SimpleProcessor extends AnnotationCollectorTransform{
    public List<AnnotationNode> visit(AnnotationNode collector, AnnotationNode aliasAnnotationUsage,
                                      AnnotatedNode aliasAnnotated, SourceUnit source) {
        def members = aliasAnnotationUsage.getMembers()
        def dontUse = members.get("dontUse")
        members.remove("dontUse")
        if(dontUse){
            aliasAnnotationUsage.addMember("excludes",dontUse)
        }
        super.visit(collector,aliasAnnotationUsage,aliasAnnotated,source)
    }
}

new GroovyShell(this.class.classLoader).evaluate '''

import java.lang.annotation.Documented
import java.lang.annotation.ElementType
import java.lang.annotation.Target
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.AnnotationCollector

@AnnotationCollector(value=[EqualsAndHashCode,ToString],processor = 'SimpleProcessor')
@interface SimpleProcessorInter { 
}

@SimpleProcessorInter(dontUse='username')
class ProUser{
  String username
  int age 
}

def user = new ProUser(username: "lucky",age:18)
assert user.toString() == 'ProUser(18)'
'''

@Retention(RetentionPolicy.RUNTIME)
public @interface Koo {
  String value() default ''
}

@Retention(RetentionPolicy.RUNTIME)
public @interface Poo {
  String value() default ''
}

@Poo
@Koo
@AnnotationCollector 
public @interface Jko {

}

@Koo("a")
@Poo("b")
class Blob {

}

assert Blob.getAnnotation(Poo).value() == 'b'
assert Blob.getAnnotation(Koo).value() == 'a'

@Jko('a')
class Job {

}

assert Job.getAnnotation(Poo).value() == 'a'
assert Job.getAnnotation(Koo).value() == 'a'


















// def tasks1 = Tasks.class.newInstance()
// def cl = {jdk > 7 && windows}.newInstance(tasks1,tasks1)

//@Retention(RetentionPolicy.RUNTIME)
//@Target(value=[ElementType.METHOD])
//public @interface MyGroovyAnnotation {
//    String value()
//}
//
//class MyGroovyClass {
//
//    public static final String VALUE = "Something"
//
//    @MyGroovyAnnotation(value= VALUE)
//    public String myMethod(String value) {
//        return value
//    }
//}
//@interface SomeAnnotation {
//    String value()
//}
//@interface SomeAnnotation {
//    String value() default 'something'
//}
//@interface SomeAnnotation {
//    int step()
//}
//@interface SomeAnnotation {
//    Class appliesTo()
//}
//@interface SomeAnnotation {}
//@interface SomeAnnotations {
//    SomeAnnotation[] value()
//}
//enum DayOfWeek { mon, tue, wed, thu, fri, sat, sun }
//@interface Scheduled {
//    DayOfWeek dayOfWeek()
//}

//@interface Service {
//
//}
//
//@interface Transactional {}
//

//
//@Service
//@Transactional
//@AnnotationCollector
//@interface TransactionalService {
//}
//
//@TransactionalService
//class MyTransactionalService {
//
//}

//def annotations = MyTransactionalService.annotations*.annotationType()
//assert (Service in annotations)
//assert (Transactional in annotations)

// @Retention(RetentionPolicy.RUNTIME)
// public @interface Foo {
    // String value()
// }
// @Retention(RetentionPolicy.RUNTIME)
// public @interface Bar {
    // String value()
// }

// @Foo
// @Bar
// @AnnotationCollector
// public @interface FooBar {}
//
// @Foo('a')
// @Bar('b')
// class Bob {}

// assert Bob.getAnnotation(Foo).value() == 'a'
// println Bob.getAnnotation(Bar).value() == 'b'

//@FooBar('a')
// class Joe {}
// assert Joe.getAnnotation(Foo).value() == 'a'
// println Joe.getAnnotation(Bar).value() == 'a'


// @CompileStatic
// class CompileDynamicProcessor extends AnnotationCollectorTransform {
    // private static final ClassNode CS_NODE = ClassHelper.make(CompileStatic)
    // private static final ClassNode TC_NODE = ClassHelper.make(TypeCheckingMode)

    // List<AnnotationNode> visit(AnnotationNode collector,
                               // AnnotationNode aliasAnnotationUsage,
                               // AnnotatedNode aliasAnnotated,
                               // SourceUnit source) {
        // def node = new AnnotationNode(CS_NODE)
        // def enumRef = new PropertyExpression(new ClassExpression(TC_NODE), "SKIP")
        // node.addMember("value", enumRef)
        // Collections.singletonList(node)
    // }
// }
