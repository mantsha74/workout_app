package com.example.a7minutesworkout

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a7minutesworkout.databinding.ActivityMainBinding
import kotlinx.coroutines.NonCancellable.start

class MainActivity : AppCompatActivity() {
    private var btnStop: TextView? = null
    private var btnStart: TextView? = null
    private var tvTimer: TextView? = null
    private var btnPause: TextView? = null

    //variable for timer which will be initialized later
    private var countDownTimer: CountDownTimer? = null

    //the duration of the timer in milliseconds
    private var timerDuration: Long = 60000

    //pause offset = timerDuration- time left
    private var pauseOffset: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvTimer?.text = "${(timerDuration / 1000).toString()}"
        btnStart?.setOnClickListener {
            startTimer(pauseOffset)
        }

        btnPause?.setOnClickListener {
            pauseTimer()
        }

        btnStop?.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer(pauseOffsetL: Long) {

        countDownTimer = object : CountDownTimer(
            timerDuration - pauseOffsetL, 1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                pauseOffset = timerDuration - millisUntilFinished
                tvTimer?.text =
                    (millisUntilFinished / 1000).toString() //current progress is set tko TextView
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Timer is finished", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
    }

    private fun resetTimer() {

        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            tvTimer?.text = "${(timerDuration / 1000).toString()}"
            countDownTimer = null
            pauseOffset = 0
        }
    }


}
