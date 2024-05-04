package com.example.a7minutesworkout

object Constants {

    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()
        val jumpingJacks =  ExerciseModel(
            1,"jumping jacks",
            R.drawable.ic_jumping,
            false,
            false
        )
        exerciseList.add(jumpingJacks )

        val sidePLank  = ExerciseModel(2,"Side Plank",R.drawable.ic_straitchng,false,false)
        exerciseList.add(sidePLank)

        val lunges = ExerciseModel(3,"Lunges", R.drawable.ic_jumping,false,false)
        exerciseList.add(lunges)

        return exerciseList
    }

}