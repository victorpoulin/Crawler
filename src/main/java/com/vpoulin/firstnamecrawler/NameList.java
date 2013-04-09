package com.vpoulin.firstnamecrawler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vpoulin
 */
public class NameList {

    public enum Type {

        BOY, GIRL, ANDROGYN
    };
    //liste des prenoms féminins
    private Set<String> girlNames;
    //liste des prenoms masculins
    private Set<String> boyNames;
    //nom de fichier devant stocker les prénoms féminins
    private String girlFileName;
    //nom de fichier devant stocker les prénoms masculins
    private String boyFileName;
    
    private FileWriter girlWriter, boyWriter;

    public NameList(String girlFileName, String boyFileName) throws IOException {
        girlNames = new TreeSet<String>();
        boyNames = new TreeSet<String>();
        this.girlFileName = girlFileName;
        this.boyFileName = boyFileName;
    }

    /**
     * Ajoute un prénom a la liste 
     * @param type Le genre du prénom   
     * @param name Le prénom
     */
    public void addName(Type type, String name) {
        if (type == Type.BOY || type == Type.ANDROGYN) {
            //verification de l'unicité du prenom
            if (!boyNames.contains(name)) {
                boyNames.add(name);
            }
        }
        if (type == Type.GIRL || type == Type.ANDROGYN) {
            if (!girlNames.contains(name)) {
                girlNames.add(name);
            }
        }
    }

    /**
     * Ecrit les deux listes (prénoms masculins et féminins) dans deux fichiers différents
     */
    public void writeResultsToFiles() {
        try {
            boyWriter = new FileWriter(boyFileName);
            for (String boyName : boyNames) {
                boyWriter.write(boyName + System.getProperty("line.separator"));
            }

            girlWriter = new FileWriter(girlFileName);
            for (String name : girlNames) {
                girlWriter.write(name + System.getProperty("line.separator"));
            }
        } catch (IOException ex) {
            Logger.getLogger(NameList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                boyWriter.flush();
                boyWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(NameList.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                girlWriter.flush();
                girlWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(NameList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
