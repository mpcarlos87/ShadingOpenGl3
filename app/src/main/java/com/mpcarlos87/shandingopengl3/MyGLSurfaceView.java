package com.mpcarlos87.shandingopengl3;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by Carlos on 22/02/2015.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 3.0 context
        setEGLContextClientVersion(3);

        mRenderer = new MyGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
    }


}
