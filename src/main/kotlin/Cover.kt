import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.color.RGBColor
import com.sksamuel.scrimage.nio.PngWriter
import com.sksamuel.scrimage.pixels.Pixel
import java.io.File

class Cover(dir: String) {
    private val image = ImmutableImage.loader().fromFile(dir)
    private val writer = PngWriter()

    private var pixels: MutableList<MutableList<MutableList<Int>>> = mutableListOf()

    init {
        for(y in 0 until image.height) {
            var row: MutableList<MutableList<Int>> = mutableListOf()
            for(x in 0 until image.width) {
                val pixel = image.pixel(x,y)
                val values: MutableList<Int> = mutableListOf(pixel.red(), pixel.green(), pixel.blue())
                row.add(values)
            }
            pixels.add(row)
        }
    }

    //writes pixels to image file "dir"
    fun save(dir: String) {
        var blankImage = ImmutableImage.create(pixels[0].size, pixels.size)

        var saveImage = blankImage.map { pixel: Pixel ->
            RGBColor(
                pixels[pixel.y][pixel.x][0],
                pixels[pixel.y][pixel.x][1],
                pixels[pixel.y][pixel.x][2],
            ).awt()
        }

        saveImage.output(writer, File(dir))
    }

    //finds a middle grey. Everything above it goes to white and everything under it goes to black.
    //needs to be updated to use the pixels array
    fun toTwoBit() {
        //find average grey
        var totalValues: Long = 0

        image.forEach { pixel: Pixel ->
            totalValues += pixel.red()
            totalValues += pixel.green()
            totalValues += pixel.blue()
        }

        var averageGrey: Long = totalValues / image.count()

        //turns the pixed white or black depending on if they are over the average grey
        for(y in 0 until pixels.size) {
            for(x in 0 until pixels[0].size) {
                var pixelTotal = pixels[y][x][0] + pixels[y][x][1] + pixels[y][x][2]
                if(pixelTotal >= averageGrey) {
                    pixels[y][x][0] = 255
                    pixels[y][x][1] = 255
                    pixels[y][x][2] = 255
                }
                else {
                    pixels[y][x][0] = 0
                    pixels[y][x][1] = 0
                    pixels[y][x][2] = 0
                }
            }
        }
    }

    fun crop(start: Array<Int>, end: Array<Int>) {
        for(y in 0 until start[1]) {
            pixels.removeAt(y)
        }
        val sizeY = pixels.size
        for(y in sizeY - 1 downTo end[1] + 1) {
            pixels.removeAt(y)
        }
        for(row in pixels) {
            for(x in 0 until start[0]) {
                row.removeAt(x)
            }
            val sizeX = pixels[0].size
            for(x in sizeX - 1 downTo end[0] + 1) {
                row.removeAt(x)
            }
        }
    }

    fun addBorder(width: Int) { //this function adds a transparent border, but also makes it square. Width is the width of the thickest part of the border.

    }

    fun styleTransparent() { //adds pattern to transparent areas of the image. Commonly the border.

    }

    //if color 2 is within the input range then it returns true.
    private fun colorCompare(range: Int, softness: Int, color1: MutableList<Int>, color2: MutableList<Int>): Boolean {
        for(i in 0..2) {
            var difference = color1[i] - color2[i]
            if (difference < 0) {
                difference *= -1
            }
            if (difference >= range) {
                return false
            }
        }
        return true
    }

    fun runLines(fileName: String, range: Int, softness: Int, startX: Int, startY: Int) {
        val color = pixels[startY - 1][startX]
        var a = ImmutableImage.loader().fromFile(fileName)

        println(a.height)
        println(a.width)

        var y = 0
        var x = 0

        while(y < a.height) {
            print("Y $y")
            while(x < a.width) {
                print("X $x")
                if(colorCompare(range, softness, color, pixels[startY + y][startX + x])){
                    if (a.pixel(x,y).alpha() != 0) {
                        pixels[startY + y][startX + x][0] -= 255 - a.pixel(x, y).red()
                        if (pixels[startY + y][startX + x][0] < 0) {
                            pixels[startY + y][startX + x][0] = 0
                        }

                        pixels[startY + y][startX + x][1] -= 255 - a.pixel(x, y).green()
                        if (pixels[startY + y][startX + x][1] < 0) {
                            pixels[startY + y][startX + x][1] = 0
                        }

                        pixels[startY + y][startX + x][2] -= 255 - a.pixel(x, y).blue()
                        if (pixels[startY + y][startX + x][2] < 0) {
                            pixels[startY + y][startX + x][2] = 0
                        }
                    }
                }
                x++
            }
            y++
        }
    }
}