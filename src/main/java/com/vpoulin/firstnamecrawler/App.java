package com.vpoulin.firstnamecrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


public class App 
{
    /**
     * 
     * @param args Argument0 : url a crawler , argument1 : chemin complet du fichier qui contiendra les prenoms féminins
     * argument 2 : chemin complet du fichier qui contiendra les prenoms masculins
     * argument 3 : chemin vers le dossier qui contiendra les pages mises en cache
     * 
     * exemple : http://www.meaning-of-names.com/english-names  C:\Users\vpoulin\girl.txt C:\Users\vpoulin\boy.txt C:\Users\vpoulin\temp
     * @throws MalformedURLException 
     */
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
