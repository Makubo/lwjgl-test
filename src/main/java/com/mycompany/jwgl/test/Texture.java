/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jwgl.test;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;

/**
 *
 * @author maxon
 */
public class Texture {
    private int id;
    private int width;
    private int height;
    
    public Texture(String filename) {
        BufferedImage bi;
        
        try {
            bi = ImageIO.read(new File(filename));
            width = bi.getWidth();
            height = bi.getHeight();
            
            int bufferSize = width * height * 4;
            
            System.out.println("Pixel size : " + bi.getColorModel().getPixelSize());
                    
            int[] rawPixels = new int[bufferSize];
            rawPixels = bi.getRGB(0, 0, width, height, null, 0, width);
            
            ByteBuffer pixels = BufferUtils.createByteBuffer(bufferSize);
            
            for (int i = 0; i < height; i++){
                for (int j = 0; j < width; j++){
                    int pixel = rawPixels[i*width + j];
                    pixels.put((byte) ((pixel >> 16) & 0xFF)); //RED
                    pixels.put((byte) ((pixel >> 8) & 0xFF));  //GREEN
                    pixels.put((byte) (pixel & 0xFF));         //BLUE
                    pixels.put((byte) ((pixel >> 24) & 0xFF)); //ALPHA
                }
            }
            
            pixels.flip();
            
            id = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, id);
            
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER,GL_NEAREST);
            
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void bind(){
        glBindTexture(GL_TEXTURE_2D, id);
    }
}
