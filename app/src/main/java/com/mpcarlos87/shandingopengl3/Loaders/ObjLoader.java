package com.mpcarlos87.shandingopengl3.Loaders;

import android.content.Context;

import com.mpcarlos87.shandingopengl3.Geometry.CustomMesh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Carlos on 02/02/2016.
 */
public class ObjLoader{

    public static CustomMesh ObjLoader(InputStream inputStream){

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();
        List<Float> allVertices = new ArrayList<>();
        List<Integer> allFaces = new ArrayList<>();

        try {
            while (( line = buffreader.readLine()) != null) {
                if(line.length() > 2){
                    String lineType = line.substring(0,2);
                    if(lineType.equals("v "))
                        allVertices.addAll(ReadVertex(line));
                    if( lineType.equals("f "))
                        allFaces.addAll(ReadFace(line));
                }
            }
        } catch (IOException e) {
            return null;
        }

        CustomMesh mesh = new CustomMesh(allVertices.size(),allFaces.size());
        for(int i = 0;i < allVertices.size(); i++)
            mesh.Vertices[i] = allVertices.get(i);
        for(int i = 0;i < allFaces.size(); i++)
            mesh.Faces[i] = allFaces.get(i)- 1;

        //Create colors

        return mesh;
    }

    private static Collection<? extends Integer> ReadFace(String line) {

        List<Integer> faces = new ArrayList<>(3);
        String lineContent = line.substring(2,line.length());
        String[] separated = lineContent.split(" ");
        for(int i = 0; i < separated.length; i++)
            faces.add(Integer.parseInt(separated[i]));

        return faces;
    }

    private static Collection<? extends Float> ReadVertex(String line) {
        List<Float> vertices = new ArrayList<>(3);
        String lineContent = line.substring(2,line.length());
        String[] separated = lineContent.split(" ");

        for(int i = 0; i < separated.length; i++)
            vertices.add(Float.parseFloat(separated[i]));

        return vertices;
    }
}
