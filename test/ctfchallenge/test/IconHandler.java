/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ctfchallenge.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Matteo Franzil
 */
public class IconHandler {

    String base64Image = "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";
    Image image;

    public IconHandler() {
        //base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
        try (FileOutputStream imageOutFile = new FileOutputStream("image.png")) {
            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
            imageOutFile.write(imageByteArray);
            Image e = new Image("file:image.jpg");
            this.image = e;
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
    }

    public static void main(String[] args) {
        IconHandler a = new IconHandler();
    }

    public void setIcon(Stage stage) {
        stage.getIcons().add(image);
    }
}
