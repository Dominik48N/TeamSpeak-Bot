package de.dominik48n.teamspeakbot.core.bootstrap

import de.dominik48n.teamspeakbot.core.TeamSpeakBotCore

fun main() {
    Thread.currentThread().name = "teamspeak-bot-core"
    val teamSpeakBotCore = TeamSpeakBotCore()

    teamSpeakBotCore.start()

    Runtime.getRuntime().addShutdownHook(Thread({
        teamSpeakBotCore.stop()
    }, "teamspeak-bot-shutdown"))
}
