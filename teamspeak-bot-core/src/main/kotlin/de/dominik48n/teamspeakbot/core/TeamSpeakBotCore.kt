package de.dominik48n.teamspeakbot.core

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import de.dominik48n.teamspeakbot.core.document.Document
import de.dominik48n.teamspeakbot.logger.TeamSpeakBotLoggerFactory
import java.io.File
import kotlin.system.exitProcess

class TeamSpeakBotCore {

    companion object {
        val GSON: Gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
        val LOGGER = TeamSpeakBotLoggerFactory.createLogger()

        lateinit var CONFIG: Document
    }

    fun start() {
        LOGGER.info("Starting TeamSpeakBot...")

        this.prepareConfig()
    }

    fun stop() {
        LOGGER.info("Stopping TeamSpeakBot...")

        exitProcess(0)
    }

    private fun prepareConfig() {
        val configFile = File("teamspeakbot", "config.json")
        val config = Document.read(configFile) ?: Document.create(configFile)

        CONFIG = config.getDocument("config")
    }

}
