/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.wellcalibration;

import com.sun.media.jai.codec.ByteArraySeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
import com.sun.media.jai.codec.SeekableStream;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import javax.media.jai.PlanarImage;

/**
 *
 * @author KH
 */
public class ImageHandler {

    public Image load(byte[] data) throws Exception {
        Image image = null;
        SeekableStream stream = new ByteArraySeekableStream(data);
        String[] names = ImageCodec.getDecoderNames(stream);
        ImageDecoder dec
                = ImageCodec.createImageDecoder(names[0], stream, null);
        RenderedImage im = dec.decodeAsRenderedImage();
        image = PlanarImage.wrapRenderedImage(im).getAsBufferedImage();
        return image;
    }

    public Image getImage(String path) throws Exception {

        FileInputStream in = new FileInputStream(path);
        FileChannel channel = in.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
        channel.read(buffer);
        Image image = load(buffer.array());
    // make sure that the image is not too big
        //  scale with a width of 500 
        Image imageScaled
                = image.getScaledInstance(500, -1, Image.SCALE_SMOOTH);
        return image;
        //System.out.println("image: " + path + "\n" + image);
        //JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(imageScaled)));
    }
}
