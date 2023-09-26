/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arkanoid;

import GAs.GAs;
import GAs.Generation;
import Game.Game;
import Interface.Drawn;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Vitor Rinaldini
 */
public class GamesPanel extends JPanel{
    Font font = new Font("Arial", Font.ITALIC, 20);
    ArrayList<Game> games = new ArrayList();
    GAs ga = null;
    public GamesPanel(ArrayList<Game> games) {
        this.games.addAll(games);
    }
    
    public GamesPanel() {
    }
    
    public void setGA(GAs ga) {
        this.ga = ga;
    }
    
    public void resetList() {
        games.clear();
    }
    
    public void addGame(Game game) {
        games.add(game);
    }
    
    public void addAll(ArrayList<Game> game) {
        games.addAll(game);
    } 
    
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        Graphics2D g2d = (Graphics2D) g;
        for(Game game: games) {
            if(game.isRunning()) game.paint(g2d);
        }
        if(ga != null) {
            Generation gen = ga.getCurrentGeneration();
            g2d.setColor(Color.BLACK);
            g2d.setFont(font);
            g2d.drawString("Geração: "+ga.getCurrentGeneration().getNumber(), 10, 20);
        }
        
        g.dispose();
    }
        
}
