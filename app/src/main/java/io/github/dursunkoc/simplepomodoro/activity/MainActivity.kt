package io.github.dursunkoc.simplepomodoro.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import io.github.dursunkoc.simplepomodoro.Constant
import io.github.dursunkoc.simplepomodoro.Constant.TAG
import io.github.dursunkoc.simplepomodoro.R
import io.github.dursunkoc.simplepomodoro.databinding.ActivityMainBinding
import io.github.dursunkoc.simplepomodoro.statemachine.State
import io.github.dursunkoc.simplepomodoro.utils.TimeUtils

class MainActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var countdownTimer: CountDownTimer
    private var isRunning = false;
    private var timeLeft = Constant.DEFAULT_WORK_DURATION_MS
    private lateinit var tvTimer: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button
    private var state:State=State.Pomodoro(1,this)
    private lateinit var ivPomodoroState:ImageView
    private lateinit var tvPomodoroCounter:TextView
    private lateinit var tvPomodoroTotal:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        tvTimer = binding.tvTimer
        btnStart = binding.btnStart
        btnStop = binding.btnStop
        ivPomodoroState = binding.ivPomodoroState
        tvPomodoroCounter = binding.tvPomodoroCounter
        tvPomodoroTotal = binding.tvPomodoroTotal

        tvPomodoroTotal.text = getString(R.string.lblTotalPomodoro, TimeUtils.formatMillisecondsMinutesSeconds(0L));
        tvPomodoroCounter.text = getString(R.string.lblPomodoroCounter, 1);
        updateTimerText(Constant.DEFAULT_WORK_DURATION_MS)


        btnStart.setOnClickListener {
            if (isRunning){
                pauseTimer();
            }else{
                startTimer(timeLeft, 10);
            }
        }

        btnStop.setOnClickListener {
            //TODO reset all!
        }
        setContentView(binding.root)
    }

    private fun startTimer(duration:Long, refreshPeriod:Long){
        countdownTimer = object: CountDownTimer(duration, refreshPeriod){
            override fun onTick(millisUntilFinished: Long) {
                updateTimerText(millisUntilFinished)
            }

            override fun onFinish() {
                timeUp()
            }

        }.start();
        isRunning = true;
        btnStart.text = resources.getText(R.string.btnPause)
    }

    private fun pauseTimer(){
        countdownTimer.cancel();
        isRunning = false;
        btnStart.text = resources.getText(R.string.btnStart)
    }

    private fun timeUp(){
        this.state = state.next()
        pauseTimer();
    }

    fun updateTimerText(millisUntilFinished:Long){
        timeLeft = millisUntilFinished;
        tvTimer.text = TimeUtils.formatMilliseconds(timeLeft)
    }


    fun pomodoroActivate(sessionCounter: Int) {
        Log.e(TAG, "Pomodoro activated")
        ivPomodoroState.setImageResource(R.drawable.ic_pomodoro_active)
        tvPomodoroCounter.text = getString(R.string.lblPomodoroCounter, sessionCounter);
        updateTimerText(Constant.DEFAULT_WORK_DURATION_MS)

    }
    fun pomodoroDeactivate(sessionDuration: Long) {
        Log.e(TAG, "Pomodoro deactivated")
        tvPomodoroTotal.text = getString(R.string.lblTotalPomodoro, TimeUtils.formatMillisecondsMinutesSeconds(sessionDuration))
    }
    fun shortBreakActivate(){
        Log.e(TAG, "ShortBreak activated")
        ivPomodoroState.setImageResource(R.drawable.ic_short_break_active)
        updateTimerText(Constant.DEFAULT_SHORT_BREAK_DURATION_MS)
    }
    fun shortBreakDeactivate(){
        Log.e(TAG, "ShortBreak deactivated")
    }
    fun longBreakActivate(){
        Log.e(TAG, "LongBreak activated")
        ivPomodoroState.setImageResource(R.drawable.ic_long_break_active)
        updateTimerText(Constant.DEFAULT_LONG_BREAK_DURATION_MS)
    }
    fun longBreakDeactivate(){
        Log.e(TAG, "LongBreak deactivated")
    }


}