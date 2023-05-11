package com.enesgunumdogdu.catchthebirdie

import android.app.AlertDialog
import android.content.DialogInterface
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.enesgunumdogdu.catchthebirdie.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var score = 0
    var highestScore = 0
    var imageArray = ArrayList<ImageView>()
    var runnable = Runnable {}
    var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mediaPlayer = MediaPlayer.create(this, R.raw.birdie_music)
        mediaPlayer.start()
        mediaPlayer.isLooping = true

        //ImageArray Oluşturma
        imageArray.add(binding.imageView)
        imageArray.add(binding.imageView2)
        imageArray.add(binding.imageView3)
        imageArray.add(binding.imageView4)
        imageArray.add(binding.imageView5)
        imageArray.add(binding.imageView6)
        imageArray.add(binding.imageView7)
        imageArray.add(binding.imageView8)
        imageArray.add(binding.imageView9)
        imageArray.add(binding.imageView10)
        imageArray.add(binding.imageView11)
        imageArray.add(binding.imageView12)
        imageArray.add(binding.imageView13)
        imageArray.add(binding.imageView14)
        imageArray.add(binding.imageView15)
        imageArray.add(binding.imageView16)

        hideImages()

        //CountDownTimer
        object : CountDownTimer(10500, 1000) {
            override fun onTick(p0: Long) {
                binding.timeText.text = "Time: " + p0 / 1000
            }

            override fun onFinish() {
                binding.timeText.text = "Your time is up!"
                handler.removeCallbacks(runnable)
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }

                var alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Game Over")
                alert.setMessage("Play again?")
                alert.setPositiveButton("Yes",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        //oyunu baştan başlatalım
                        var intentFromMain = intent
                        finish()
                        startActivity(intentFromMain)
                    })
                alert.setNegativeButton("No",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        Toast.makeText(this@MainActivity, "Game Over!", Toast.LENGTH_LONG).show()
                    })
                alert.show()
            }
        }.start()

    }

    fun hideImages() {

        runnable = object : Runnable {
            override fun run() {
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }
                var random = Random
                var randomIndex = random.nextInt(9)//0-8
                imageArray[randomIndex].visibility = View.VISIBLE

                handler.postDelayed(runnable, 500)
            }
        }

        handler.post(runnable)


    }

    fun increaseScore(view: View) {
        score = score + 1
        binding.scoreText.text = "Score: " + score
    }
}


