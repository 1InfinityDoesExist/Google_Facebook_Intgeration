package com.fb.demo.utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;
import com.amazonaws.util.IOUtils;

@Component
public class FileToBase64 {

    /*
     * You have a like that opens up a picture. You need base64 of that image.
     */
    public String imageUrlToBase64(String imageUrl) throws Exception {
        String base64 = null;
        URL url = new URL(imageUrl);
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        byte[] bytes = IOUtils.toByteArray(inputStream);
        Base64.Encoder encoder = Base64.getEncoder();
        base64 = encoder.encodeToString(bytes);
        return base64;
    }

    public BufferedImage base64ToImage(String imageString) throws IOException {

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(imageString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        BufferedImage image = ImageIO.read(byteArrayInputStream);
        byteArrayInputStream.close();
        return image;
    }
}
