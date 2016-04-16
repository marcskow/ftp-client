package pl.edu.agh.marcskow.ftpclient.layout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * Created by intenso on 16.04.16.
 */
public class ImageService {
    public static final Image fileImage = new Image(ClassLoader.getSystemResourceAsStream("images/filesmall.png"));
    public static final Image folderImage = new Image(ClassLoader.getSystemResourceAsStream("images/folder.png"));

    public static ImageView checkImage(File file){
        ImageView imageView = new ImageView();
        if(file.isDirectory()){
            imageView.setImage(folderImage);
        } else {
            imageView.setImage(fileImage);
        }
        return imageView;
    }

    public static ImageView checkImageToString(String s){
        ImageView imageView = new ImageView();
        if(s.equals("d")){
            imageView.setImage(folderImage);
        } else {
            imageView.setImage(fileImage);
        }
        return imageView;
    }
}
