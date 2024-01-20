package io.github.dursunkoc.simplepomodoro.utils

import java.time.Duration

object TimeUtils {
    fun formatMilliseconds(milliseconds: Long): String {
        val duration = Duration.ofMillis(milliseconds)
        val minutes = duration.toMinutes()
        val seconds = duration.minusMinutes(minutes).seconds
        val millis = duration.minusMinutes(minutes).minusSeconds(seconds).toMillis()/100
        val minutesText = makeTwoDigitWithLeadingZero(minutes);
        val secondsText = makeTwoDigitWithLeadingZero(seconds);
        val millisText = makeTwoDigitWithLeadingZero(millis);
        return "$minutesText:$secondsText.$millisText"
    }
    fun formatMillisecondsMinutesSeconds(milliseconds: Long): String {
        val duration = Duration.ofMillis(milliseconds)
        val minutes = duration.toMinutes()
        val seconds = duration.minusMinutes(minutes).seconds
        val minutesText = makeTwoDigitWithLeadingZero(minutes);
        val secondsText = makeTwoDigitWithLeadingZero(seconds);
        return "$minutesText:$secondsText"
    }
    private fun makeTwoDigitWithLeadingZero(input: Long) = input.toString().padStart(2,'0')
}