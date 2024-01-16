println "hello" //单行注释


/**
  多行注释
*/
println "mutiple comments" 

/**
  文档注释
  可以使用JavaDoc相关 
 @param name 名称
  @return 格式化欢迎
*/
String greet(String name){
  return "你好 ${name}"
}


/**@
  *另外 3.xx版本支持 运行时groovy文档,默认是关闭的 需要通过 -Dgroovy.attach.runtime.groovydoc=true
  *Some class groovydoc 运行时groovydoc
*/
class Foo {

  /**@
  * some method groovy for bar
  */
  void bar(){

  }
}
assert Foo.class.groovydoc.content.contains('Some')
assert Foo.class.getMethod('bar',new Class[0]).groovydoc.content.contains('method')
