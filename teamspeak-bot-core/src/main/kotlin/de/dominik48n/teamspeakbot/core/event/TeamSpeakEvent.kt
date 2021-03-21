package de.dominik48n.teamspeakbot.core.event

import com.github.theholywaffle.teamspeak3.TS3ApiAsync
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode
import com.github.theholywaffle.teamspeak3.api.event.*
import de.dominik48n.teamspeakbot.core.TeamSpeakBotCore
import java.text.MessageFormat

class TeamSpeakEvent(private val api: TS3ApiAsync) {

    private val queryId = api.whoAmI().get().id

    fun init() {
        this.api.registerAllEvents()
        this.api.addTS3Listeners(object: TS3Listener {

            override fun onTextMessage(event: TextMessageEvent) {
                if (event.targetMode != TextMessageTargetMode.CLIENT) return
                if (event.invokerId == queryId) return
                val message = event.message

                if (message.equals("!help", true)) {
                    api.sendPrivateMessage(event.invokerId, TeamSpeakBotCore.MESSAGE_CONFIG.getStringValue("help-message"))
                    return
                }

                if (message.equals("!bot", true) ||
                    message.equals("!info", true) ||
                    message.equals("!dev", true) ||
                    message.equals("!developer", true)) {
                    api.sendPrivateMessage(event.invokerId, "The TeamSpeak Bot was developed by [url=https://dominik48n.de]Dominik48N[/url].")
                    return
                }

            }

            override fun onClientJoin(event: ClientJoinEvent) {
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
