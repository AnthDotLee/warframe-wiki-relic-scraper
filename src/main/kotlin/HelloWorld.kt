import org.w3c.dom.Document
import org.w3c.dom.Element
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

fun main() {
    val relicDoc = getXML("relic-data.xml")
    println("Root element: ${relicDoc.documentElement.nodeName}")

    val relics:MutableList<Relic> = mutableListOf()

    val relicList = relicDoc.getElementsByTagName("tr")
    for (i in 0 until relicList.length) {
        val node = relicList.item(i) as Element
        println("Element name at ${i+1}: ${node.tagName}")
        println("first child: ${node.firstChild.nodeName}")
        relics.add(Relic(
            era = node.firstChild.firstChild.textContent.trim(),
            name = node.firstChild.nextSibling.firstChild.firstChild.textContent,
            commonRewards = mutableListOf(
                node.firstChild.nextSibling.nextSibling.firstChild.firstChild.firstChild.firstChild.textContent.trim(),
                node.firstChild.nextSibling.nextSibling.firstChild.firstChild.nextSibling.firstChild.firstChild.textContent.trim(),
                node.firstChild.nextSibling.nextSibling.firstChild.firstChild.nextSibling.nextSibling.firstChild.firstChild.textContent.trim()
            ),
            uncommonRewards = mutableListOf(
                node.firstChild.nextSibling.nextSibling.nextSibling.firstChild.firstChild.firstChild.firstChild.textContent.trim(),
                node.firstChild.nextSibling.nextSibling.nextSibling.firstChild.firstChild.nextSibling.firstChild.firstChild.textContent.trim()
            ),
            rareReward = node.firstChild.nextSibling.nextSibling.nextSibling.nextSibling.firstChild.firstChild.firstChild.firstChild.textContent.trim()
        ))
    }
    println("resulting list size: ${relics.size}")
}

fun getXML(filename: String): Document {
    val builder = (DocumentBuilderFactory.newInstance()).newDocumentBuilder()
    val file = File(filename)
    //TODO: handle file not found
    val document = builder.parse(file)
    document.documentElement.normalize()
    return document
}