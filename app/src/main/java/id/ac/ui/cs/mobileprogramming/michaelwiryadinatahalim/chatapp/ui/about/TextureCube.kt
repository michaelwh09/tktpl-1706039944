package id.ac.ui.cs.mobileprogramming.michaelwiryadinatahalim.chatapp.ui.about

import android.opengl.GLES31

import java.nio.ByteOrder

import java.nio.ByteBuffer

import java.nio.ShortBuffer

import java.nio.FloatBuffer




class TextureCube {
    private val vertexBuffer: FloatBuffer
    private val indexBuffer: ShortBuffer
    private var colorHandle = 0
    private var vPMatrixHandle = 0
    val color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 0.2f)
    private val vertices = floatArrayOf(
        -0.2f, -0.2f, 0.2f,  // 0. left-bottom-front
        0.2f, -0.2f, 0.2f,  // 1. right-bottom-front
        -0.2f, 0.2f, 0.2f,  // 2. left-top-front
        0.2f, 0.2f, 0.2f,  // 3. right-top-front
        // BACK
        0.2f, -0.2f, -0.2f,  // 6. right-bottom-back
        -0.2f, -0.2f, -0.2f,  // 4. left-bottom-back
        0.2f, 0.2f, -0.2f,  // 7. right-top-back
        -0.2f, 0.2f, -0.2f,  // 5. left-top-back
        // LEFT
        -0.2f, -0.2f, -0.2f,  // 4. left-bottom-back
        -0.2f, -0.2f, 0.2f,  // 0. left-bottom-front
        -0.2f, 0.2f, -0.2f,  // 5. left-top-back
        -0.2f, 0.2f, 0.2f,  // 2. left-top-front
        // RIGHT
        0.2f, -0.2f, 0.2f,  // 1. right-bottom-front
        0.2f, -0.2f, -0.2f,  // 6. right-bottom-back
        0.2f, 0.2f, 0.2f,  // 3. right-top-front
        0.2f, 0.2f, -0.2f,  // 7. right-top-back
        // TOP
        -0.2f, 0.2f, 0.2f,  // 2. left-top-front
        0.2f, 0.2f, 0.2f,  // 3. right-top-front
        -0.2f, 0.2f, -0.2f,  // 5. left-top-back
        0.2f, 0.2f, -0.2f,  // 7. right-top-back
        // BOTTOM
        -0.2f, -0.2f, -0.2f,  // 4. left-bottom-back
        0.2f, -0.2f, -0.2f,  // 6. right-bottom-back
        -0.2f, -0.2f, 0.2f,  // 0. left-bottom-front
        0.2f, -0.2f, 0.2f // 1. right-bottom-front
    )

    private val vertexShaderCode =  // the coordinates of the objects that use this vertex shader
        "uniform mat4 uMVPMatrix;" +
                "attribute vec4 vPosition;" +
                "void main() {" +
                "  gl_Position = uMVPMatrix * vPosition;" +
                "}"


    private val fragmentShaderCode = ("precision mediump float;" +
            "uniform vec4 vColor;" +
            "void main() {" +
            "  gl_FragColor = vColor;" +
            "}")
    private var positionHandle = 0
    private val vertexStride = COORDS_PER_VERTEX * 4 // 4 bytes per vertex

    private var program = 0
    private val colors = arrayOf(
        floatArrayOf(1.0f, 0.5f, 0.0f, 1.0f),
        floatArrayOf(1.0f, 0.0f, 1.0f, 1.0f),
        floatArrayOf(0.0f, 1.0f, 0.0f, 1.0f),
        floatArrayOf(0.0f, 0.0f, 1.0f, 1.0f),
        floatArrayOf(1.0f, 0.0f, 0.0f, 1.0f),
        floatArrayOf(1.0f, 1.0f, 0.0f, 1.0f)
    )


    private val indices = shortArrayOf(
        0,
        1,
        2,
        2,
        1,
        3,
        5,
        4,
        7,
        7,
        4,
        6,
        8,
        9,
        10,
        10,
        9,
        11,
        12,
        13,
        14,
        14,
        13,
        15,
        16,
        17,
        18,
        18,
        17,
        19,
        22,
        23,
        20,
        20,
        23,
        21
    )
    init {
        val vbb = ByteBuffer.allocateDirect(vertices.size * 4)
        vbb.order(ByteOrder.nativeOrder()) // Use native byte order
        vertexBuffer = vbb.asFloatBuffer() // Convert from byte to float
        vertexBuffer.put(vertices) // Copy data into buffer
        vertexBuffer.position(0) // Rewind
        indexBuffer = ByteBuffer.allocateDirect(indices.size * 2).order(ByteOrder.nativeOrder()).asShortBuffer()
        indexBuffer.put(indices).position(0)
        val vertexShader: Int = loadShader(GLES31.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader: Int = loadShader(GLES31.GL_FRAGMENT_SHADER, fragmentShaderCode)
        program = GLES31.glCreateProgram()
        GLES31.glAttachShader(program, vertexShader)
        GLES31.glAttachShader(program, fragmentShader)
        GLES31.glLinkProgram(program)
    }


    fun draw(mvpMatrix: FloatArray?) {
        GLES31.glUseProgram(program)

        // get handle to vertex shader's vPosition member
        positionHandle = GLES31.glGetAttribLocation(program, "vPosition")

        // Enable a handle to the triangle vertices
        GLES31.glEnableVertexAttribArray(positionHandle)

        // Prepare the triangle coordinate data
        GLES31.glVertexAttribPointer(
            positionHandle, COORDS_PER_VERTEX,
            GLES31.GL_FLOAT, false,
            vertexStride, vertexBuffer
        )
        vPMatrixHandle = GLES31.glGetUniformLocation(program, "uMVPMatrix")
        colorHandle = GLES31.glGetUniformLocation(program, "vColor")

        // Pass the projection and view transformation to the shader
        GLES31.glUniformMatrix4fv(vPMatrixHandle, 1, false, mvpMatrix, 0)
        for (face in 0 until numFaces) {
            // Set the color for each of the faces
            GLES31.glUniform4fv(colorHandle, 1, colors[face], 0)
            indexBuffer.position(face * 6)
            GLES31.glDrawElements(GLES31.GL_TRIANGLES, 6, GLES31.GL_UNSIGNED_SHORT, indexBuffer)
        }

        // Disable vertex array
        GLES31.glDisableVertexAttribArray(positionHandle)
    }

    companion object {
        private const val COORDS_PER_VERTEX: Int = 3
        private const val numFaces = 6
    }
}
