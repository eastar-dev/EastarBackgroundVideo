package dev.eastar.bgvideo

import android.media.MediaPlayer
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


class Main : AppCompatActivity() {
    var player: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        playVideo()
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() = player?.release()

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() = player?.start()

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause() = player?.pause()
        })
    }


    // playVideo /////////////////////////////////////////////////////////////////////////////
    private fun playVideo() {
        findViewById<SurfaceView>(R.id.port3).holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
                android.util.Log.e(">>>>>>>>>>>>>>>>", "surfaceCreated")
                player = MediaPlayer.create(this@Main, R.raw.sample_mp4_file).apply {
                    setDisplay(surfaceHolder)
                    setVolume(0F, 0F)
                    setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
                    isLooping = true
                    prepare()
                    start()
                }

                player?.setOnInfoListener { player, what, extra ->
                    android.util.Log.e(">>>>>>>>>>>>>>>>", "$what,$extra")
                    false
                }

            }

            override fun surfaceChanged(surfaceHolder: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
                android.util.Log.e(">>>>>>>>>>>>>>>>", "surfaceChanged")
            }
            override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
                android.util.Log.e(">>>>>>>>>>>>>>>>", "surfaceDestroyed")
            }
        })
    }
}