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
public class Generation {
    
    int number;
    ArrayList<Membro> membros = new ArrayList();
    
    public Generation(int number, int size, int dnaSize) {
        this.number = number;
        create_membros(size, dnaSize);
    }
    
    public Generation(int number) {
        this.number = number;
    }
    
    public void create_membros(int size, int dnaSize) {
        for(int i = 0; i < size; i++) {
            Membro membro = new Membro(dnaSize);
            membros.add(membro);
        }
    }
    
    public void addMembro(Membro membro) {
        membros.add(membro);
    }
    
    public void addMembros(ArrayList<Membro> membros) {
        this.membros.addAll(membros);
    }

    public Membro getMembro(int position) {
        return membros.get(position);
    }

    public int getNumber() {
        return number;
    }
    
    public int size() {
        return membros.size();
    }
    
    public float getTotalScore() {
        float sum = 0;
        for(Membro membro: membros) sum += membro.getScore();
        return sum;
    }
    
    public Statistics getStatistics() {
        Statistics s = new Statistics();
        
        s.id = getNumber();
        s.score_total = getTotalScore();
        s.score_average = s.score_total/membros.size();
        s.numDNA = this.membros.get(0).getDNA().getSize();      
        
        return s;
    }
    
    
}
