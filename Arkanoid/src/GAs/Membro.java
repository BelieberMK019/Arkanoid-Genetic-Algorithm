/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GAs;

import Game.Game;
import Game.Nave;
import Random.RandomNumbers;
import java.util.ArrayList;

/**
 *
 * @author Vitor Rinaldini
 */
public class Membro implements Comparable<Membro>{
    
    private float score = 0;
    private DNA dna;
    
    public Membro(int dnaSize) {
        this.dna = new DNA();
        increaseDna(dnaSize);
    }
    
    public Membro(DNA dna) {
        this.dna = dna.getCopy();
    }
    
    public Membro getCopy() {
        return new Membro(dna);
    }
    
    public void increaseDna(int deltaSize) {
        RandomNumbers rand = RandomNumbers.getInstance();
        for(int i = 0; i < deltaSize; i++) {
            dna.add(rand.getBinaryChar());
        }
    }
    
    public void setScore(float score) {
        this.score = score;
    }
    
    public float getScore() {
        return this.score;
    }
    
    public void addScore(float deltaScore) {
        this.score += deltaScore;
    }
    
    public DNA getDNA() {
        return this.dna;
    }
    
    public void substituir(ArrayList<Character> newDNA, int start) {
        dna.substituir(newDNA, start);
    }

    @Override
    public int compareTo(Membro o) {
        float delta = -(score - o.score);
        if(delta > 0) return 1;
        else if(delta == 0) return 0;
        else return -1;
    }
    
    int ti = 0;    
    public void play(Game game) {
        Nave nave = game.nave;
        if(ti >= dna.size()) {
            nave.setDirection(Nave.STOP_DIRECTION);
            //game.setRunning(false);
            return;
        } else if(dna.getCode(ti) == '0'){
            nave.setDirection(Nave.LEFT_DIRECTION);
        } else {
            nave.setDirection(Nave.RIGHT_DIRECTION);
        }        
        //ti = (ti+1) % dna.getSize();
        ti += 1;
        
    }
    
}
