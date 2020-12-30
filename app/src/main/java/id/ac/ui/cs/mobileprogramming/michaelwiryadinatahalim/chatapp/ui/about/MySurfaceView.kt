package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.about

import android.content.Context
import android.opengl.GLSurfaceView

class MySurfaceView(context: Context?) : GLSurfaceView(context) {
    private val renderer: OpenGlRenderer

    init {
        setEGLContextClientVersion(3)

        renderer = OpenGlRenderer()
        setRenderer(renderer)
    }
}
