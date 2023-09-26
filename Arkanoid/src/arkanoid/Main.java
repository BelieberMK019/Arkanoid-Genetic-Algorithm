/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package arkanoid;

import Random.RandomNumbers;

/**
 *
 * @author Vitor Rinaldini
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int seed = 8975;
        RandomNumbers rand = RandomNumbers.getInstance(seed);
        Program p = new Program();
    }
    
}
