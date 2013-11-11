/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arduino;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.util.Random;

/**
 *
 * @author lucas
 */
public class ByteImage {

    private byte[] bytes;
    private ByteArrayOutputStream bos;
    private Random random = new Random();
    private String name;

    public ByteImage() {
        this.bos = new ByteArrayOutputStream();
        name = "";
    }

    String gerar() {
        bytes = bos.toByteArray();
        InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage bi;
        try {
            bi = ImageIO.read(is);
            name = "darksouls" + random.nextInt(1000) + ".jpg";
            ImageIO.write(bi, "jpg", new File("/home/lucas/" + name));
            return name;

        } catch (IOException ex) {
            Logger.getLogger(ByteImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public void popular(byte[] buff, int size) {
        bos.write(buff, 0, size);
    }
}
