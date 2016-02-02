package com.mpcarlos87.shandingopengl3.Geometry;

import java.util.Random;

/**
 * Created by Carlos on 02/02/2016.
 */
public class CustomMesh {

    public float[] Vertices;
    public int[] Faces;
    public float[] Colors;

    public CustomMesh(int nVertices, int nFaces)
    {
        Vertices = new float[nVertices];
        Faces = new int[nFaces];
        Colors = new float[nVertices*4/3];

        Random rand = new Random();
        for(int i = 0; i < nVertices; i+=3){
            int colorIndex = i/3*4;
            float randFloat = rand.nextFloat();
            Colors[colorIndex] = randFloat;
            Colors[colorIndex+1] = randFloat;
            Colors[colorIndex+2] = randFloat;
            Colors[colorIndex+3] = 1.0f;
        }
    }
}
