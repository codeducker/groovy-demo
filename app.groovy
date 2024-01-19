import groovy.xml.MarkupBuilder


class XmlTest {

  static void main(String[] args){
    def s = new StringWriter()
    def xml = new MarkupBuilder(s)
    xml.html{
        head{
            title("Hello")
            script(ahref:'https://xxxx.com/vue.js')
        }
        body{
            p("Excited")
        }
    }
    println s.toString()
  }
}
