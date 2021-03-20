package de.dominik48n.teamspeakbot.core.document

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import de.dominik48n.teamspeakbot.core.TeamSpeakBotCore
import java.io.*
import java.nio.charset.StandardCharsets
import java.nio.file.Files

class Document(private val jsonObject: JsonObject) {

    constructor() : this(JsonObject())

    companion object {
        fun read(input: String): Document {
            return Document(TeamSpeakBotCore.GSON.fromJson(input, JsonObject::class.java))
        }

        fun read(file: File): Document? {
            if (!file.exists()) return null
            val bufferedReader = BufferedReader(FileReader(file))
            val jsonObject = TeamSpeakBotCore.GSON.fromJson(bufferedReader, JsonObject::class.java)

            return Document(jsonObject)
        }

        fun create(file: File): Document {
            try {
                val inputStream = ClassLoader.getSystemResourceAsStream(file.name);

                file.parentFile.mkdirs()
                if (inputStream != null) Files.copy(inputStream, file.toPath())
                return read(file) ?: Document()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return Document()
        }
    }

    fun appendString(key: String, value: String): Document {
        this.jsonObject.addProperty(key, value)
        return this
    }

    fun appendInt(key: String, value: Int): Document {
        this.jsonObject.addProperty(key, value)
        return this
    }

    fun appendDouble(key: String, value: Double): Document {
        this.jsonObject.addProperty(key, value)
        return this
    }

    fun appendFloat(key: String, value: Float): Document {
        this.jsonObject.addProperty(key, value)
        return this
    }

    fun appendBoolean(key: String, value: Boolean): Document {
        this.jsonObject.addProperty(key, value)
        return this
    }

    fun appendJsonElement(key: String, value: JsonElement): Document {
        this.jsonObject.add(key, value)
        return this
    }

    fun appendDocument(key: String, value: Document): Document {
        this.jsonObject.add(key, value.jsonObject)
        return this
    }

    fun getStringValue(key: String): String {
        if (!this.jsonObject.has(key)) return "null"
        return this.jsonObject.get(key).asString
    }

    fun getIntValue(key: String): Int {
        if (!this.jsonObject.has(key)) return -1
        return this.jsonObject.get(key).asInt
    }

    fun getDoubleValue(key: String): Double {
        if (!this.jsonObject.has(key)) return -1.0
        return this.jsonObject.get(key).asDouble
    }

    fun getFloatValue(key: String): Float {
        if (!this.jsonObject.has(key)) return -1.0F
        return this.jsonObject.get(key).asFloat
    }

    fun getBooleanValue(key: String): Boolean {
        if (!this.jsonObject.has(key)) return false
        return this.jsonObject.get(key).asBoolean
    }

    fun getJsonElementValue(key: String): JsonElement {
        if (!this.jsonObject.has(key)) return JsonObject()
        return this.jsonObject.get(key)
    }

    fun getDocument(key: String): Document {
        if (!this.jsonObject.has(key)) return Document()
        return Document(this.jsonObject.get(key).asJsonObject)
    }

    fun getAsJsonObject(): JsonObject {
        return this.jsonObject
    }

    fun getAsString(): String {
        return this.jsonObject.toString()
    }

    fun write(file: File) {
        val bufferedWriter = BufferedWriter(OutputStreamWriter(FileOutputStream(file), StandardCharsets.UTF_8))

        bufferedWriter.write(TeamSpeakBotCore.GSON.toJson(this.jsonObject))
        bufferedWriter.flush()
        bufferedWriter.close()
    }
}
