package de.dominik48n.teamspeakbot.core.event

import com.github.theholywaffle.teamspeak3.TS3ApiAsync
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode
import com.github.theholywaffle.teamspeak3.api.event.*
import de.dominik48n.teamspeakbot.core.TeamSpeakBotCore
import de.dominik48n.teamspeakbot.core.module.AFKMoveModule
import de.dominik48n.teamspeakbot.core.module.SupportModule
import java.text.MessageFormat

class TeamSpeakEvent(private val api: TS3ApiAsync) {

    private val queryId = api.whoAmI().get().id

    private val afkMoveModule = AFKMoveModule(api)
    private val supportModule = SupportModule(api)

    fun init() {
        this.afkMoveModule.startAFKCheck()

        this.api.registerAllEvents()
        this.api.addTS3Listeners(object: TS3Listener {

            override fun onTextMessage(event: TextMessageEvent) {
                if (event.targetMode != TextMessageTargetMode.CLIENT) return
                if (event.invokerId == queryId) return
                val message = event.message

                // Send a help message
                if (message.equals("!help", true)) {
                    api.sendPrivateMessage(event.invokerId, TeamSpeakBotCore.MESSAGE_CONFIG.getStringValue("help-message"))
                    return
                }

                // TeamSpeak Bot Developer credits
                if (message.equals("!bot", true) ||
                    message.equals("!info", true) ||
                    message.equals("!dev", true) ||
                    message.equals("!developer", true)) {
                    api.sendPrivateMessage(event.invokerId, "The TeamSpeak Bot was developed by [url=https://dominik48n.de]Dominik48N[/url].")
                    return
                }
                val clientId = event.invokerId
                val client = api.getClientInfo(clientId).get()

                if (message.equals("!support", true) && client.isInServerGroup(TeamSpeakBotCore.CORE_CONFIG.getIntValue("access.supporter.servergroupid"))) {

                    // Check if the client is in the supporter list
                    if (supportModule.supporters.containsKey(clientId)) {
                        // Remove the client from the supporter list
                        supportModule.supporters.remove(clientId)

                        api.sendPrivateMessage(clientId, TeamSpeakBotCore.MESSAGE_CONFIG.getStringValue("quit-support"))
                    } else {
                        // Put the client in the supporter list
                        supportModule.supporters[clientId] = client

                        api.sendPrivateMessage(clientId, TeamSpeakBotCore.MESSAGE_CONFIG.getStringValue("join-support"))
                    }

                    return
                }

                if (client.isInServerGroup(TeamSpeakBotCore.CORE_CONFIG.getIntValue("access.supportadmin.servergroupid"))) {

                    // Open the support channel
                    if (message.equals("!open", true)) {
                        supportModule.openSupport()
                        api.sendPrivateMessage(clientId, TeamSpeakBotCore.MESSAGE_CONFIG.getStringValue("open-support"))
                        return
                    }

                    // Close the support channel
                    if (message.equals("!close", true)) {
                        supportModule.closeSupport()
                        api.sendPrivateMessage(clientId, TeamSpeakBotCore.MESSAGE_CONFIG.getStringValue("close-support"))
                        return
                    }

                }

            }

            override fun onClientJoin(event: ClientJoinEvent) {
                // Client join message
                api.sendPrivateMessage(event.clientId, MessageFormat.format(TeamSpeakBotCore.MESSAGE_CONFIG.getStringValue("join-message"), event.clientNickname))
            }

            override fun onClientLeave(event: ClientLeaveEvent) {

            }

            override fun onServerEdit(event: ServerEditedEvent) {

            }

            override fun onChannelEdit(event: ChannelEditedEvent) {

            }

            override fun onChannelDescriptionChanged(event: ChannelDescriptionEditedEvent) {

            }

            override fun onClientMoved(event: ClientMovedEvent) {
                val channelId = event.targetChannelId

                // Support Channel
                if (TeamSpeakBotCore.CORE_CONFIG.getIntValue("channelid.support") == channelId) {
                    val clientId = event.clientId
                    val name = api.getClientInfo(clientId).get().nickname

                    // Poke all supporters
                    for (supporter in supportModule.supporters) {
                        api.pokeClient(supporter.key, MessageFormat.format(TeamSpeakBotCore.MESSAGE_CONFIG.getStringValue("support.poke"), name))
                    }

                    // Send a privat message to the client
                    api.sendPrivateMessage(clientId, TeamSpeakBotCore.MESSAGE_CONFIG.getStringValue("support.joined"))
                    return
                }

            }

            override fun onChannelCreate(event: ChannelCreateEvent) {

            }

            override fun onChannelDeleted(event: ChannelDeletedEvent) {

            }

            override fun onChannelMoved(event: ChannelMovedEvent) {

            }

            override fun onChannelPasswordChanged(event: ChannelPasswordChangedEvent) {

            }

            override fun onPrivilegeKeyUsed(event: PrivilegeKeyUsedEvent) {

            }

        })
    }

}
