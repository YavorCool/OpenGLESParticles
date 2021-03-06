package com.example.qwe.openglforandroidbook.util;

import android.util.Log;

import java.util.logging.Logger;

import static android.opengl.GLES20.*;
/**
 * Created by qwe on 24.04.2016.
 */
public class ShaderHelper {
    private static final String TAG = "ShaderHelper";



    public static int compileVertexShader(String shaderCode){
        return compileShader(GL_VERTEX_SHADER, shaderCode);
    }


    public static int compileFragmentShader(String shaderCode){
        return compileShader(GL_FRAGMENT_SHADER, shaderCode);
    }


    public static int compileShader(int type, String shaderCode){
        final int shaderObjectId = glCreateShader(type);

        if(shaderObjectId == 0){
            if(LoggerConfig.ON){
                Log.w(TAG, "Couldn't create a new shader");
            }
            return 0;
        }

        glShaderSource(shaderObjectId, shaderCode);
        glCompileShader(shaderObjectId);

        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);

        if(LoggerConfig.ON){
            Log.v(TAG, "Results of compiling source: " + "\n" + shaderCode + "\n:"
            + glGetShaderInfoLog(shaderObjectId));
        }

        if(compileStatus[0] == 0){
            glDeleteShader(shaderObjectId);

            if(LoggerConfig.ON){
                Log.w(TAG, "Compilation of shader failed");
            }

            return 0;
        }

        return shaderObjectId;
    }


    public static int linkProgram(int vertexShaderId, int fragmentShaderId){
        final int programObjectId = glCreateProgram();

        if(programObjectId == 0){
            if(LoggerConfig.ON){
                Log.w(TAG, "Couldn't create a new com.example.qwe.openglforandroidbook.program");
            }

            return 0;
        }

        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);

        glLinkProgram(programObjectId);

        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);

        if(LoggerConfig.ON){
            Log.v(TAG, "Results of linking com.example.qwe.openglforandroidbook.program");
        }

        if(linkStatus[0] == 0){
            glDeleteProgram(programObjectId);
            if(LoggerConfig.ON){
                Log.w(TAG, "Linking com.example.qwe.openglforandroidbook.program failed");
            }
            return 0;
        }

        return programObjectId;
    }


    public static boolean validateProgram(int programObjectId){
        glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);

        Log.v(TAG, "Results of validating com.example.qwe.openglforandroidbook.program: " + validateStatus[0] + "\nLog:" + glGetProgramInfoLog(programObjectId));

        return validateStatus[0]!=0;
    }

    public static int buildProgram(String vertexShaderSource, String fragmentShaderSource){
        int program;

        //Compile shaders
        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);

        //Linking them into a shader com.example.qwe.openglforandroidbook.program
        program = linkProgram(vertexShader, fragmentShader);

        if(LoggerConfig.ON){
            validateProgram(program);
        }

        return program;
    }
}
