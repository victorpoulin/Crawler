package com.vpoulin.firstnamecrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws MalformedURLException
    {
        if(args.length > 2)
        {
            try {
                Crawler crawler = new Crawler(args[1],args[2],args[3]);
                
                //Mise en cache des pages 
                crawler.crawlePage(args[0]);
                
                //Consitution des fichiers avec les prénoms
                crawler.extractNamesFromFiles();
                
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
