fun main() {
    println("Album Maker Version 0.1")

    print("please enter the name of your home folder: ")
    var homeFolder = readLine()

    print("open file from the Desktop: ")
    var openName = readLine()
    println("loading...")
    var cover = Cover("C:/Users/$homeFolder/Desktop/$openName")

    print("convert to two bit? y/n: ")
    var toConvert = readLine()
    if(toConvert == "y") {
        cover.toTwoBit()
    }

    print("add text? y/n")
    var toAdd = readLine()
    if(toAdd == "y") {
        print("name of file on Desktop: ")
        var fileName = readLine()

        print("Enter start x coordinate: ")
        var startX = Integer.parseInt(readLine())
        print("Enter start y coordinate: ")
        var startY = Integer.parseInt(readLine())

        cover.runLines("C:/Users/$homeFolder/Desktop/$fileName",30,30, startX, startY)
    }

    print("Crop? (y/n): ")
    var crop = readLine()
    if(crop == "y") {
        print("Enter start x coordinate: ")
        var startX = Integer.parseInt(readLine())
        print("Enter start y coordinate: ")
        var startY = Integer.parseInt(readLine())
        print("Enter end x coordinate: ")
        var endX = Integer.parseInt(readLine())
        print("Enter end y coordinate: ")
        var endY = Integer.parseInt(readLine())

        println("cropping...")
        cover.crop(arrayOf(startX,startY), arrayOf(startX,startY))
    }

    print("Save as: ")
    var saveName = readLine()
    println("saving to the Desktop...")
    cover.save("C:/Users/$homeFolder/Desktop/$saveName")
}