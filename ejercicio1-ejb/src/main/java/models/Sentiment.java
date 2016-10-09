/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.HashMap;

/**
 *
 * @author sergio
 */
public enum Sentiment {
    
    VERY_NEGATIVE(0),NEGATIVE(1),NEUTRO(2),POSITIVE(3),VERY_POSITIVE(4),UNKNOW(5);
    
    private static final HashMap<Integer, Sentiment> lookup = new HashMap<>();
    
    static {
        //Create reverse lookup hash map
        for (Sentiment s : Sentiment.values()) {
            lookup.put(s.getValue(), s);
        }
    }
    
    private final int value;
    private Sentiment(final int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
    public static Sentiment getFromValue(Integer value) {
            //the reverse lookup by simply getting
        //the value from the lookup HsahMap.
        return lookup.get(value) != null ? lookup.get(value) : lookup.get(5);
    }
    
}
