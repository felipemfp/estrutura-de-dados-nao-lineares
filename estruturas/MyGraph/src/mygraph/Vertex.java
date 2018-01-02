/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygraph;

/**
 *
 * @author felipe
 */
public class Vertex {
    private Object value;
    
    public Vertex(Object o) {
        value = o;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
