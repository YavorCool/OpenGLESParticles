package com.example.qwe.openglforandroidbook.data;

import com.example.qwe.openglforandroidbook.Constants;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_ELEMENT_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_STATIC_DRAW;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glBufferData;
import static android.opengl.GLES20.glGenBuffers;

/**
 * Created by qwe on 06.06.2016.
 */
public class IndexBuffer {

    private final int bufferId;

    public IndexBuffer(short[] indexData) {
        final int buffers[] = new int[1];
        glGenBuffers(buffers.length, buffers, 0);

        if(buffers[0] == 0){
            throw new RuntimeException("Couldn't create a new index buffer object");
        }
        bufferId = buffers[0];

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffers[0]);

        ShortBuffer vertexArray = ByteBuffer
                .allocateDirect(indexData.length * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(indexData);

        vertexArray.position(0);

        glBufferData(GL_ELEMENT_ARRAY_BUFFER, vertexArray.capacity() * 2, vertexArray, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public int getBufferId() {
        return bufferId;
    }

}
