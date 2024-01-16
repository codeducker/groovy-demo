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

//@Retention(RetentionPolicy.RUNTIME)
//@interface OnlyIf {
//    Class value()
//}
//
//class Tasks {
//    Set result = []
//    void alwaysExecuted() {
//        result << 1
//    }
//    @OnlyIf({ jdk>=6 })
//    void supportedOnlyInJDK6() {
//        result << 'JDK 6'
//    }
//    @OnlyIf({ jdk>=7 && windows })
//    void requiresJDK7AndWindows() {
//        result << 'JDK 7 Windows'
//    }
//}

//class Runner {
//    static <T> T run(Class<T> taskClass) {
//        def tasks = taskClass.newInstance()
//        def params = [jdk: 6, windows: false]
//        tasks.class.declaredMethods.each { m ->
//            if (Modifier.isPublic(m.modifiers) && m.parameterTypes.length == 0) {
//                def onlyIf = m.getAnnotation(OnlyIf)
//                if (onlyIf) {
//                    Closure cl = onlyIf.value().newInstance(tasks,tasks)
//                    cl.delegate = params
//                    if (cl()) {
//                        m.invoke(tasks)
//                    }
//                } else {
//                    m.invoke(tasks)
//                }
//            }
//        }
//        tasks
//    }
//}

//def tasks = Runner.run(Tasks)
//assert tasks.result == [1, 'JDK 6'] as Set
//
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

@Retention(RetentionPolicy.RUNTIME)
public @interface Foo {
    String value()
}
@Retention(RetentionPolicy.RUNTIME)
public @interface Bar {
    String value()
}

@Foo
@Bar
@AnnotationCollector
public @interface FooBar {}

@Foo('a')
@Bar('b')
class Bob {}

assert Bob.getAnnotation(Foo).value() == 'a'
println Bob.getAnnotation(Bar).value() == 'b'

//@FooBar('a')
class Joe {}
assert Joe.getAnnotation(Foo).value() == 'a'
println Joe.getAnnotation(Bar).value() == 'a'


@CompileStatic
class CompileDynamicProcessor extends AnnotationCollectorTransform {
    private static final ClassNode CS_NODE = ClassHelper.make(CompileStatic)
    private static final ClassNode TC_NODE = ClassHelper.make(TypeCheckingMode)

    List<AnnotationNode> visit(AnnotationNode collector,
                               AnnotationNode aliasAnnotationUsage,
                               AnnotatedNode aliasAnnotated,
                               SourceUnit source) {
        def node = new AnnotationNode(CS_NODE)
        def enumRef = new PropertyExpression(new ClassExpression(TC_NODE), "SKIP")
        node.addMember("value", enumRef)
        Collections.singletonList(node)
    }
}