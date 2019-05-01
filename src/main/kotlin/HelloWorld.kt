import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

fun main() {
    println("Hello World!")
}

fun getXML(filename: String) {
    var builder = (DocumentBuilderFactory.newInstance()).newDocumentBuilder()
    var file = File("relic-data.xml")
}