class Foo {}
def foo = new Foo()

def map = [:]

map."an identifier with a space and double quotes" = "ALLOWED"
map.'with-dash-signs-and-single-quotes' = "ALLOWED"

assert map."an identifier with a space and double quotes" == "ALLOWED"
assert map.'with-dash-signs-and-single-quotes' == "ALLOWED"

//以下 key 会去除 ' " ''' """ / $
map.'single quote' = 1
map."double quote" = 2
map.'''triple single quote''' = 3
map."""triple double quote""" = 4
map./slashy string/ = 5
map.$/dollar slashy string/$ = 6
print(map)


def firstname = "Homer"
map."Simpson-${firstname}" = "Homer Simpson"

assert map.'Simpson-Homer' == "Homer Simpson"

def a = 's'
assert 'ab' == 'a'+'b'

print("\n")

//单引号不支持占位符替换
i = 1
a = 's${i}'
print(a)

print("\n")
//双引号支持占位符替换
x = 2
b = "s2${x}"
print(a + b)

c = ''' 
    hello jey
    you are better
'''
println(c.stripIndent()) //去除缩进
println(c.stripMargin())
assert 'ABC\n123\n456' == '''ABC
                              |123
                              |456'''.stripMargin()

//在字符串开头加入换行符 \
def strippedFirstNewline = '''\
line one
line two
line three
'''

assert !strippedFirstNewline.startsWith('\n')
println('The Euro currency symbol: \u20AC')


def sum = "The sum of 2 and 3 equals ${2 + 3}"
assert sum.toString() == 'The sum of 2 and 3 equals 5'

def name = "lucy"
println("hello ${name}")
println("hello $name")

println(" ${a = 1; b =2; a+b;}")

message = '''
this is a test name ${name}'''
println message
assert message.startsWith('\n')

newline = '''\
match line
'''
assert !newline.startsWith('\n')

//def number = 3.14
String thing = 'treasure'
//assert 'The x-coordinate of the treasure is represented by treasure.x' ==
//        "The x-coordinate of the $thing is represented by $thing.x"   // <= Not allowed: ambiguous!!
assert 'The x-coordinate of the treasure is represented by treasure.x' ==
        "The x-coordinate of the $thing is represented by ${thing}.x"  // <= Curly braces required

println("1+2 == ${w-> w << 3}")


def number = 1
def eagerGString = "value == ${number}"
def lazyGString = "value == ${ w-> w << number }"
def lazyGStringSingle = "value == ${ -> number}"

assert eagerGString == "value == 1"
assert lazyGString ==  "value == 1"

number = 2
assert eagerGString == "value == 1"
assert lazyGString ==  "value == 2"

assert  lazyGStringSingle == lazyGString


static String takeString(String message){
    assert message instanceof String
    return message
}

def th = "lastmesh ${1+2}"
def last = takeString(th)
println(last)

assert "one: ${1}".hashCode() != "one: 1".hashCode()


def key = "a"
def m = ["${key}": "letter ${key}"]

assert m["a"] == null

def ne = 'Groovy'
def template = """
    Dear Mr ${ne},

    You're the winner of the lottery!

    Yours sincerly,

    Dave
"""

assert template.toString().contains('Groovy')


def pt = /.*/
assert pt == '.*'

def multiline = /one
    two
    three/

assert multiline.contains('\n')

def color = 'blue'
def interpolated = /a ${color} car/

assert interpolated == 'a blue car'

def pic = /\t${'\\'}/
println(pic)
assert pic == '\\t\\'


name = "Guillaume"
def date = "April, 1st"

def dollarSlash = $/
    Hello $name,
    today we're ${date}.

    $ dollar sign
    $$ escaped dollar sign
    \ backslash
    / forward slash
    $/ escaped forward slash
    $$$/ escaped opening dollar slashy
    $/$$ escaped closing dollar slashy
/$

assert [
        'Guillaume',
        'April, 1st',
        '$ dollar sign',
        '$ escaped dollar sign',
        '\\ backslash',
        '/ forward slash',
        '/ escaped forward slash',
        '$/ escaped opening dollar slashy',
        '/$ escaped closing dollar slashy'
].every { dollarSlash.contains(it) }
