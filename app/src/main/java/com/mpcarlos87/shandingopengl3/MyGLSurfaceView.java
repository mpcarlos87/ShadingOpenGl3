package com.mpcarlos87.shandingopengl3;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by Carlos on 22/02/2015.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;
    private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 3.0 context
        setEGLContextClientVersion(3);

        mRenderer = new MyGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY); //TODO: TOUCH
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                mRenderer.setAngleY(mRenderer.getAngleY() + (dx * TOUCH_SCALE_FACTOR));
                mRenderer.setAngleX(mRenderer.getAngleX() + (dy * TOUCH_SCALE_FACTOR));
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
}
