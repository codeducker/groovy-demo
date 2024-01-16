class DataTypes{
    static def methodA(int x = 20, int y = 30){
        return x+y
    }
    static void main(String[] args){
        byte data = 2
        short less = 1
        long value = 45
        double df = 1.3
        float vad = 3.4
        char va = 'c'
        String ch = "he"
        def range = 0..5
        for(index in range) {
            println(index)
        }
        methodA()
    }
}