/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cohesion.imgrecog.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.apache.commons.fileupload.FileItemStream;

/**
 *
 * @author Calvin He
 */
public class FileUploader {
    public static boolean process(String path, FileItemStream item){
        try{
            File f = new File(path + File.separator + "images");
            if(!f.exists())
                f.mkdir();
            File savedFile = new File(f.getAbsolutePath() + File.separator + item.getName());
            FileOutputStream fos = new FileOutputStream(savedFile);
            InputStream is = item.openStream();
            int length = 0;
            byte[] buffer = new byte[1024];
            while((length = is.read(buffer)) != -1){
                fos.write(buffer, 0, length);
            }
            fos.flush();
            fos.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
