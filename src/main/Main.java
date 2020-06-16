package main;

import handlers.ImageStorage;
import org.jnativehook.GlobalScreen;

import services.ManageService;


public class Main
{
    public static void main(String[] args)
    {
        ImageStorage imageStorage = new ImageStorage();
        imageStorage.createPath();
        ManageService service = new ManageService();

        try
        {
            GlobalScreen.registerNativeHook();
        }
        catch(Throwable e)
        {
            e.printStackTrace();
        }
        GlobalScreen.getInstance().addNativeKeyListener(service.getKeyboard());
    }
}