/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vpoulin.firstnamecrawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author vpoulin
 */
public class Crawler {
    
    //liste des urls a traiter
    private Set<String> pendingUrls;
    //liste des urls deja traitées (donc a ne pas retraiter)
    private Set<String> processedUrls;
    
    private NameList nameList;
    
    //nombre max de recursions autorisé
    private final int MAX_PROFONDEUR = 300;
    //nombre de récusions courant
    private int profondeur;
    
    //Chemin vers le dossier de travail (contiendra la pages mises en cache)
    private String path;
    
    /**
     * Construction du crawler
     * @param girlFile chemin complet et nom du fichier devant contenir les noms féminins
     * @param boyFile chemin complet et nom du fichier devant contenir les noms masculins
     * @param path  chemin complet du dossier dans lequel les pages sertont mises en cache
     * @throws IOException 
     */
    public Crawler(String girlFile, String boyFile, String path) throws IOException
    {
        pendingUrls = new HashSet<String>();
        processedUrls = new HashSet<String>();
        nameList = new NameList(girlFile,boyFile);
        this.path = path;
        
        profondeur = 0;
    }
    

    /**
     * Fonction récursive pour le parcours d'une page : extraction des liens contenus dans cette page
     * @param url URL de la page à crawler
     */
    public void crawlePage(String url)
    {
        System.out.println("PAGE COURANTE : "+url);
        if(profondeur < MAX_PROFONDEUR)
        {
            try 
            {
                Page p = new Page(url,path);
                //Obtenir le contenu de la page 
                String content = p.getContent();
                //En extraire les liens
                findLinks(content);       
                
                //traitement de cette page terminée
                processedUrls.add(url);
                pendingUrls.remove(url);
                profondeur ++;
                
                System.out.println("RESTE A FAIRE "+pendingUrls.size()+" URLs");
               
                try {
                     //Attente avant passage au suivant (ne pas saturer le serveur)
                     Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Passage au suivant
                if(! pendingUrls.isEmpty())
                    crawlePage((String)pendingUrls.toArray()[0]);
                    
            }
            catch (MalformedURLException ex) {
                Logger.getLogger(Crawler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * Retrouve les liens dans la page
     * @param content Le contenu de la page à analyser
     */
    public void findLinks(String content) 
    {
        ArrayList<String> url = new ArrayList<String>();
        //Expression reguliere pour retrouver les liens
        //restriction sur les noms ANGLAIS
        String prefix = "/english-names/";

        //prendre en compte les liens de type : /english-names/x-names-y.asp ET http://www.meaning-of-names.com/english-names/english-names/x-names-y.asp ;
        Pattern p = Pattern.compile("href=\"(.*?)" + prefix+"(.)-names-(.*?).asp\"");
        Matcher m = p.matcher(content);
        //recherche des liens
        while (m.find()) 
        {
           String fullLink = "http://www.meaning-of-names.com"+prefix + m.group(2)+"-names-"+m.group(3)+".asp";
            //Ajout du lien si pas deja fait
            if(! url.contains(fullLink))
                url.add(fullLink); 
        }
        //ajout des liens trouvés
        for(String link : url)
        {
            //Ajout du lien uniquement si celui ci n'a pas deja été traité ou pas deja dans la file d'attente
            if( ! processedUrls.contains(link) && ! pendingUrls.contains(link))
                pendingUrls.add(link);
         }

    }
    
    /**
     * Extrait les prénoms de l'ensemble des pages mises en cache et les enregistre dans les deux fichiers prénoms
     * 
     */
    public void extractNamesFromFiles()
    {
        //recuperer tous les fichiers du repertoire de cache
        File directory = new File(path);
        String[] files = directory.list();
        
        //traitement de tous les fichiers
        Page curPage;
        for(String file:files)
        {
            System.out.println("Traitement de :"+file);
            
            //Construction d'un page deja mise en cache
            curPage = new Page(path+"/"+file);
            //recuperation du contenu de la page
            String content = curPage.getContent();
           
            //Recherche des prénoms et leur genre
            Pattern p = Pattern.compile("<a href=\"http://www.meaning-of-names.com/english-names/(.*?)>(.*?)</a></div><div (.*?)><b>(.*?)</b>");
            Matcher m = p.matcher(content);
            while(m.find())
            {
                String sexe = m.group(4).toLowerCase();
                String name = m.group(2);
                //Ajout des prenoms et genres à la liste
                if(sexe.equals("male"))
                    nameList.addName(NameList.Type.BOY, name);
                else if(sexe.equals("female"))
                     nameList.addName(NameList.Type.GIRL, name);
                else 
                    nameList.addName(NameList.Type.ANDROGYN, name);  
            } 
        }
        //ecriture dans les fichiers
        nameList.writeResultsToFiles();
    }
    
    public Set<String> getPendingUrls()
    {
        return pendingUrls;
    }
       
}
