/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jwgl.test;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

/**
 *
 * @author maxon
 */
public class Model {
    private int drawCount;
    private int vID;
    private int tID;
    
    private int iID;
    
    public Model(float[] vertices, float[] texCoords, int[] indices){
        drawCount = indices.length;
        
        //FloatBuffer buffer = createBuffer(vertices);
//BufferUtils.createFloatBuffer(vertices.length);
//        buffer.put(vertices);
//        buffer.flip();
        
        vID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vID);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);
        
        tID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, tID);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(texCoords), GL_STATIC_DRAW);
        
        iID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iID);
        IntBuffer idBuffer = BufferUtils.createIntBuffer(indices.length);
        idBuffer.put(indices);
        idBuffer.flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, idBuffer, GL_STATIC_DRAW);
        
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, GL_ZERO);
        glBindBuffer(GL_ARRAY_BUFFER, GL_ZERO);
    }
    
    public void render() {
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        //glEnableClientState(vID);
        
        glBindBuffer(GL_ARRAY_BUFFER, vID);
        glVertexPointer(3, GL_FLOAT, 0, 0);
        
        glBindBuffer(GL_ARRAY_BUFFER, tID);
        glTexCoordPointer(2, GL_FLOAT, 0, 0);
        
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iID);
        
        glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);
        
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, GL_ZERO);
        glBindBuffer(GL_ARRAY_BUFFER, GL_ZERO);
        
        glDisableClientState(GL_VERTEX_ARRAY);
    }
    
    private FloatBuffer createBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
