/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vpoulin.firstnamecrawler;

import java.io.IOException;
import java.util.ArrayList;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author vpoulin
 */
public class CrawlerTest extends TestCase {
    
    public CrawlerTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
    public void testFindLinks() throws Exception {
        Crawler crawler = new Crawler(".\\girl.txt", ".\\boy.txt", ".");
        //Contenu de test
        String content = "<html>\n" +
        "<head>\n" +
        "</head>\n" +
        "<body>\n" +
        "<a href=\"http://www.google.fr\">\n" +
        "<a href=\"/english-names/b-names-2.asp\">\n" +
        "<a href=\"/english-names/c-names-20.asp\">\n" +
        "<a href=\"/english-names/dd-names-1.asp\">\n" +
        "<a href=\"/english-names/b-names-3.asp\" style=\"font-size:10px;\">\n" +
        "<a id=\"0\" href=\"/english-names/b-names-4.asp\" style=\"font-size:10px;\">\n" +
        "<a href=\"http://www.meaning-of-names.com/english-names/english-names/z-names-2.asp\">\n" +
        "</body>\n" +
        "</html>";

        //Liens a trouver 
        ArrayList<String> expectedResults = new ArrayList<String>();
        expectedResults.add("http://www.meaning-of-names.com/english-names/b-names-2.asp");
        expectedResults.add("http://www.meaning-of-names.com/english-names/c-names-20.asp");
        expectedResults.add("http://www.meaning-of-names.com/english-names/b-names-3.asp");
        expectedResults.add("http://www.meaning-of-names.com/english-names/b-names-4.asp");
        expectedResults.add("http://www.meaning-of-names.com/english-names/z-names-2.asp");    
      
        //Trouver les liens
        crawler.findLinks(content);
        //Recuperation du resultat
        String [] actualResults = crawler.getPendingUrls().toArray(new String [0]);
        
        //Verification du nombre de resultat
        Assert.assertTrue(actualResults.length == expectedResults.size());
        //Verification de chaque resultat
        for(String url: actualResults)
        {
            Assert.assertTrue(expectedResults.contains(url));
           
        }
    }
    
    @Test
    public void testExtractNamesFromFiles() throws IOException
    {
        String girlFilePath = ".\\src\\test\\java\\com\\vpoulin\\firstnamecrawler\\girl.txt";
        String boyFilePath=".\\src\\test\\java\\com\\vpoulin\\firstnamecrawler\\boy.txt";
        Crawler crawler = new Crawler(girlFilePath, boyFilePath, "src\\test\\java\\com\\vpoulin\\firstnamecrawler\\");
        crawler.extractNamesFromFiles();
        
        ArrayList<String> expectedBoyResults = new ArrayList<String>();
        ArrayList<String> expectedGirlResults = new ArrayList<String>();
        expectedBoyResults.add("Quent");
        expectedBoyResults.add("Quentin");
        expectedBoyResults.add("Quenton");
        expectedBoyResults.add("Quentrell");
        expectedBoyResults.add("Quincey");
        expectedBoyResults.add("Quincy");
        expectedBoyResults.add("Quint");
        expectedBoyResults.add("Quinton");
        expectedBoyResults.add("Quintrell");
        
        expectedGirlResults.add("Queen");
        expectedGirlResults.add("Queena");
        expectedGirlResults.add("Queenie");
        expectedGirlResults.add("Queeny");
        expectedGirlResults.add("Quella");
        expectedGirlResults.add("Quenna");
        expectedGirlResults.add("Quilla");
        
        
        
        //TO DO : Terminer
        
    }

}