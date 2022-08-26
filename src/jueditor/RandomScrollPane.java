/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jueditor;

import java.awt.Component;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RunnableFuture;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeListener;

/**
 *
 * @author sarin
 */
public class RandomScrollPane extends JScrollPane {

    public RandomScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
        super(view, vsbPolicy, hsbPolicy);
    }

    public RandomScrollPane(Component view) {
        super(view);
    }

    public RandomScrollPane(int vsbPolicy, int hsbPolicy) {
        super(vsbPolicy, hsbPolicy);
    }

    public RandomScrollPane() {
    }
    
    protected class DelayedSB extends ScrollBar {

        public DelayedSB(int orientation) {
            super(orientation);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            if (innerValue != getValue()) {
                                DelayedSB.super.setValue(innerValue);
                            }
                        }
                    
                    });
                }
            };
            new Timer().schedule(timerTask, 50, 50);
        }
        
        int innerValue;

        @Override
        public void setValue(int value) {
            innerValue = value;
            //super.setValue(value); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        }

        
        
    }

    @Override
    public JScrollBar createVerticalScrollBar() {
        return new DelayedSB(JScrollBar.VERTICAL);
    }

    
    
    
}
