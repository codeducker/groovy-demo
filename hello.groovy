println("hello")

//此时会生成Hello主类 代码内容如下


// import groovy.lang.Binding;
// import groovy.lang.Script;
// import org.codehaus.groovy.runtime.InvokerHelper;
//
// public class hello extends Script {
//     public hello() {
//     }
//
//     public hello(Binding context) {
//         super(context);
//     }
//
//     public static void main(String... args) {
//         InvokerHelper.class.invoke<invokedynamic>(InvokerHelper.class, hello.class, args);
//     }
//
//     public Object run() {
//         return this.invoke<invokedynamic>(this, "hello");
//     }
// }
//
