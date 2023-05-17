package com.example.ttsapp

import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ttsapp.ui.theme.TTSAppTheme
import kotlinx.coroutines.delay
import java.util.Locale

class MainActivity : ComponentActivity(), TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    private var ttsInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(this, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (ttsInitialized) {
            tts.stop()
            tts.shutdown()
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            //Set boolean that TextToSpeech is initialized
            ttsInitialized = true
            //Choose Language
            //val result: Int = tts.setLanguage(Locale.US)
            //Check language is supported
//            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Log.i("autolog", "This language isn't supported: ");
//            } else {
            //Run speak(...) function with required parameters
            Handler().postDelayed({
                //Run speak(...) function with required parameters
                tts.speak("hello, this is text to speech", TextToSpeech.QUEUE_FLUSH, null, "speech")
            }, 3000) // add a delay of 3 seconds//            }
//        } else {
//            Log.i("autolog", "TTS initialization failed: ");
//        }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!ttsInitialized) {
            tts = TextToSpeech(this, this)
        }
    }

    override fun onPause() {
        super.onPause()
        if (ttsInitialized) {
            tts.stop()
            tts.shutdown()
        }
    }

    override fun onStop() {
        super.onStop()
        if (ttsInitialized) {
            tts.stop()
            tts.shutdown()
        }
    }

    override fun onRestart() {
        super.onRestart()
        if (!ttsInitialized) {
            tts = TextToSpeech(this, this)
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("ttsInitialized", ttsInitialized)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        ttsInitialized = savedInstanceState.getBoolean("ttsInitialized")
    }
}
