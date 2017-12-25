/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hackcomputer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 *
 * @author Gustavo Rojas
 */
public class Timer {
    
    private Timeline timer;
    private double tic;
    
    public Timer(EventHandler<ActionEvent> e, double tic){
        
        this.tic = tic;
        KeyFrame kf = new KeyFrame(Duration.seconds(this.tic),e);
        this.timer = new Timeline(kf);
        this.timer.setCycleCount(Timeline.INDEFINITE);
        
    
    }
    
    public void start(){
        this.timer.play();
    }
    
    public void pause(){
        this.timer.pause();
    }
    
    public void stop(){
        this.timer.stop();
    }
    
     public void setTic(double tic){
        this.timer.setRate(tic);
        
    }

    
}
