/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import Geometry.Vector2f;
import Interface.Collision;
import Interface.Drawn;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Vitor Rinaldini
 */
public class Block implements Drawn, Collision{

    public static float WIDTH = 35;
    public static float HEIGHT = 20;
    
    private Vector2f position;
    private Color color = Color.BLUE;
    
    public Block(Vector2f position) {
        this.position = position.copy();
    }
    
    public Vector2f getPosition() {
        return this.position;
    }
    
    
    @Override
    public void paint(Graphics2D g) {
        float xInit = this.position.x - WIDTH/2;
        float yInit = this.position.y - HEIGHT/2;
        
        g.setColor(color);
        g.fillRect((int) xInit,(int) yInit,(int) WIDTH, (int) HEIGHT);
        g.setColor(Color.BLACK);
        g.drawRect((int) xInit,(int) yInit,(int) WIDTH, (int) HEIGHT);
    }

    @Override
    public Shape getShape() {
        float xInit = this.position.x - WIDTH/2;
        float yInit = this.position.y - HEIGHT/2;
        
        Rectangle2D rectangle = new Rectangle.Float((int) xInit,(int) yInit,(int) WIDTH, (int) HEIGHT);
        return rectangle;
    }
    
}
