package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.about

import android.content.Context
import android.opengl.*

import android.os.SystemClock
import javax.microedition.khronos.opengles.GL10


class OpenGlRenderer : GLSurfaceView.Renderer {
    private lateinit var textureCube: TextureCube
    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private var angleTriangle = 0.5f
    private val speedTriangle = 0.05f
    private val rotationMatrix = FloatArray(16)

    override fun onSurfaceCreated(gl: GL10?, config: javax.microedition.khronos.egl.EGLConfig?) {
        GLES31.glClearColor(0f, 1f, 1f, 0f)
        textureCube = TextureCube()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES31.glViewport(0, 0, width, height)
        val ratio = width.toFloat() / height

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1F, 1F, 3F, 7F)
    }

    override fun onDrawFrame(gl10: GL10?) {
        val scratch = FloatArray(16)
        GLES31.glClear(GLES31.GL_COLOR_BUFFER_BIT)
        val time = SystemClock.uptimeMillis() % 4000L
        val angle = 0.090f * time.toInt()
        Matrix.setRotateM(rotationMatrix, 0, angle, 1f, 0.2f, 0F)
        Matrix.setLookAtM(
            viewMatrix,
            0,
            0F,
            0F,
            -3F,
            0f,
            0f,
            0f,
            0f,
            1.0f,
            0.0f
        )

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
        Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0)
        textureCube.draw(scratch)
        angleTriangle += speedTriangle
    }

}
fun loadShader(type: Int, shaderCode: String?): Int {
    val shader = GLES31.glCreateShader(type)
    GLES31.glShaderSource(shader, shaderCode)
    GLES31.glCompileShader(shader)
    return shader
}