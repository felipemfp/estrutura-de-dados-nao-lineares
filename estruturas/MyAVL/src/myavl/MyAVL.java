/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myavl;

/**
 *
 * @author guest-caerzc
 */
public class MyAVL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InvalidKeyException {
        AVLPrintable avl = new AVLPrintable(7, "Felipe Pontes", new IntegerComparator());
        System.out.println(avl);
        
        addAndPrint(avl, 5, "Karen Cristina");
        addAndPrint(avl, 3, "Francisco Bento");
        addAndPrint(avl, 4, "Beatriz Nascimento");
        addAndPrint(avl, 14, "Alguém");
        addAndPrint(avl, 15, "Mareana Graciano");
        addAndPrint(avl, 22, "Cecília Rayllana");
        addAndPrint(avl, 10, "Lana Raquel");
        addAndPrint(avl, 6, "Rebeca Gagliuffi");
        addAndPrint(avl, 23, "Paulo Trindade");
        addAndPrint(avl, 8, "Cesar Vagner");
        addAndPrint(avl, 12, "Deyvison Costa");
        
        removeAndPrint(avl, 14);        
        removeAndPrint(avl, 6);        
        removeAndPrint(avl, 7);
        
        addAndPrint(avl, 6, "Karla Viviane");
        
        removeAndPrint(avl, 5);
        removeAndPrint(avl, 8);
        removeAndPrint(avl, 6);
        removeAndPrint(avl, 15);
        removeAndPrint(avl, 22);
        addAndPrint(avl, 6, "Rebeca Gagliuffi");
        addAndPrint(avl, 22, "Cecília Rayllana");
    }
    
    private static void removeAndPrint(AVLPrintable avl, Object k) throws InvalidKeyException {
        String alguem = (String)avl.remove(k);
        System.out.println(alguem + " [" +k+ "] foi removido.");
        System.out.println(avl);
    }
    
    private static void addAndPrint(AVLPrintable avl, Object k, Object o) {
        avl.insert(k, o);
        System.out.println(o + " [" +k+ "] foi adicionado.");
        System.out.println(avl);
}

}
