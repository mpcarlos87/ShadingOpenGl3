package com.mpcarlos87.shandingopengl3.Geometry;

import android.opengl.GLES30;

import com.mpcarlos87.shandingopengl3.MyGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Carlos on 22/02/2015.
 */
public class Pyramid extends BaseGeometry {

    static final int[] pyramidFaceIndices =
            {
                    1,0,2,
                    2,0,3,
                    3,0,4,
                    4,0,1,
                    3,4,2,
                    1,2,4
            };

    static float pyramidCoords[] = {   // in counterclockwise order:
            0.0f,  0.6f, 0.0f, // top
            0.5f, -0.3f, 0.5f, // bottom left
            -0.5f, -0.3f, 0.5f,  // bottom right
            -0.5f, -0.3f, -0.5f,  // top right
            0.5f, -0.3f, -0.5f // top left
    };

    // Set color with red, green, blue and alpha (opacity) values
    static float pyramidColors[] = {
            1.0f,1.0f,1.0f,1.0f,
            1.0f,0.0f,0.0f,1.0f,
            0.0f,0.0f,1.0f,1.0f,
            1.0f,1.0f,0.0f,1.0f,
            0.0f,1.0f,0.0f,1.0f};

    public Pyramid() {
        super(pyramidCoords, pyramidFaceIndices, pyramidColors);
    }
}
