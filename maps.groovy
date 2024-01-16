def colors = [red : 1,yellow:2]
assert colors['red'] == 1
assert colors.yellow == 2
colors['blue'] = 3
assert colors.unknown == null

def emptyMap = [:]
assert  emptyMap.anyKey == null

def otherMap =[1:"hello"]
otherMap[1] = "hello"

def key = 'name'
def person = ["${key}": 'Guillaume']
println(person)

assert person.containsKey("${key}")

//assert person.containsKey('name')
assert !person.containsKey('key')

key = 'name'
person = [(key): 'Guillaume']
println(person)
assert person.containsKey('name')
assert !person.containsKey('key')