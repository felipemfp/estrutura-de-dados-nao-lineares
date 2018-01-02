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
public class Edge {
    private Vertex end;
    private Vertex start;
    private Object value;
    private boolean isTwoWay;

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    public Edge(Vertex s, Vertex e, Object o) {
        this(s, e, o, true);
    }
    
    public Edge(Vertex s, Vertex e, Object o, boolean isTwoWay) {
        start = s;
        end = e;
        value = o;
        this.isTwoWay = isTwoWay;
    }

    public Vertex getEnd() {
        return end;
    }

    public Vertex getStart() {
        return start;
    }
    
    public boolean isDirected() {
        return !isTwoWay;
    }
}
