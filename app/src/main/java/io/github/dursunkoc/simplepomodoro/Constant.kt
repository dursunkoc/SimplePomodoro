package io.github.dursunkoc.simplepomodoro

object Constant {
    val TAG = "POM";

    //val DEFAULT_WORK_DURATION_MS = 25*60*MILLIS_IN_SECOND;
    private const val MILLIS_IN_SECOND = 1000L;
    private const val SECONDS_IN_MINUTES = 60L;
    const val DEFAULT_WORK_DURATION_MS = 25 * SECONDS_IN_MINUTES * MILLIS_IN_SECOND;
    const val DEFAULT_LONG_BREAK_DURATION_MS = 15 * SECONDS_IN_MINUTES * MILLIS_IN_SECOND;
    const val DEFAULT_SHORT_BREAK_DURATION_MS = 5 * SECONDS_IN_MINUTES * MILLIS_IN_SECOND;
}