package com.mpcarlos87.shandingopengl3.Geometry;

/**
 * Created by Carlos on 05/07/2015.
 */
public class Box extends BaseGeometry{
    static final int[] boxFaceIndices =
            {
                    0, 1, 2,
                    2, 3, 0,
                    // top
                    1, 5, 6,
                    6, 2, 1,
                    // back
                    7, 6, 5,
                    5, 4, 7,
                    // bottom
                    4, 0, 3,
                    3, 7, 4,
                    // left
                    4, 5, 1,
                    1, 0, 4,
                    // right
                    3, 2, 6,
                    6, 7, 3,
            };

    static float boxCoords[] = {
            // front
            -0.5f, -0.5f,  0.5f,
            0.5f, -0.5f,  0.5f,
            0.5f,  0.5f,  0.5f,
            -0.5f,  0.5f,  0.5f,
            // back
            -0.5f, -0.5f,  -0.5f,
            0.5f, -0.5f,  -0.5f,
            0.5f,  0.5f,  -0.5f,
            -0.5f,  0.5f,  -0.5f
    };

    // Set color with red, green, blue and alpha (opacity) values
    static float boxColors[] = {
            // front colors
            1.0f, 0.0f, 0.0f,1.0f,
            0.0f, 1.0f, 0.0f,1.0f,
            0.0f, 0.0f, 1.0f,1.0f,
            1.0f, 1.0f, 1.0f,1.0f,
            // back colors
            1.0f, 0.0f, 0.0f,1.0f,
            0.0f, 1.0f, 0.0f,1.0f,
            0.0f, 0.0f, 1.0f,1.0f,
            1.0f, 1.0f, 1.0f,1.0f,
    };

    public Box(){
        super(boxCoords, boxFaceIndices, boxColors);
    }
}
