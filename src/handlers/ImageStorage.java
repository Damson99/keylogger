package handlers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ImageStorage
{
    public String imagesPath = System.getProperty("user.dir") + "\\img\\";

    private List<String> imagesList = new ArrayList<>();


    public void addImageToList(String image)
    {
        imagesList.add(image);
    }

    public List<String> getImagesList()
    {
        return imagesList;
    }

    public void createPath()
    {
        Path path = Paths.get(imagesPath);
        if(!Files.exists(path))
        {
            try
            {
                Files.createDirectory(path);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void clearImages()
    {
        imagesList.clear();
    }
}
