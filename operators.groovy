//import java.util.regex.Pattern


import java.util.regex.Matcher

assert 1 +2 == 3 
assert 4 -1 ==3 
assert 3 * 4 == 12 
assert 4 / 2 == 2 
assert 4 % 2 == 0 
assert 3 ** 3 == 27 

assert +3 ==  3 
assert -3 == 0-3
assert -(-3) == 3 


def a1 = 2 
def b1 = a1++ * 3 
assert a1 == 3 && b1 == 6 


def a2  = 3 
def b2 = ++a2 * 3 
assert a2 == 4 && b2 ==12


def ac = 4 
ac += 2 
println(ac)




//pattern = /he/
//
//def reg = ~/p/
//assert reg instanceof Pattern
//
//reg = ~'foo'
//assert reg instanceof Pattern
//reg = ~"foo"
//assert reg instanceof Pattern
//reg = ~$/dollar/slashy $ string/$
//assert reg instanceof Pattern
//reg = ~"${pattern}"
//assert reg instanceof Pattern

//def text = "some text to match"
//def m = text ==~ /match/ //返回 Matcher 相当于  if(m.find(0))
//def m = text =~ /match/
//println(m.group())
//if (!m) {
//    throw new RuntimeException("Should not reach that point!")
//}
//println(m)
//println(m.find())
//assert m instanceof Matcher
//if (!m) {
//    throw new RuntimeException("Oops, text not found!")
//}

//
//class User {
//    String name
//    int sex
//    User(String name){ this.name = name}
//    String getName(){
//        "name:$name"
//    }
//}
//
//def user = new User("hello")
//println(user.sex ? user.sex : "0")
//user.with {sex?=2} //sex为空则赋值2
//println(user.sex ?: "0")
//assert user.name == 'name:hello'//调用getXxx方法
//assert user.@name  == 'hello' //调用原始属性值


//def str = 'example of method reference'
//def fun = str.&toUpperCase
//def upper = fun()
//assert upper == str.toUpperCase()
//
//
//def met = String.&toString
//println(met("hello"))

//import static java.util.stream.Collectors.toList
//
//assert 6G == [1G, 2G, 3G].stream().reduce(0G, BigInteger::add)
//
//assert [4G, 5G, 6G] == [1G, 2G, 3G].stream().map(3G::add).collect(toList())
//
//assert [1G, 2G, 3G] == [1L, 2L, 3L].stream().map(BigInteger::valueOf).collect(toList())
//
//assert [1G, 2G, 3G] == [1L, 2L, 3L].stream().map(3G::valueOf).collect(toList())

//def result = [1,3,4].stream().toArray(int[]::new)
//println(result)

//user = new User();
//user.setName("")
//
//displayName = user.name ? user.name : 'Anonymous' // displayName = user.name ?: 'Anonymous'
//
//println(displayName)
//
//if ( user.name ) {
//    println("1111")
//}
//
////def user = User.find { it.id == 123 }
//user = null
//def name = user?.name
//assert  user.get()
//assert name == null
//
//class Person {
//    String name
//    int age
//}

//def transform(List elements, Closure action) {
//    def result = []
//    elements.each {
//        result << action(it)
//    }
//    result
//}
//String describe(Person p) {
//    "$p.name is $p.age"
//}
//def action = this.&describe
//def list = [
//        new Person(name: 'Bob',   age: 42),
//        new Person(name: 'Julia', age: 35)]
//assert transform(list, action) == ['Bob is 42', 'Julia is 35']

//def foo = BigInteger::new
//def foo2 = BigInteger.&new
//println(foo(21))
//assert foo === foo2
//println(/\s+/)


//assert 'two words' ==~ /\S+\s+\S+/
//assert 'two words' ==~ /^\S+\s+\S+$/
//assert !(' leading space' ==~ /\S+\s+\S+/)

//def m3 = 'now three words' =~ /\S+\s+\S+/
//println(m3.groupCount())
//
//
//class P {
//    final String make
//    P(String make){
//        this.make = make
//    }
//}

//def ps = [new P(),new P("helo"),null]
//println(ps*.make)
//import static java.util.stream.Collectors.toList
//pd = ps.stream().map (p->p.make).collect(toList())
//println(pd)

//
//class Component {
//    Integer id
//    String name
//}
//class CompositeObject implements Iterable<Component> {
//    def components = [
//            new Component(id: 1, name: 'Foo'),
//            new Component(id: 2, name: 'Bar')]
//
//    @Override
//    Iterator<Component> iterator() {
//        components.iterator()
//    }
//}
//def composite = new CompositeObject()
//assert composite*.id == [1,2]
//assert composite*.name == ['Foo','Bar']


//models = [["name", "like"],["some" , "ok" , "other"]]
//println(models.sum())
//println(models.flatten())
//
//class Car {
//    String make
//    String model
//}
//def cars = [
//        [
//                new Car(make: 'Peugeot', model: '408'),
//                new Car(make: 'Peugeot', model: '508')
//        ], [
//                new Car(make: 'Renault', model: 'Clio'),
//                new Car(make: 'Renault', model: 'Captur')
//        ],
//        null
//]
//def models = cars.collectNested{ it.model }
//assert models == [['408', '508'], ['Clio', 'Captur']]

//def func (int x ,int y,int z ){x+y+z}
//def args = [1,34]
//println(func(*args))

//def mp = [c:3,d:4]
//def m1 = [a:1,*:mp]
//println(m1)
//
//def items = [4,5]
//def list = [1,2,3,*items,6]
//println(list)

//def range = 0..5
//def range = 0 <..5
//assert  range instanceof List
//
//println(1 <=> 2)

//def list = [1,3]
//list[1] = 3 //下标 0 开始
//list[2..4] = [6,7,8]
//println(list)


//class User {
//    Long id
//    String name
//    def getAt(int i) {
//        switch (i) {
//            case 0: return id
//            case 1: return name
//        }
//        throw new IllegalArgumentException("No such element $i")
//    }
//    void putAt(int i, def value) {
//        switch (i) {
//            case 0: id = value; return
//            case 1: name = value; return
//        }
//        throw new IllegalArgumentException("No such element $i")
//    }
//}
//def user = new User(id: 1, name: 'Alex')
//assert user[0] == 1
//assert user[1] == 'Alex'
//user[1] = 'Bob'

//def list = [1,3]
//println(list?[1])
//println(list?[4])

//
//array = null
//assert null == array?[1]     // return null for all index values
//array?[1] = 'c'              // quietly ignore attempt to set value
//assert null == array?[1]

//def list = [1,3,40]
//println(list.isCase(1))

//def list1 = ['Groovy 1.8','Groovy 2.0','Groovy 2.3']
//def list2 = ['Groovy 1.8','Groovy 2.0','Groovy 2.3']
//assert list1 == list2
//assert !list1.is(list2)
//assert list1 !== list2
//String a = "1ab"
//int b = a as int
//println(
//        b
//)
//
//class Identifiable {
//    String name
//}
//
//class User {
//    Long id
//    String name
//
//    def asType(Class target){
//        if ( target == Identifiable ){
//            return new Identifiable(name:name)
//        }else{
//            return null
//        }
//    }
//
//    def call(){
//        id +=23
//    }

//}
//def u = new User(id:1,name:"li")
//def p = u  as Identifiable
//println(p)
//
//
//def user = new User(id: 1)
//user.call()
//println(user.id)
//user()
//println(user.id)

class Bnu {
    int size
    Bnu(int size){
        this.size = size
    }
    Bnu plus( Bnu other){
        return new Bnu(this.size + other.size)
    }
}
def bnu1 = new Bnu(1)
def bnu2 = new Bnu(2)
println(( bnu1 + bnu2 ).size)
