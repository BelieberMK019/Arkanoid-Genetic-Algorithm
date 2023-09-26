/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GAs;

import java.util.ArrayList;

/**
 *
 * @author Vitor Rinaldini
 */
public class DNA extends ArrayList<Character>{
    
    public DNA(ArrayList<Character> fitaDNA) {
        for(Character c: fitaDNA) {
            add(new Character(c.charValue()));
        }
    }
    
    public DNA() {
    }
    
    public DNA getCopy() {
        return new DNA(this);
    }
    
    public int getSize() {
        return size();
    }
    
    public Character getCode(int position) {
        return get(position);
    }
    
    public void flip(int position) {
        if(get(position) == '0') set(position, '1');
        else set(position, '0');
    }
    
    public void addDNA(DNA dna) {
        addAll(dna);
    }
    
    public void setCode(int position, Character code) {
        set(position, code);
    }
    
    public void addCode(Character code) {
        add(code);
    }
    
    public void substituir(ArrayList<Character> newDNA, int start) {
        for(int position = start; position < start+newDNA.size(); position++) {
            Character c = newDNA.get(position-start);
            if(position < size()) {
                set(position, c);
            } else {
                add(c);
            }
        }
    }
    
    public DNA getSubDna(int startPosition, int size) {
        ArrayList<Character> dna = new ArrayList();
        for(int position = startPosition; position < size; position++) {
            dna.add(get(position));
        }
        return new DNA(dna);
    }
    
    public void print() {
        System.out.print("[");
        for(int i = 0; i < this.size(); i++) {
            Character c = this.get(i);
            System.out.print(c);
            if(i < this.size()-1) {
                System.out.print(",");
            } else {
                System.out.print("]");
            }
        }
    }
    
    
}
