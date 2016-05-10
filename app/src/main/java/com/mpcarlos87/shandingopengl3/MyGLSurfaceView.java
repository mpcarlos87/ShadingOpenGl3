package com.mpcarlos87.shandingopengl3;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Carlos on 22/02/2015.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX,mPreviousY;
    private double mDistance;
    private double mDiagonal;
    private MotionEvent.PointerCoords mFinger1,mFinger2;
    private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);
        // Create an OpenGL ES 3.0 context
        setEGLContextClientVersion(3);

        //Read from the resources the file
        InputStream inputStream= null;
        try {
            inputStream = getResources().getAssets().open("dragon_obj");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRenderer = new MyGLRenderer(inputStream);
        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY); //TODO: TOUCH

        DisplayMetrics dm = this.getContext().getResources().getDisplayMetrics();
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        mDiagonal = Math.sqrt(Math.pow(width,2)+Math.pow(height,2));
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        // Get the index of the pointer associated with the action.
        int index = e.getActionMasked();

        Log.d("DEBUG_ACTIONS","The action is " + actionToString(index));

        if (e.getPointerCount() > 1) {
            Log.d("DEBUG_ACTIONS","Multitouch event");
        } else {
            // Single touch event
            Log.d("DEBUG_ACTIONS","Single touch event");
        }

        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_MOVE:
                //Single point
                if(e.getPointerCount()== 1){
                    float dx = x - mPreviousX;
                    float dy = y - mPreviousY;

                    mRenderer.setAngleY(mRenderer.getAngleY() + (dx * TOUCH_SCALE_FACTOR));
                    mRenderer.setAngleX(mRenderer.getAngleX() + (dy * TOUCH_SCALE_FACTOR));
                    requestRender();
                }
                //Multiple point
                else if(e.getPointerCount()== 2){

                    MotionEvent.PointerCoords newFinger1= new MotionEvent.PointerCoords(),newFinger2= new MotionEvent.PointerCoords();
                    e.getPointerCoords(0,newFinger1);
                    e.getPointerCoords(1, newFinger2);
                    double newDistance = ComputeDistance(newFinger1, newFinger2);

                    double coefficient = ((newDistance-mDistance)/mDiagonal)*100;
                    mRenderer.setDistance(coefficient);
                    Log.d("DEBUG_ACTIONS", String.format("Coefficient: %f",coefficient));
                    mDistance = newDistance;
                    requestRender();
                }
                break;

            // Case finger 1 down
            case MotionEvent.ACTION_DOWN:
                break;

            // Case finger 1 up
            case MotionEvent.ACTION_UP:
                break;

            // Case finger 2 or more down
            case MotionEvent.ACTION_POINTER_DOWN:
                mFinger1 = new MotionEvent.PointerCoords();
                mFinger2 = new MotionEvent.PointerCoords();
                e.getPointerCoords(0,mFinger1);
                e.getPointerCoords(1,mFinger2);
                mDistance = ComputeDistance(mFinger1,mFinger2);
                break;

            // Case finger 2 or more up
            case MotionEvent.ACTION_POINTER_UP:
                mFinger1= null;
                mFinger2 = null;
                break;
        }
        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

    public static String actionToString(int action) {
        switch (action) {

            case MotionEvent.ACTION_DOWN: return "Down";
            case MotionEvent.ACTION_MOVE: return "Move";
            case MotionEvent.ACTION_POINTER_DOWN: return "Pointer Down";
            case MotionEvent.ACTION_UP: return "Up";
            case MotionEvent.ACTION_POINTER_UP: return "Pointer Up";
            case MotionEvent.ACTION_OUTSIDE: return "Outside";
            case MotionEvent.ACTION_CANCEL: return "Cancel";
            case MotionEvent.ACTION_SCROLL: return "Scroll";
        }
        return "";
    }

    private double ComputeDistance(MotionEvent.PointerCoords coordA, MotionEvent.PointerCoords coordB){
        return Math.sqrt(Math.pow(coordA.x-coordB.x,2)+Math.pow(coordA.y-coordB.y,2));
    }
}
