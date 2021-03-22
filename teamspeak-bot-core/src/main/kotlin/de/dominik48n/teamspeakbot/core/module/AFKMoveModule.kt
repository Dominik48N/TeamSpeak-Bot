package de.dominik48n.teamspeakbot.core.module

import com.github.theholywaffle.teamspeak3.TS3ApiAsync
import de.dominik48n.teamspeakbot.core.TeamSpeakBotCore

class AFKMoveModule(private val api: TS3ApiAsync) {

    fun startAFKCheck() {
        if (!TeamSpeakBotCore.MODULE_CONFIG.getBooleanValue("afk-mover")) return // Check if the afk mover module is activated
        val afkChannel = TeamSpeakBotCore.CORE_CONFIG.getIntValue("channelid.afk")
        val idleTime = TeamSpeakBotCore.CORE_CONFIG.getIntValue("afk.idletimeformove") * 1000

        while (TeamSpeakBotCore.RUNNING) {
            Thread.sleep(1000)

            for (client in api.clients.get()) {
                if (client.channelId == afkChannel) continue
                if (client.idleTime < idleTime) continue
                val id = client.id

                api.moveClient(id, afkChannel)
                api.sendPrivateMessage(id, TeamSpeakBotCore.MESSAGE_CONFIG.getStringValue("afk.move.message"))
            }
        }
    }

}
