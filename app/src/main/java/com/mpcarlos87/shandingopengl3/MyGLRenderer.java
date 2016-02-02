package com.mpcarlos87.shandingopengl3;

import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import com.mpcarlos87.shandingopengl3.Geometry.BaseGeometry;
import com.mpcarlos87.shandingopengl3.Geometry.Box;
import com.mpcarlos87.shandingopengl3.Geometry.CustomMesh;
import com.mpcarlos87.shandingopengl3.Geometry.Pyramid;
import com.mpcarlos87.shandingopengl3.Geometry.Triangle;
import com.mpcarlos87.shandingopengl3.Loaders.ObjLoader;

import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Carlos on 22/02/2015.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {
    private Triangle mTriangle;
    private Pyramid mPyramid;
    private Box mBox;
    private BaseGeometry mGeometry;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    // Rotation Matrices
    private float[] mRotationMatrix = new float[16];
    private float[] mRotationMatrixX = new float[16];
    private float[] mRotationMatrixY = new float[16];

    // Rotation angles
    private float mAngleY,mAngleX;
    private float mDistance;

    private final int NEAR_PLANE = 1, FAR_PLANE = 40;

    private CustomMesh _mesh;
    public MyGLRenderer(InputStream inputStream){
        _mesh = ObjLoader.ObjLoader(inputStream);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mDistance = -3.0f;
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES30.glEnable(GLES30.GL_DEPTH_TEST);
        GLES30.glDepthFunc(GLES30.GL_LESS);
        // initialize the geometry
        //mTriangle = new Triangle();
        //mPyramid = new Pyramid();
        //mBox = new Box();
        mGeometry = new BaseGeometry(_mesh.Vertices,_mesh.Faces,_mesh.Colors);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, NEAR_PLANE, FAR_PLANE);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        float[] scratch = new float[16];
        // Redraw background color
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0,mDistance, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        // Create a rotation transformation for the triangle
        //long time = SystemClock.uptimeMillis() % 4000L;//TODO: TOUCH
        //mAngle = 0.090f * ((int) time);//TODO: TOUCH
        Matrix.setRotateM(mRotationMatrixY, 0, mAngleY, 0, 1.0f, 0);
        Matrix.setRotateM(mRotationMatrixX, 0, mAngleX, 1.0f, 0, 0);
        Matrix.multiplyMM(mRotationMatrix,0,mRotationMatrixX,0,mRotationMatrixY,0);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw triangle
        if(mTriangle!=null)
            mTriangle.draw(scratch);
        // Draw pyramid
        if(mPyramid!=null)
            mPyramid.draw(scratch);
        // Draw Box
        if(mBox!=null)
            mBox.draw(scratch);
        if(mGeometry!=null)
            mGeometry.draw(scratch);
    }

    public static int loadShader(int type, String shaderCode)
    {
        int shader = GLES30.glCreateShader(type);
        // add the source code to the shader and compile it
        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);
        return shader;
    }

    public float getAngleY() {
        return mAngleY;
    }

    public void setAngleY(float angle) {
        mAngleY = angle;
    }

    public float getAngleX() {
        return mAngleX;
    }

    public void setAngleX(float angle) {
        mAngleX = angle;
    }

    public void setDistance(double distance){
        mDistance -= distance;
    }
}
