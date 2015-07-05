package com.mpcarlos87.shandingopengl3.Geometry;

/**
 * Created by Carlos on 05/07/2015.
 */
public class Box extends BaseGeometry{
    static final short[] boxFaceIndices =
            {
                    0,2,1,
                    0,3,2,
                    0,1,5,
                    0,5,4,
                    1,2,5,
                    2,6,5,
                    2,6,3,
                    3,6,7,
                    0,3,4,
                    3,7,4,
                    4,5,6,
                    4,6,7
            };

    static float boxCoords[] = {   // in counterclockwise order:
            0.5f,  -0.5f, -0.5f, // bottom Left Front
            -0.5f,  -0.5f, -0.5f, // bottom Right Front
            -0.5f,  -0.5f, 0.5f, // bottom Right Back
            0.5f,  -0.5f, 0.5f, // bottom Left Back
            0.5f,  0.5f, -0.5f, // top Left Front
            -0.5f,  0.5f, -0.5f, // top Right Front
            -0.5f,  0.5f, 0.5f, // top Right Back
            0.5f,  0.5f, 0.5f, // top Left Back
    };

    // Set color with red, green, blue and alpha (opacity) values
    static float boxColors[] = {
            0.0f,1.0f,0.0f,1.0f,
            1.0f,0.0f,0.0f,1.0f,
            0.0f,0.0f,1.0f,1.0f,
            1.0f,1.0f,0.0f,1.0f,
            0.0f,1.0f,1.0f,1.0f,
            0.0f,0.5f,1.0f,1.0f,
            0.0f,1.0f,0.5f,1.0f,
            0.5f,1.0f,0.0f,1.0f
    };

    public Box(){
        super(boxCoords, boxFaceIndices, boxColors);
    }
}
