package com.mpcarlos87.shandingopengl3.Geometry;

import android.opengl.GLES30;

import com.mpcarlos87.shandingopengl3.MyGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Carlos on 05/07/2015.
 */
public class BaseGeometry {

    private FloatBuffer vertexBuffer,vertexColorBuffer;

    private ShortBuffer mIndices;

    private final short[] mIndicesFaces; //TODO: Constructor

    private int mNumIndices = 0; //TODO: Constructor from mIndicesData(Size)

    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "attribute vec4 vColor;" +
                    "varying vec4 fColor;" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "  fColor = vColor;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "varying vec4 fColor;" +
                    "void main() {" +
                    "  gl_FragColor = fColor;" +
                    "}";

    // Use to access and set the view transformation
    private int mMVPMatrixHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static final int COLORS_PER_VERTEX = 4;

    static float mGeometryCoords[]; //TODO: FRom constructor

    // Set color with red, green, blue and alpha (opacity) values
    static float mColors[]; //TODO :From constructor

    private final int mProgram;

    // VertexBufferObject Ids
    private int [] mVBOIds = new int[1];

    public BaseGeometry(float[] coords, short[] faces, float[] colors) {
        mGeometryCoords = coords;
        mNumIndices = coords.length;
        mIndicesFaces = faces;
        mColors = colors;

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                coords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(mGeometryCoords);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bColor = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                mColors.length * 4);
        // use the device hardware's native byte order
        bColor.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexColorBuffer = bColor.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexColorBuffer.put(mColors);
        // set the buffer to read the first coordinate
        vertexColorBuffer.position(0);

        mIndices = ByteBuffer.allocateDirect ( mIndicesFaces.length * 2 )
                .order ( ByteOrder.nativeOrder() ).asShortBuffer();
        mIndices.put ( mIndicesFaces ).position ( 0 );

        mVBOIds[0] = 0;

        int vertexShader = MyGLRenderer.loadShader(GLES30.GL_VERTEX_SHADER,
                vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES30.GL_FRAGMENT_SHADER,
                fragmentShaderCode);

        // create empty OpenGL ES Program
        mProgram = GLES30.glCreateProgram();

        // add the vertex shader to program
        GLES30.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES30.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES30.glLinkProgram(mProgram);
    }

    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    private final int vertexColorStride = COLORS_PER_VERTEX * 4; // 4 bytes per vertex

    public void draw(float[] mMVPMatrix) {
        int numIndices = mNumIndices;
        // Add program to OpenGL ES environment
        GLES30.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES30.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the pyramid vertices
        GLES30.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the pyramid coordinate data
        GLES30.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES30.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES30.glGetAttribLocation(mProgram, "vColor");

        // Enable a handle to the pyramid vertices
        GLES30.glEnableVertexAttribArray(mColorHandle);

        // Prepare the Colors for vertices
        GLES30.glVertexAttribPointer(mColorHandle, COLORS_PER_VERTEX,
                GLES30.GL_FLOAT, false,vertexColorStride,vertexColorBuffer);

        // get handle to shape's transformation matrix
        mMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram, "uMVPMatrix");

        // Pass the projection and view transformation to the shader
        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        ///Custom
        // Reserve memory for the indexes of the faces
        if(mVBOIds[0] == 0)
        {
            // Only allocate on the first draw
            GLES30.glGenBuffers ( 1, mVBOIds, 0 );
            mIndices.position ( 0 );
            GLES30.glBindBuffer ( GLES30.GL_ELEMENT_ARRAY_BUFFER, mVBOIds[0] );
            GLES30.glBufferData ( GLES30.GL_ELEMENT_ARRAY_BUFFER, 2 * numIndices,
                    mIndices, GLES30.GL_STATIC_DRAW );
        }

        //Bind the buffer of the faces
        GLES30.glBindBuffer ( GLES30.GL_ELEMENT_ARRAY_BUFFER, mVBOIds[0] );

        // Draw the pyramid
        GLES30.glDrawElements( GLES30.GL_TRIANGLES,numIndices,
                GLES30.GL_UNSIGNED_SHORT, 0 );

        // Disable vertex array
        GLES30.glDisableVertexAttribArray(mPositionHandle);
        //Disable bind buffer??
        GLES30.glBindBuffer ( GLES30.GL_ARRAY_BUFFER, 0 );
        GLES30.glBindBuffer ( GLES30.GL_ELEMENT_ARRAY_BUFFER, 0 );
    }

}
