/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GAs;

import Game.Game;
import Geometry.Vector2f;
import Random.RandomNumbers;
import arkanoid.Program;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Vitor Rinaldini
 */
public class GAs {
    
    int population;
    float topPlayersRate = 0.1f;
    float mutationRate = 0.001f;
    float randomRate = 0.1f;
    
    int dnaSize = 25;
    int control_gap = 10;
    
    Generation currentGen = null;
    ArrayList<Game> games = new ArrayList();
    
    public GAs(int population) {
        this.population = population;
    }
    
    public void createFirstGeneration() {
        Generation gen = new Generation(0, population, dnaSize);
        currentGen = gen;
    }
    
    public void setGames(ArrayList<Game> games) {
        this.games.clear();
        this.games.addAll(games);
    }
    
    public void updateIAs() {
        Generation currentGen = getCurrentGeneration();
        for(int i = 0; i < games.size(); i++) {
            Membro currentMembro = currentGen.getMembro(i);
            Game currentGame = games.get(i);
            if(currentGame.isRunning()) {
                currentMembro.play(currentGame);
            }
        }
    }
    
    public Generation getCurrentGeneration() {
        return currentGen;
    }
    
    private Membro getRandomPonderadoMembro(ArrayList<Membro> membros) {
        RandomNumbers rand = RandomNumbers.getInstance();
        float sumProbabilities = 0;
        for(Membro top: membros) sumProbabilities += top.getScore();

        ArrayList<Float> probabilities = new ArrayList();
        for(Membro top: membros) probabilities.add(top.getScore()/sumProbabilities);

        float randomNumber = rand.getProbability();
        float aux = 0;
        for(int position = 0; position < probabilities.size(); position++) {
            float delta = probabilities.get(position);
            if(randomNumber >= aux && randomNumber <= aux + delta) {
                return membros.get(position);
            }
            aux += delta;
        }
        return null;
    }
    int test =0;
    ArrayList<Statistics> dados = new ArrayList();
    private Generation nextGeneration(int deltaDNA) {
        updateFitness();  
        RandomNumbers rand = RandomNumbers.getInstance();
        
        Generation currentGen = getCurrentGeneration();       
        System.out.println("Geração: "+currentGen.getNumber()+"; Score: "+ currentGen.getTotalScore());
        
        Statistics s = currentGen.getStatistics();
        
        Generation newGen = new Generation(currentGen.getNumber()+1);
        ArrayList<Membro> newMembros = new ArrayList();
            
        ArrayList<Membro> currentSortedMembros = (ArrayList<Membro>) currentGen.membros.clone();
        Collections.sort(currentSortedMembros);
        Membro bestMembro = currentSortedMembros.get(0);
        s.top1 = bestMembro.getCopy();
        s.top1.setScore(bestMembro.getScore());
        dados.add(s);
        
        
        if(bestMembro.getScore() >= 1.0f) {
            if(test > 20) {
                System.out.println("Encontrei a solução ótima!");
                saveData();
                System.exit(0);
            }
            test++;
        }

        System.out.println(currentSortedMembros.get(0).getScore());
        
        dnaSize += deltaDNA;
        //TopPlayers;
        int amountTopPelayers = (int) (topPlayersRate*currentSortedMembros.size());
        ArrayList<Membro> topPlayers = new ArrayList();
        for(int i = 0; i < amountTopPelayers; i++) {
            topPlayers.add(currentSortedMembros.get(i));
        }
        
        //Increase DNA dos tops;
        for(Membro topMembro: topPlayers) {
            topMembro.increaseDna(deltaDNA);
            newMembros.add(topMembro.getCopy());
        }
        
        //Crossover
        int amountMembrosCross = (int) (currentGen.size() - randomRate * currentGen.size());
        for(int i = amountTopPelayers; i < amountMembrosCross; i++) {
            
            
            Membro top1 = getRandomPonderadoMembro(topPlayers);
            ArrayList<Membro> aux = (ArrayList<Membro>) topPlayers.clone();
            aux.remove(top1);
            Membro top2 = getRandomPonderadoMembro(aux);
            
            DNA dna1 = top1.getDNA().getSubDna(0, dnaSize/2);
            DNA dna2 = top2.getDNA().getSubDna(dnaSize/2, dnaSize/2);
            dna1.addDNA(dna2);
            Membro newMembro = new Membro(dna1);
            newMembros.add(newMembro);
            
            /*if(rand.getRandomNumber(10) >= 5) {
                DNA dna1 = top1.getDNA().getCopy();
                DNA dna2 = top2.getDNA().getSubDna(top2.getDNA().size()-deltaDNA, deltaDNA);
                dna1.addDNA(dna2);
                Membro newMembro = new Membro(dna1);
                newMembros.add(newMembro);
            } else {
                DNA dna1 = top2.getDNA().getCopy();
                DNA dna2 = top1.getDNA().getSubDna(top2.getDNA().size()-deltaDNA, deltaDNA);
                dna1.addDNA(dna2);
                Membro newMembro = new Membro(dna1);
                newMembros.add(newMembro);
            }*/
            
        }
        
        //Mutação
        for(Membro membro: newMembros) {
            DNA dna = membro.getDNA();
            for(int position = 0; position < dna.size(); position++) {
                if(rand.getProbability() <= mutationRate) { //DANGER
                    dna.flip(position);
                }
            }
        }
        
        //Aleatorio
        int amountRandomPlayers = population - amountMembrosCross;
        for(int i = 0; i < amountRandomPlayers; i++) {
            newMembros.add(new Membro(dnaSize));
            
        } 
        
        newGen.addMembros(newMembros);
        System.out.println("Geração: "+newGen.getNumber());
        return newGen;
    }
    
    public int getDnaSize() {
        return dnaSize;
    }
    
    public void updateGeneration() {
        Generation nextGen = nextGeneration(control_gap);
        currentGen = nextGen;
    }
    
    public void updateFitness() {
       Generation currentGen = getCurrentGeneration();
       for(int i = 0; i < currentGen.size(); i++) {
           Game game = games.get(i);
           if(!game.isRunning()) {
               Membro membro = currentGen.getMembro(i);
               Vector2f ballPosition = game.ball.getPosition();
               Vector2f navePosition = game.nave.getPosition();
               
               float dist = Math.abs(ballPosition.x-navePosition.x);
               
               double time = game.getTime();
               int blocks = game.getBlocksAtingidos();
               
               float score = (float) (blocks/(1f*Program.numBlocksColuna*Program.numBlocksLine));
               membro.setScore(score);
               
               
           }
           
       }
    }
    
    private void saveData() {
        ArrayList<Float> score_total = new ArrayList();
        ArrayList<Float> score_average = new ArrayList();
        ArrayList<Float> score_top1 = new ArrayList();
        ArrayList<DNA> dna_top1 = new ArrayList();
        ArrayList<Integer> numDNA = new ArrayList();
        
        for(Statistics s: dados) {
            score_total.add(s.score_total);
            score_average.add(s.score_average);
            score_top1.add(s.top1.getScore());
            dna_top1.add(s.top1.getDNA());
            numDNA.add(s.top1.getDNA().getSize());
        }
        
        System.out.println(arrayToPythonString("score_total", score_total));
        System.out.println(arrayToPythonString("score_average", score_average));
        System.out.println(arrayToPythonString("score_top1", score_top1));
        System.out.println(arrayToPythonString("numDNA", numDNA));
        
        int numGen = 8;
        ArrayList<Integer> selectedGens = new ArrayList();
        int delta = dna_top1.size()/(numGen-2);
        for(int i = 0; i < numGen-2; i++) {
            selectedGens.add(i*delta);
        }
        selectedGens.add(dna_top1.size()-1);
        
        
        System.out.println(arrayToPythonString("selectedGens", selectedGens));
        System.out.print('[');
        for(int gen: selectedGens) {
            System.out.print(',');
            dna_top1.get(gen).print();
        }
        System.out.println(']');
        
        
    }
    
    private String arrayToPythonString(String varName, ArrayList<? extends Number> data) {
        String result = varName+" = [";
        for(int i = 0; i < data.size(); i++) {
            result += data.get(i)+"";
            if(i == data.size()-1) {
                result += "]";
            } else {
                result += ", ";
            }
        }
        return result;
    }
    
}
