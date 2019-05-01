import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

fun main() {
    val relicDoc = getXML("relic-data.xml")
    println("Root element: ${relicDoc.documentElement.nodeName}")

    val relicList = relicDoc.getElementsByTagName("tr")
    println("Elements in the list: ${relicList.length}")
    println("Example of relic data: ${relicList.item(0)}")
    for (i in 0 until relicList.length) {
        val node = relicList.item(i) as Element
        println("Element name at ${i+1}: ${node.tagName}")
    }
}

fun getXML(filename: String): Document {
    val builder = (DocumentBuilderFactory.newInstance()).newDocumentBuilder()
    val file = File(filename)
    //TODO: handle file not found
    val document = builder.parse(file)
    document.documentElement.normalize()
    return document
}