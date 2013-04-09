/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private Set<String> girlNames;
    private Set<String> boyNames;
    private String girlFileName;
    private String boyFileName;
    private FileWriter girlWriter, boyWriter;

    public NameList(String girlFileName, String boyFileName) throws IOException {
        girlNames = new TreeSet<String>();
        boyNames = new TreeSet<String>();
        this.girlFileName = girlFileName;
        this.boyFileName = boyFileName;
    }

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
