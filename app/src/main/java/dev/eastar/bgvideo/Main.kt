package dev.eastar.bgvideo

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


class Main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)


        findViewById<VideoView>(R.id.port).apply {
            setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.sample_mp4_file))
            setOnPreparedListener {
                it.setVolume(0F, 0F)
                it.isLooping = true
            }
            start()
        }

        findViewById<VideoView>(R.id.land).apply {
            setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.sample_mp4_file))
            start()
        }

        findViewById<VideoView>(R.id.port2).apply {
            setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.sample_mp4_file))
            start()
        }

        findViewById<VideoView>(R.id.land2).apply {
            setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.sample_mp4_file))
            start()
        }

        playVideo()
        //val context = this
        //var mediaPlayer = MediaPlayer.create(context, R.raw.sample_mp4_file)
        //mediaPlayer.start() // no need to call prepare(); create() does that for you
    }

    // playVideo /////////////////////////////////////////////////////////////////////////////
    private fun playVideo() {
        findViewById<SurfaceView>(R.id.port3).holder.addCallback(object : SurfaceHolder.Callback {
            lateinit var player: MediaPlayer
            override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
//                Log.e("~~surfaceCreated")
                player = MediaPlayer.create(this@Main, R.raw.sample_mp4_file).apply {
                    setDisplay(surfaceHolder)
                    setVolume(0F, 0F)
                    setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING)
                    isLooping = true
                    start()
                }

                player.setOnInfoListener { a, b, c ->
                    android.util.Log.e("t", "$b,$c")
                    false
                }
                //lifecycle.addObserver(object : LifecycleObserver {
                //    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                //    fun onDestroy() = player.release()
                //
                //    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
                //    fun onResume() = player.start()
                //
                //    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
                //    fun onPause() = player.pause()
                //})
            }

            override fun surfaceChanged(surfaceHolder: SurfaceHolder, p1: Int, p2: Int, p3: Int) {}
            override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {}
        })
    }
}