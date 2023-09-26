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
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author Vitor Rinaldini
 */
public class Ball implements Drawn, Collision {
    public static float RADIUS = 5f;
    public static float SPEED = 5f;
    
    private Vector2f position;
    private Vector2f velocity;
    private Color color = Color.RED;
    
    public Ball(Vector2f position, Vector2f velocity) {
        this.position = position.copy();
        this.velocity = velocity.copy();
    }
    
    public Ball() {
        position = new Vector2f(0, 0);
        velocity = new Vector2f(0, 0);
    }
    
    public void resetBall(Vector2f position, Vector2f velocity) {
        this.position = position.copy();
        this.velocity = velocity.copy();
    }
    
    public Vector2f getPosition() {
        return position.copy();
    }
    
    public Vector2f getVelocity() {
        return velocity.copy();
    }
    
    public void setPosition(Vector2f position) {
        this.position = position;
    }
    
    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }
    
    public void setVelocity(float x, float y) {
        this.velocity.x = x;
        this.velocity.y = y;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void paint(Graphics2D g) {
        float xInit = position.x - RADIUS;
        float yInit = position.y - RADIUS;
        
        g.setColor(color);
        g.fillArc((int) xInit, (int) yInit, (int) (2*RADIUS),(int) (2*RADIUS), 0, 360);
        g.setColor(Color.BLACK);
        g.drawArc((int) xInit, (int) yInit, (int) (2*RADIUS),(int) (2*RADIUS), 0, 360);
    }

    @Override
    public Shape getShape() {
        float xInit = position.x - RADIUS;
        float yInit = position.y - RADIUS;
        Shape circle = new Ellipse2D.Float((int) xInit, (int) yInit, (int) (2*RADIUS),(int) (2*RADIUS));
        return circle;
    }
    
}
