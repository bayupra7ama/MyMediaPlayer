package com.example.myapplication


import android.media.AudioAttributes
import android.media.MediaPlayer

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var mMediaPlayer : MediaPlayer? = null
    private var isReady : Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        init()

        val btnPlay = findViewById<Button>(R.id.btn_play)
        val btnStop = findViewById<Button>(R.id.btn_stop)

        btnPlay.setOnClickListener {
            if (!isReady){
                mMediaPlayer?.prepareAsync()
            }
            else{

                if(mMediaPlayer?.isPlaying as Boolean){
                    mMediaPlayer?.pause()
                }else{
                    mMediaPlayer?.start()
                }
            }
        }
        btnStop.setOnClickListener {
            if (mMediaPlayer?.isPlaying as Boolean || isReady) {
                mMediaPlayer?.stop()
                isReady = false
            }
        }

    }

    private fun init(){
//      //memperbarui media player
        mMediaPlayer = MediaPlayer()
        val attribute  = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        mMediaPlayer?.setAudioAttributes(attribute)
//      //memperbarui media player

        //      //mengambil suara dari file raw
        val afd = applicationContext.resources.openRawResourceFd(R.raw.marry_on_a_cross)
        try {
            mMediaPlayer?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
        }catch (e : IOException){
            e.printStackTrace()
        }

        //      //mengambil suara dari file raw



        mMediaPlayer?.setOnPreparedListener {
            isReady = true
            mMediaPlayer?.start()
        }
        
        mMediaPlayer?.setOnErrorListener { mp, what, extra ->
            false
        }
    }
}