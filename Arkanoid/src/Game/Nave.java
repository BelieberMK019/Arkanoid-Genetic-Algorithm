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
public class Nave implements Drawn, Collision {
    public static int LEFT_DIRECTION = -1;
    public static int STOP_DIRECTION = 0;
    public static int RIGHT_DIRECTION = 1;
    public static float SPEED = 5f;
    
    private Vector2f position;
    private int direction = 0;
    public static float WIDTH = 100;
    public static float HEIGHT = 10;
    private Color color = Color.RED;
    
    public Nave(float x, float y) {
        this.position = new Vector2f(x, y);
    }
    
    public Nave(Vector2f position) {
        this.position = position.copy();
    }
    
    public Nave() {
        this.position = new Vector2f(0, 0);
    }
    
    public void resetNave(Vector2f position) {
        this.position = position.copy();
    }
    
    public void setX(float x) {
        this.position.x = x;
    }
    
    public void setY(float y) {
        this.position.y = y;
    }
    
    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public int getDirection() {
        return direction;
    }
    
    public Vector2f getPosition() {
        return position.copy();
    }
    
    public void setPosition(Vector2f position) {
        this.position = position;
    }
    
    public float getX() {
        return this.position.x;
    }
    
    public float getY() {
        return this.position.y;
    }
    
    public void setColor(Color color) {
        this.color = color;
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
