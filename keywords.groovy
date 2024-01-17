//保留关键字可以特殊作为方法名
def "abstract"(){
    print("hello")
}
this.abstract()

def "for"(){
  println "for.."
}
this.for()

def as = true
assert as

//上下文关键字  可以定义为参数 或者 方法名

def in(){
  true
}
this.in()

// 错误 关键字 无法用作变量
// def for = true
// assert for
