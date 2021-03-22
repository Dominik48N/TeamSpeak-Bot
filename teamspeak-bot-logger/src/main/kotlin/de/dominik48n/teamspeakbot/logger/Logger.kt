package de.dominik48n.teamspeakbot.logger

import java.text.SimpleDateFormat
import java.util.*

class Logger(private val debugMode: Boolean) {

    private val simpleDateFormat = SimpleDateFormat("YYYY-MM-dd HH:mm:ss")

    fun empty(message: String) {
        println(message)
    }

    fun info(message: String) {
        this.empty(this.prepareLogMessage("INFO", message))
    }

    fun warn(message: String) {
        this.empty(this.prepareLogMessage("WARN", message))
    }

    fun error(message: String) {
        this.empty(this.prepareLogMessage("ERROR", message))
    }

    fun debug(message: String) {
        if (this.debugMode) this.empty(this.prepareLogMessage("DEBUG", message))
    }

    fun prepareLogMessage(prefix: String, message: String): String {
        val date = this.simpleDateFormat.format(Calendar.getInstance().time)

        return "$date [${Thread.currentThread().name}] $prefix: $message"
    }

}
