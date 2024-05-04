package com.example.a7minutesworkout

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {
    private var binding:ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercsiePosition = -1

    private var exerciseAdapter : ExerciseStatusAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if(supportActionBar !=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        exerciseList = Constants.defaultExerciseList()

       // add back btn
        binding?.toolbarExercise?.setNavigationOnClickListener{
            onBackPressed()
        }

        setupRestView()
        setupExerciseStatusRecyclerView()
    }


    private fun setupExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)


        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setupRestView(){
        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility =View.VISIBLE
        binding?.tvExerciseName?.visibility= View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImageView?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE



        if(restTimer !=null){
            restTimer?.cancel()
            restProgress = 0
        }

        binding?.tvUpcomingExerciseName?.text =
            exerciseList!![currentExercsiePosition +1].getName()
        setRestProgressBar()
    }

    private fun  setupExerciseView(){
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility =View.INVISIBLE
        binding?.tvExerciseName?.visibility= View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImageView?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE

        if (exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        binding?.ivImageView?.setImageResource(exerciseList!![currentExercsiePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercsiePosition].getName()

        setExerciseProgressBar()

    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        restTimer = object: CountDownTimer(10000,1000){
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text =(10-restProgress).toString()
            }

            override fun onFinish() {

                 currentExercsiePosition++
                exerciseList!![currentExercsiePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setupExerciseView()
            }
        }.start()
    }


    private fun setExerciseProgressBar(){
        binding?.progressBarExercise?.progress = exerciseProgress

        exerciseTimer = object: CountDownTimer(30000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress = 30 - exerciseProgress
                binding?.tvTimerExercise?.text =(30 - exerciseProgress).toString()
            }

            override fun onFinish() {

                exerciseList!![currentExercsiePosition].setIsCompleted(true)

                exerciseList!![currentExercsiePosition].setIsSelected(false)
                exerciseAdapter!!.notifyDataSetChanged()

                if(currentExercsiePosition <exerciseList?.size!! -1){
                    setupRestView()

                }else{
                    Toast.makeText(this@ExerciseActivity,
                        "Congratulations! you have completed the 7 minutes workout.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (restTimer!=null){
            restTimer?.cancel()
            restProgress = 0
        }

        if (exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        binding= null
    }
}