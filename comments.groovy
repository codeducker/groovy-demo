//单行注释
/*
 * 多行注释
 */
print("hello") /* 单行注释 */

print 1+ /* hello */ 2

/**
 * 文档注释
 */
class Too {

}

/**@
 *  运行时类文档注释
 */
class Foo {

    /**@
     * 运行时方法文档注释
     */
    void bar(){

    }

}
// VM 参数 -Dgroovy.attach.runtime.groovydoc=true
assert Foo.class.groovydoc.content.contains('运行时类文档注释')
assert Foo.class.getMethod('bar',new Class[0]).groovydoc.content.contains('运行时方法文档注释')

