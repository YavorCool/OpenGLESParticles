package com.example.qwe.openglforandroidbook.objects;

import android.opengl.Matrix;

import com.example.qwe.openglforandroidbook.util.Geometry;

import java.util.Random;

/**
 * Created by qwe on 11.05.2016.
 */
public class ParticleShooter {
    private final Geometry.Point position;
    private final int color;

    private final float angleVariance;



    private final float speedVariance;

    private final Random random = new Random();

    private float[] rotationMatrix = new float[16];
    private float[] directionVector = new float [4];
    private float[] resultVector = new float[4];

    public ParticleShooter(float speedVariance, float angleVarianceInDegrees, int color, Geometry.Vector direction, Geometry.Point position) {
        this.speedVariance = speedVariance;
        this.angleVariance = angleVarianceInDegrees;
        this.color = color;

        this.directionVector[0] = direction.x;
        this.directionVector[1] = direction.y;
        this.directionVector[2] = direction.z;

        this.position = position;


    }

    public void addParticles(ParticleSystem particleSystem, float currentTime, int count) {
        for(int i = 0; i < count; i++) {
            Matrix.setRotateEulerM(rotationMatrix, 0,
                    (random.nextFloat() - 0.5f) * angleVariance,
                    (random.nextFloat() - 0.5f) * angleVariance,
                    (random.nextFloat() - 0.5f) * angleVariance);

            Matrix.multiplyMV(resultVector, 0, rotationMatrix, 0, directionVector, 0);

            float speedAdjustment = 1f + random.nextFloat() * speedVariance;

            Geometry.Vector thisDirection = new Geometry.Vector(
                    resultVector[0] * speedAdjustment,
                    resultVector[1] * speedAdjustment,
                    resultVector[2] * speedAdjustment
                    );

            particleSystem.addParticle(position, color, thisDirection, currentTime);
        }
    }


}
