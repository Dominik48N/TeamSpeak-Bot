package de.dominik48n.teamspeakbot.logger

class TeamSpeakBotLoggerFactory {

    companion object {

        fun createLogger(): Logger {
            return this.createLogger(true)
        }

        fun createLogger(debugMode: Boolean): Logger {
            return Logger(debugMode)
        }

    }

}
