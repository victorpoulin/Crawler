/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vpoulin.firstnamecrawler;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
/**
 *
 * @author vpoulin
 */
public class PageTest extends TestCase {
    
    public PageTest(String testName) {
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
   
    /**
     * Test la creation d'un objet PAGE à partir d'une page web enregistrée sur le disque (testfile.txt)
     */
   @Test
   public void testCreation() {
       String testContent = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head></head><body><H1>TEST</H1> </body></html>";
       assertEquals(testContent, new Page("src\\test\\java\\com\\vpoulin\\firstnamecrawler\\testfile.txt").getContent());
   }
}
