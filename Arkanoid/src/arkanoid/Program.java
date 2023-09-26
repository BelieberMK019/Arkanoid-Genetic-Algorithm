/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arkanoid;

import GAs.GAs;
import Game.Ball;
import Game.Block;
import Game.Game;
import Geometry.Vector2f;
import Random.RandomNumbers;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Vitor Rinaldini
 */
public class Program extends JFrame implements Runnable, KeyListener{
    
    ArrayList<Game> jogos = new ArrayList();
    GamesPanel painelDeJogos;
    
    public static int width = 500;
    public static int height = 800;
    public static int numGames = 200;
    
    public static final int numBlocksColuna = 6;
    public static final int numBlocksLine = 12;
    
    GAs ga;
    
    public Program() {
        setBounds(100, 100, width, height);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
        addKeyListener(this);
        setTitle("Simulação");
        setFocusable(true);
        painelDeJogos = new GamesPanel();
        ga = new GAs(numGames);
        
        ga.createFirstGeneration();
        painelDeJogos.setGA(ga);
        
        resetGames();        
        
        add(painelDeJogos);
        
        Thread t1 = new Thread(this);
        t1.start();
        
        show();
    }
    
    private int scaleSimulation = 2;
    private boolean running = false;
    
    @Override
    public void run() {
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        
        long lastFpsTime = lastLoopTime;
        int fps = 0;
        int ticks = 500;
        running = true;
        while (running){
           // work out how long its been since the last update, this
           // will be used to calculate how far the entities should
           // move this loop
           long now = System.nanoTime();
           long updateLength = now - lastLoopTime;
           lastLoopTime = now;
           double delta = updateLength / ((double)OPTIMAL_TIME);

           // update the frame counter
           lastFpsTime += updateLength;
           fps++;

           // update our FPS counter if a second has passed since
           // we last recorded
           if (lastFpsTime >= 1000000000)
           {
              System.out.println("(FPS: "+fps+")");
              lastFpsTime = 0;
              fps = 0;
           }

           // update the game logic
           for(int i = 0; i < scaleSimulation; i++) {
               ticks++;
               update(1f);
               
               if(ticks >= 5) {
                    ticks = 0;
                    ga.updateIAs();
                }
           }
           

           // draw everyting
           if(scaleSimulation <= 10) render();

           // we want each frame to take 10 milliseconds, to do this
           // we've recorded when we started the frame. We add 10 milliseconds
           // to this and then factor in the current time to give 
           // us our final value to wait for
           // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
           try{
               double timeToSleep =(lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000;
               if(timeToSleep > 0)Thread.sleep((long) timeToSleep);
           } catch (Exception ex) {
                Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void update(double deltaTime) {
        for (Game game: jogos) {
            if(game.isRunning()) game.updateEntities((float) deltaTime);
        }
        
        boolean update = true;
        for(Game game : jogos) {
            if(game.isRunning()) {
                 update = false;
                 break;
            }
        }
        
        if(update) {
            reproduzir();
            resetGames();
            for(Game game : jogos) game.setRunning(true);
        }
    }
    
    public static ArrayList<Game> generateNewGames() {
        RandomNumbers rand = RandomNumbers.getInstance();
        Vector2f ballPosition = new Vector2f(300, 600);
        
        Vector2f ballVelocity = new Vector2f(5,-5);
        ballVelocity.normalize();
        ballVelocity = ballVelocity.multiply(Ball.SPEED);
        
        Vector2f navePosition = new Vector2f(width/2, 700);
        
        ArrayList<Block> blocks = new ArrayList();
        for(int i = 0; i < numBlocksColuna; i++) {
            float xCenter = Block.WIDTH/2 + Block.WIDTH*i + 40*(i+1);
            for(int j = 0; j < numBlocksLine; j++) {
                float yCenter = Block.HEIGHT/2 + Block.HEIGHT*j + 40;
                blocks.add(new Block(new Vector2f(xCenter, yCenter)));
            }
        }
        
        ArrayList<Game> games = new ArrayList();
        for(int i = 0; i < numGames; i++) {
            Game game = new Game(ballPosition, ballVelocity, navePosition, width, height);
            Color color = new Color(rand.getProbability(), rand.getProbability(), rand.getProbability());
            game.setColor(color, color);
            game.addBlocks(blocks);
            game.setRunning(true);
            games.add(game);
        }
        
        return games;
    }
    
    public void resetGames() {
        painelDeJogos.resetList();
        jogos.clear();
                  
        ArrayList<Game> gamesGerados = Program.generateNewGames();
        jogos.addAll(gamesGerados);
        
        painelDeJogos.addAll(jogos);
        ga.setGames(jogos);
    }
    
    public void reproduzir() {
        ga.updateGeneration();
    }
    
    public void render() {
        painelDeJogos.repaint(); 
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W) {
            scaleSimulation++;      
        } else if(code == KeyEvent.VK_S) {
            if(scaleSimulation - 1 > 0)scaleSimulation--;
        } else if(code == KeyEvent.VK_E) {
            scaleSimulation += 100;
        } else if(code == KeyEvent.VK_Q) {
            scaleSimulation = 1;
        }
        System.out.println("Scale: "+ scaleSimulation);
        
    }

}
