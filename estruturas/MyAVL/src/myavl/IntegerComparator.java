/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myavl;

import java.util.Comparator;

/**
 *
 * @author guest-caerzc
 */
public class IntegerComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer v1, Integer v2) {
        return v1 < v2 ? -1 : v1 > v2 ? +1 : 0;
    }
}