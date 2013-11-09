/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package arduino;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author lucas
 */
public class ByteImage {
    
    private byte[] bytes;
    private ByteArrayOutputStream bos;
    
    public ByteImage(){
        this.bos = new ByteArrayOutputStream();
    }
    
    void gerar(){
        int i = 0;           
            /*
            bytes = bos.toByteArray();
           
            InputStream is = new ByteArrayInputStream(bytes);
            BufferedImage bi = ImageIO.read(is);
            ImageIO.write(bi, "jpg", new File("/home/lucas/darksouls" + i + ".jpg"));
            System.out.println("FOTO GERADA");
            i++;*/
            
            bytes = bos.toByteArray();
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            Iterator<?> readers = ImageIO.getImageReadersByFormatName("jpg");
            //ImageIO is a class containing static convenience methods for locating ImageReaders
            //and ImageWriters, and performing simple encoding and decoding.
            
            ImageReader reader = (ImageReader) readers.next();
            Object source = bis; // File or InputStream, it seems file is OK
            
            ImageInputStream iis;
            try {
            iis = ImageIO.createImageInputStream(source);
            //Returns an ImageInputStream that will take its input from the given Object
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Image image = reader.read(0, param);
            //got an image file
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
            //bufferedImage is the RenderedImage to be written
            Graphics2D g2 = bufferedImage.createGraphics();
            g2.drawImage(image, null, null);
            File imageFile = new File("/home/lucas/darksoul" + i + ".jpg");
            ImageIO.write(bufferedImage, "jpg", imageFile);
            System.out.println("--------------> IMAGEM CRIADA!!!");
            i++;
            } catch (IOException ex) {
            Logger.getLogger(ByteImage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    
    public void popular(byte[] buff, int size){
        bos.write(buff, 0, size);
    }
}
