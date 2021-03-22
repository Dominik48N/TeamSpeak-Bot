package de.dominik48n.teamspeakbot.core

import com.github.theholywaffle.teamspeak3.TS3ApiAsync
import com.github.theholywaffle.teamspeak3.TS3Config
import com.github.theholywaffle.teamspeak3.TS3Query
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import de.dominik48n.teamspeakbot.core.document.Document
import de.dominik48n.teamspeakbot.core.event.TeamSpeakEvent
import de.dominik48n.teamspeakbot.logger.TeamSpeakBotLoggerFactory
import java.io.File
import kotlin.system.exitProcess

class TeamSpeakBotCore {

    companion object {
        val GSON: Gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
        val LOGGER = TeamSpeakBotLoggerFactory.createLogger()
        var RUNNING = false
        val CONFIG = TS3Config()
        val QUERY = TS3Query(CONFIG)
        val API = TS3ApiAsync(QUERY)

        lateinit var MESSAGE_CONFIG: Document
        lateinit var MODULE_CONFIG: Document
        lateinit var CORE_CONFIG: Document
    }

    fun start() {
        LOGGER.info("Starting TeamSpeakBot...")

        RUNNING = true

        this.prepareConfig()
        this.prepareTeamspeakBot()
    }

    fun stop() {
        LOGGER.info("Stopping TeamSpeakBot...")

        RUNNING = false

        Thread.currentThread().stop()
        exitProcess(0)
    }

    private fun prepareConfig() {
        val configFile = File("teamspeakbot", "config.json")
        val config = Document.read(configFile) ?: Document.create(configFile)

        MESSAGE_CONFIG = config.getDocument("messages")
        MODULE_CONFIG = config.getDocument("modules")
        CORE_CONFIG = config.getDocument("config")
    }

    private fun prepareTeamspeakBot() {
        CONFIG.setHost(CORE_CONFIG.getStringValue("host"))
        CONFIG.setFloodRate(TS3Query.FloodRate.UNLIMITED)

        QUERY.connect()

        API.login(CORE_CONFIG.getStringValue("login.user"), CORE_CONFIG.getStringValue("login.password"))
        API.selectVirtualServerByPort(CORE_CONFIG.getIntValue("port"))
        API.setNickname(CORE_CONFIG.getStringValue("bot.nickname"))

        // Initialize the events
        TeamSpeakEvent(API).init()
    }

}
