/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GAs;

/**
 *
 * @author Vitor Rinaldini
 */
public class Statistics {
    
    public int id;
    public float score_total;
    
    public float score_average;
    public int numDNA;
    
    public Membro top1 = null;
    
    public Statistics() {};
    
    public Statistics(int id, float score_total, float score_top1, float score_average, int numDNA) {
        this.id = id;
        this.score_total = score_total;
        this.score_average = score_average;
        this.numDNA = numDNA;
    }
    
}
