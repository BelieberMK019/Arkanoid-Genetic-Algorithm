/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Random;

import java.util.Random;

/**
 *
 * @author Vitor Rinaldini
 */
public class RandomNumbers {
    
    private Random random;
    
    private RandomNumbers(int seed) {
        random = new Random(seed);
    }
    
    private RandomNumbers() {
        random = new Random();
    }
    
    public char getBinaryChar() {
        return (char) ('0' + random.nextInt(2));
    }
    
    public int getRandomNumber(int max) {
        return random.nextInt(max);
    }
    
    public float getProbability() {
        return random.nextFloat();
    }
    
    private static RandomNumbers randomObj = null;
    public static RandomNumbers getInstance(int seed) {
        if(randomObj == null) randomObj = new RandomNumbers(seed);
        return randomObj;
    }
    
    public static RandomNumbers getInstance() {
        if(randomObj == null) randomObj = new RandomNumbers();
        return randomObj;
    }
    
}
