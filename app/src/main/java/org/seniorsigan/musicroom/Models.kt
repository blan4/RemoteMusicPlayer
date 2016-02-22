package org.seniorsigan.musicroom

import java.io.Serializable

data class AudioPlayedMessage(val title: String): Serializable

data class AudioInfo(
        val artist: String?,
        val title: String?,
        val url: String,
        val album: String?,
        val picture: ByteArray?

): Serializable {
    val name = "$artist - $title"
}