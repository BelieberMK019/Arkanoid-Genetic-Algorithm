/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import Geometry.Vector2f;
import Interface.Drawn;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Vitor Rinaldini
 */
public class Game implements Drawn{
    
    public Canvas canvas;
    public Ball ball;
    public Nave nave;
    public ArrayList<Block> blocks;
    
    private boolean running = false;
    private float width, height;
    
    public Game(Vector2f ballPosition, Vector2f ballVelocity, Vector2f playerPosition, float width, float height) {
        ball = new Ball(ballPosition, ballVelocity);
        nave = new Nave(playerPosition);
        blocks = new ArrayList();
        
        canvas = new Canvas();
        canvas.addObj(ball);
        canvas.addObj(nave);
        
        this.width = width;
        this.height = height;
    }
    
    private double startTime;
    private double stopTime;
    
    public double getTime() {
        return stopTime-startTime;
    }
    
    public void setColor(Color ballColor, Color naveColor) {
        nave.setColor(naveColor);
        ball.setColor(ballColor);
    }
    
    public void setRunning(boolean running) {
        startTime = System.nanoTime();
        this.running = running;
    }
    
    public boolean isRunning() {
        return this.running;
    }
    
    public void addBlocks(ArrayList<Block> blocks) {
        this.blocks.addAll((Collection<? extends Block>) blocks.clone());
        this.canvas.addObjs((ArrayList<? extends Drawn>) blocks.clone());
    }
    
    public void updateEntities(float deltaTime) {
        updateNave(deltaTime);
        updateBallBounds(deltaTime);
        
        collisionBallNave();
        collisionBallBlocks();
    }
   
    
    private void updateBallBounds(float deltaTime) {
        Vector2f ballPosition = ball.getPosition();
        Vector2f ballVelocity = ball.getVelocity();
        
        Vector2f displacement = ballVelocity.multiply(deltaTime);
        Vector2f newBallPosition = ballPosition.sum(displacement);
        
        if(newBallPosition.x - Ball.RADIUS <= 0) {
            newBallPosition.x = Math.abs(newBallPosition.x);
            ballVelocity.x = Math.abs(ballVelocity.x);
        } else if(newBallPosition.x + Ball.RADIUS >= width-12) {
            newBallPosition.x = width - Math.abs(newBallPosition.x + Ball.RADIUS - width-12);
            ballVelocity.x = -Math.abs(ballVelocity.x);
        }
        
        if(newBallPosition.y - Ball.RADIUS <= 0) {
            newBallPosition.y = Math.abs(newBallPosition.y);
            ballVelocity.y = Math.abs(ballVelocity.y);
        } else if(newBallPosition.y + Ball.RADIUS >= height) {
            stopTime = System.nanoTime();
            running = false;
        }
        
        ball.setPosition(newBallPosition);
        ball.setVelocity(ballVelocity);
    }
    
    private void updateNave(float deltaTime) {
        Vector2f position = nave.getPosition();
        Vector2f dispalcement = new Vector2f(nave.getDirection()*Nave.SPEED*deltaTime, 0);
        Vector2f newPosition = position.sum(dispalcement);
        
        if(newPosition.x-Nave.WIDTH/2 > 0 && newPosition.x+Nave.WIDTH/2 < width) {
            nave.setPosition(newPosition);
        }
    }
    
    private void collisionBallNave() {
        Shape ballShape = ball.getShape();
        Shape naveShape = nave.getShape();
        
        if(ballShape.intersects((Rectangle2D) naveShape)) {
            Vector2f ballVelocity = ball.getVelocity();
            ballVelocity.y = -Math.abs(ballVelocity.y);
            ball.setVelocity(ballVelocity);
        }
        
    }
    
    int blocksAtingidos = 0;
    
    public int getBlocksAtingidos() {
        return blocksAtingidos;
    }
    
    private void collisionBallBlocks() {
        Shape ballShape = ball.getShape();
        for(Block block: blocks) {
            Shape blockShape = block.getShape();
            if(ballShape.intersects((Rectangle2D) blockShape)) {
                Vector2f ballPosition = ball.getPosition();
                Vector2f ballVelocity = ball.getVelocity();
                Vector2f blockPosition = block.getPosition();
                
                if(ballPosition.x <= blockPosition.x - Block.WIDTH/2) {
                    ballVelocity.x = - Math.abs(ballVelocity.x);
                } else if(ballPosition.x >= blockPosition.x + Block.WIDTH/2) {
                    ballVelocity.x = Math.abs(ballVelocity.x);
                }
                
                if(ballPosition.y <= blockPosition.y - Block.HEIGHT/2) {
                    ballVelocity.y = - Math.abs(ballVelocity.y);
                } else if(ballPosition.y >= blockPosition.y + Block.HEIGHT/2) {
                    ballVelocity.y = Math.abs(ballVelocity.y);
                }
                blocksAtingidos++;
                ball.setVelocity(ballVelocity);
                blocks.remove(block);
                canvas.removeObj(block);
                return;
            }
        }
    }
    

    @Override
    public void paint(Graphics2D g) {
        canvas.paint(g);
    }
    
}
