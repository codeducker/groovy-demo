//class TestGroovy {
    def printlnNames(String[] args){
        args.each {
            print " $it"
        }
        return args.length
    }
//}
printlnNames(args)