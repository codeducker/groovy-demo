import org.apache.groovy.groovysh.CommandSupport
import org.apache.groovy.groovysh.Groovysh 
//这里在命令行下输入 groovysh 
// 然后使用 :register 类名 进行自定义命令注册
// 使用 :prefix 进行调用
class Stats extends CommandSupport {
  protected Stats(final Groovysh shell){
    super(shell,'stats','T')
  }
  public Object execute(List args){
    println "new Line : ${Runtime.runtime.freeMemory()}"
  }
}
