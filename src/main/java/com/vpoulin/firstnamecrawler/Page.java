/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vpoulin.firstnamecrawler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vpoulin
 */
public class Page {

    private URL url;
    private StringBuilder content;
    //writer pour l'enregistreement de la page en cache
    private FileWriter writer;

    
    /**
     * 
     * @param adresse URL de la page
     * @param path Chemin vers le dossier où vont être enregistrées les pages
     * @throws MalformedURLException 
     */
    public Page(String adresse, String path) throws MalformedURLException {
        url = new URL(adresse);

        try {
            //Connexion a l'url
            URLConnection connec = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connec.getInputStream()));

            //recuperation des données dans le stringbuilder
            String inputLine;
            content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            //enregistrement de la page dans le dossier indiqué en parametre
            writer = new FileWriter(path + "//" + adresse.substring(adresse.lastIndexOf("/")) + ".txt");

            writer.write(content.toString());

        } catch (IOException ex) {
            Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Construction d'une Page à partir de son fichier mis en cache
     * @param fullPath Chemin complet vers le fichier contenant la page
     */
    public Page(String fullPath)
    {
        BufferedReader in=null;
        try {
            in = new BufferedReader(new FileReader(fullPath));
            String inputLine;
            content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Page.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(Page.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Retourne le contenu de la page
     * @return String contenant la page
     */
    public String getContent()
    {
        return content.toString();
    }
  
}
