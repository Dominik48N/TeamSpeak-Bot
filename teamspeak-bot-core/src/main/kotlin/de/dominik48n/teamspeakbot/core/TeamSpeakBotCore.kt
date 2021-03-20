package de.dominik48n.teamspeakbot.core

import de.dominik48n.teamspeakbot.logger.TeamSpeakBotLoggerFactory
import kotlin.system.exitProcess

class TeamSpeakBotCore {

    companion object {
        val LOGGER = TeamSpeakBotLoggerFactory.createLogger()
    }

    fun start() {
        LOGGER.info("Starting TeamSpeakBot...")
    }

    fun stop() {
        LOGGER.info("Stopping TeamSpeakBot...")

        exitProcess(0)
    }

}
