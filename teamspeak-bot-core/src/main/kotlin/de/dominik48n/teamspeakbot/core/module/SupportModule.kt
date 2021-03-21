package de.dominik48n.teamspeakbot.core.module

import com.github.theholywaffle.teamspeak3.TS3ApiAsync
import com.github.theholywaffle.teamspeak3.api.ChannelProperty
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo
import de.dominik48n.teamspeakbot.core.TeamSpeakBotCore
import java.util.*
import kotlin.collections.HashMap

class SupportModule(private val api: TS3ApiAsync) {

    val supporters: HashMap<Int, ClientInfo> = HashMap()

    fun openSupport() {
        val options: HashMap<ChannelProperty, String> = java.util.HashMap()

        options[ChannelProperty.CHANNEL_NAME] = TeamSpeakBotCore.MESSAGE_CONFIG.getStringValue("support.open.channel.name")
        options[ChannelProperty.CHANNEL_PASSWORD] = ""
        options[ChannelProperty.CHANNEL_DESCRIPTION] = TeamSpeakBotCore.MESSAGE_CONFIG.getStringValue("support.open.channel.description")

        api.editChannel(TeamSpeakBotCore.CORE_CONFIG.getIntValue("channelid.support"), options)
    }

    fun closeSupport() {
        val options: HashMap<ChannelProperty, String> = java.util.HashMap()

        options[ChannelProperty.CHANNEL_NAME] = TeamSpeakBotCore.MESSAGE_CONFIG.getStringValue("support.close.channel.name")
        options[ChannelProperty.CHANNEL_PASSWORD] = "${UUID.randomUUID()} - ${UUID.randomUUID()} - ${UUID.randomUUID()}"
        options[ChannelProperty.CHANNEL_DESCRIPTION] = TeamSpeakBotCore.MESSAGE_CONFIG.getStringValue("support.close.channel.description")

        api.editChannel(TeamSpeakBotCore.CORE_CONFIG.getIntValue("channelid.support"), options)
    }

}
