package mygraph;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author felipe
 */
public class VertexTest {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * Test of getValue method, of class Vertex.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        Vertex instance = new Vertex(50);
        Object expResult = 50;
        Object result = instance.getValue();
        assertEquals(expResult, result);
        
    }

    /**
     * Test of setValue method, of class Vertex.
     */
    @Test
    public void testSetValue() {
        System.out.println("setValue");
        Object value = 50;
        Vertex instance = new Vertex(10);
        instance.setValue(value);
        assertEquals(instance.getValue(), value);        
    }
    
}
