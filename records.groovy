import groovy.transform.RecordType

@RecordType
record Message(String from ,String to ,String body) {}
def msg = new Message("me@host.com","your@host.com","hello")
assert msg.toString() == 'Message[from=me@host.com, to=your@host.com, body=hello]'

record Point3D(int x ,int y ,int z){
  String toString(){
    "Point3D(coords=$x,$y,$z)"
  }
}
assert new Point3D(1,2,3).toString() == 'Point3D(coords=1,2,3)'

record Coord<T extends Number> (T v1,T v2){
  double distFromOrigin(){
    println v1() ** 2
    Math.sqrt(v1() ** 2 + v2() **2 as double)
  }
}
def r1 = new Coord<Integer>(3,4)
assert r1.distFromOrigin() == 5

def r2 = new Coord<Double>(6d,2.5d)
assert r2.distFromOrigin() == 6.5

public record Warning(String message){
  public Warning{
    Objects.requireNonNull(message)
    message = message.toUpperCase()
  }
}
def w = new Warning("like")
assert w.message() == "LIKE"

//支持参数默认值
record ColoredPoint(int x,y = 0 ,String color ="white"){

}

def color1 = new ColoredPoint(5,5,"black")
assert color1.toString() == 'ColoredPoint[x=5, y=5, color=black]'

def color2 = new ColoredPoint(5,5)
assert color2.toString() == 'ColoredPoint[x=5, y=5, color=white]'

def color3 = new ColoredPoint(x:9)
assert color3.toString() == 'ColoredPoint[x=9, y=0, color=white]'

import groovy.transform.TupleConstructor
import groovy.transform.DefaultsMode

@TupleConstructor(defaultsMode = DefaultsMode.OFF)//此时只有全参数默认构造函数
record ColoredPoint2(int x,int y ,String color) {}
assert new ColoredPoint2(4,5,'red').toString() == 'ColoredPoint2[x=4, y=5, color=red]'

@TupleConstructor(defaultsMode = DefaultsMode.ON)
record ColoredPoint3(int x, y = 0 ,String color = 'white') {}
assert new ColoredPoint3(y:6).toString() == 'ColoredPoint3[x=0, y=6, color=white]'

//自定义toString方法
import groovy.transform.ToString
@ToString(ignoreNulls=true,cache= true,includeNames=true,leftDelimiter='(',rightDelimiter=')',nameValueSeparator=':')
record Point(Integer x,Integer y,Integer z = null){}
assert new Point(3,4).toString() == 'Point(x:3, y:4)'


//使用@RecordOptions(toList=false)禁用该方法
//同样也可以使用 toMap方法 也适用于 @RecordOptions(toMap=false)
def p = new Point(4,5,6)
def (x,y,z) = p.toList()
assert x== 4 && y==5 && z == 6
assert p.size() ==3


import groovy.transform.RecordOptions
@RecordOptions(copyWith=true)
record Fruit(String name, double price){

}
def apple = new Fruit('apple',1.2)
def orange = apple.copyWith(name:'Orange')
assert orange.toString() == 'Fruit[name=Orange, price=1.2]'

import groovy.transform.ImmutableProperties
@ImmutableProperties
record Shopping(List items){}
def items = ['bread','milk']
def shop = new Shopping(items: items)
items << 'bear'
println items
assert shop.items() == ['bread','milk']

@RecordOptions(components=true)
record Comp(int x,int y ,String color){}
println new Comp(1,2,'DARK').components()

//sealed 必须和 permits 一起使用
import groovy.transform.Sealed

//或者替换成 @Sealed(permittedSubClasses=[]) 实现
// @Sealed(permittedSubClasses=[Polygon])
sealed class Shape permits Circle,Rectangle,Polygon {}
final class Circle extends Shape {}

class Polygon extends Shape {}
non-sealed class RegularPolygon extends Polygon {}
final class Hexagon extends Polygon {}

sealed class Rectangle extends Shape permits Square {}
final class Square extends Rectangle {}

enum Weather {Rainy ,Cloudy,Sunny}
def forecast = [Weather.Rainy , Weather.Cloudy, Weather.Sunny]
assert forecast.toString() == '[Rainy, Cloudy, Sunny]'

import groovy.transform.Immutable

sealed abstract class Wea {}
@Immutable(includeNames=true) class Rainy extends Wea { Integer expectedRainFall }
@Immutable(includeNames=true) class Sunny extends Wea { Integer expectedTemp } 
@Immutable(includeNames=true) class Cloudy extends Wea { Integer expectedUV }
def forecast2 = [new Rainy(12), new Sunny(35), new Cloudy(6)]
assert forecast2.toString() == '[Rainy(expectedRainFall:12), Sunny(expectedTemp:35), Cloudy(expectedUV:6)]'

import groovy.lang.Singleton
import groovy.transform.Canonical

sealed interface Tree<T> {}

@Singleton final class Empty implements Tree {
  String toString() {'Empty'}
}

@Canonical final class Node<T> implements Tree<T> {
  T value 
  Tree<T> left ,right
}
println Node.class.getDeclaredConstructors()*.toString()
def bp = new Node<Integer>(0,Empty.instance,Empty.instance)

Tree<Integer> tree = new Node<Integer>(42,new Node<Integer >(0,Empty.instance,Empty.instance),Empty.instance)
