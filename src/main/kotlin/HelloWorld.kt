import org.w3c.dom.Document
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

fun main() {
    val relicDoc = getXML("relic-data.xml")
    println("Root element: ${relicDoc.documentElement.nodeName}")
}

fun getXML(filename: String): Document {
    val builder = (DocumentBuilderFactory.newInstance()).newDocumentBuilder()
    val file = File(filename)
    //TODO: handle file not found
    val document = builder.parse(file)
    document.documentElement.normalize()
    return document
}