def file = new File("/Users/loern/Desktop/1.txt")
file.write("Hello world")

file.eachLine {
    line->println(line)
}  