import com.mongodb.MongoException
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

fun main() {
    val relicDoc = getXML("relic-data.xml")
    println("Root element: ${relicDoc.documentElement.nodeName}")

    val relics: MutableList<Relic> = parseRelicTable(relicDoc.getElementsByTagName("tr"))
    try {
        saveRelicsToDB(relics)
    } catch (ex: Exception) {
        println(ex)
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

fun saveRelicsToDB(relics: Iterable<Relic>) {
// Drops the whole collection and re-inserts the data

    var mongoClient: MongoClient? = null
    try {
        mongoClient = MongoClients.create("mongodb://127.0.0.1:27017")
        println("Connected to MongoDB")
    } catch (ex: MongoException) {
        ex.printStackTrace()
    }

    val mongoDB = mongoClient!!.getDatabase("warframe-relic-db")

// Drop the existing collection
    val collection = mongoDB.getCollection("relics")
    collection.drop()

// Insert relic using a default vaulted status
    for (relic in relics) {
        val relicDoc = org.bson.Document("name", relic.name)
            .append("era", relic.era)
            .append(
                "rewards", org.bson.Document("common", relic.commonRewards)
                    .append("uncommon", relic.uncommonRewards)
                    .append("rare", relic.rareReward)
            )
            .append("vaulted", relic.isVaulted)
        collection.insertOne(relicDoc)
    }

// Update the Vaulted status
}

fun parseRelicTable(relicList: NodeList): MutableList<Relic> {
    val relics = mutableListOf<Relic>()
    try {
        for (i in 0 until relicList.length) {
            val node = relicList.item(i) as Element
            relics.add(
                Relic(
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
                )
            )
        }
    } catch (exception: Exception) {
        println("Unable to parse table into list. Ex: $exception")
        return mutableListOf()
    }
    return relics
}

fun updateVaultedStatus() {
    throw NotImplementedError("Not done yet")
}