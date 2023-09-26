/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import Interface.Drawn;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Vitor Rinaldini
 */
public class Canvas implements Drawn {
    private ArrayList<Drawn> objectsToPrint = new ArrayList();
    
    public void addObj(Drawn object) {
        objectsToPrint.add(object);
    }
    
    public void addObjs(ArrayList<? extends Drawn> objects) {
        objectsToPrint.addAll(objects);
    }
    
    public void removeObj(Drawn object) {
        objectsToPrint.remove(object);
    }
    
    @Override
    public void paint(Graphics2D g) {
        Graphics2D g2D = (Graphics2D) g;
        for (Drawn objectToPrint: objectsToPrint) {
            objectToPrint.paint(g2D);
        }
    }
    
}
