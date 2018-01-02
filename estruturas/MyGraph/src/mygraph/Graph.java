/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author felipe
 */
public class Graph {
    Vertex[] vertices;
    Edge[][] matrix;
    
    public Graph() {
        this(30);
    }
    
    public Graph(int size) {
        vertices = new Vertex[size];
        matrix = new Edge[size][size];
    }
    
    private int findEmpty() {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i] == null) {
                return i;
            }
        }
        expand();
        return findEmpty();
    }
    
    private void expand() {
        int size = vertices.length * 2;
        Vertex[] verticesCopy = vertices;
        Edge[][] matrixCopy = matrix;
        
        vertices = new Vertex[size];
        matrix = new Edge[size][size];
        
        for (int i = 0; i < verticesCopy.length; i++) {
            vertices[i] = verticesCopy[i];
        }
        
        for (int i = 0; i < verticesCopy.length; i++) {
            for (int j = 0; j < verticesCopy.length; j++) {
                matrix[i][j] = matrixCopy[i][j];
            }
        }
    }
    
    private Vertex findVertex(Object o) {
        for (int i = 0; i < vertices.length; i++) {
            if (vertices[i].getValue().equals(o)) {
                return vertices[i];
            }
        }
        return null;
    }
    
    private int findIndex(Vertex v) {
        for (int i = 0; i < vertices.length; i++) {
            if (v.equals(vertices[i])) {
                return i;
            }
        }
        return -1;
    }
    
    public Vertex[] finalVertices(Edge e) {
        return new Vertex[] { e.getStart(), e.getEnd() };
    }
    
    public Vertex opposite(Vertex v, Edge e) throws NotRelatedEdge {
        if (e.getEnd().equals(v)) return e.getStart();
        if (e.getStart().equals(v)) return e.getEnd();
        
        throw new NotRelatedEdge();
    }
    
    public boolean isAdjacent(Vertex v, Vertex w) {
        int vi = findIndex(v);
        int wi = findIndex(w);
        
        return matrix[vi][wi] != null;
    }
    
    public void replace(Vertex v, Object o) {
        int i = findIndex(v);
        vertices[i].setValue(o);
    }
    
    public void replace(Edge e, Object o){
        int vi = findIndex(e.getStart());
        int wi = findIndex(e.getEnd());
        matrix[vi][wi].setValue(o);
        if (!e.isDirected()) {
            matrix[wi][vi].setValue(o);
        }
    }
    
    public Vertex insertVertex(Object o) {
        Vertex v = new Vertex(o);
        int i = findEmpty();
        vertices[i] = v;
        return v;
    }
    
    private Edge insertEdge(Vertex v, Vertex w, Object o, boolean isDirected) {
        int vi = findIndex(v);
        int wi = findIndex(w);
        Edge e = new Edge(v, w, o, isDirected);
        matrix[vi][wi] = e;
        if (!isDirected) {
            matrix[wi][vi] = e;
        }
        return e;
    }
    
    public Edge insertEdge(Vertex v, Vertex w, Object o) {
        return insertEdge(v, w, o, false);
    }
    
    public Edge insertDirectedEdge(Vertex v, Vertex w, Object o) {
        return insertEdge(v, w, o, true);
    }
    
    public Object removeVertex(Vertex v) {
        int i = findIndex(v);
        vertices[i] = null;
        for (int j = 0; j < vertices.length; j++) {
            for (int k = 0; k < vertices.length; k++) {
                if (j == i || k == i) {
                    matrix[j][k] = null;
                }
            }
        }
        return v.getValue();
    }
    
    public Object removeEdge(Edge e) {
        int vi = findIndex(e.getStart());
        int wi = findIndex(e.getEnd());
        
        matrix[vi][wi] = null;
        if (!e.isDirected()) {
            matrix[wi][vi] = null;
        }
        
        return e.getValue();
    }
    
    public Iterator<Edge> edgesRelated(Vertex v) {
        Set<Edge> edges = new HashSet<>();
        
        int i = findIndex(v);
        
        for (int j = 0; j < vertices.length; j++) {
            for (int k = 0; k < vertices.length; k++) {
                if (j == i || k == i) {
                    edges.add(matrix[j][k]);
                }
            }
        }
        
        return edges.iterator();
    }
    
    public Iterator<Vertex> vertices() {
        return new ArrayList(Arrays.asList(vertices)).iterator();
    }
    
    public Iterator<Edge> edges() {
        Set<Edge> edges = new HashSet<>();
        
        for (int j = 0; j < vertices.length; j++) {
            for (int k = 0; k < vertices.length; k++) {
                if (matrix[j][k] != null) {
                    edges.add(matrix[j][k]);
                }
            }
        }
        
        return edges.iterator();
    }
    
    public boolean isDirected(Edge e) {
        return e.isDirected();
    }
    
}
