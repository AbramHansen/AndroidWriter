//global "top level" variable
val name: String = "Abram"

//function with Int return type
fun add(x: Int,y: Int): Int{
    return x + y
}
//function with optional parameters
fun addLots(vararg numbers: Int): Int{
    var total = 0
    numbers.forEach {
        total += it
    }
    return total
}

//function that does not return a value
fun sayHi(){
    println("Hi!")
}

fun main(){
    //print without newline
    print("Hello World! ")
    //print with new line
    println("Hello " + name + "!")

    //standard variable
    var x: Int = 1
    //type not required
    var y = 5
    //immutable
    val pi = 3.1415
    //nullable variable
    var n: Int? = null

    //if else
    if(y == 10) {
        println("y is 10!")
    } else if(y == 5){
        println("y is 5!")
    } else {
        println("y is not 5 or 10!")
    }

    y = 10
    //switch case equivalent
    when(y){
        10 -> println("y is now 10!")
        30 -> println("y is now 30!")
        5 -> println("y is now 5!")
    }

    //calling a function
    println("x + y = " + add(x,y))
    println(addLots(5,6,3,74))
    sayHi()
    //pass in existing data using the spreader operator (unpacks the array)
    val numsToAdd = arrayOf(23,876,2,234)
    //not working println(addLots(*numsToAdd))

    //array
    val things = arrayOf("Cheese","Ice Cream", "Milk")
    println("Number of things: " + things.size)
    //mutable list
    val numbers = mutableListOf(1,0,5,2,4,0,1,3)
    numbers.add(2)
    //map (dictionary equivalent)
    val ages = mapOf("Abram" to 22, "Fabiola" to 24)
    println("Abram is " + ages["Abram"])
    //for loop
    for(thing in things) {
        println(thing)
    }
    //functional way of doing it with index
    things.forEachIndexed { i, thing ->
        println("$thing at index $i")
    }
}