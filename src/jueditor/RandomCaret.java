/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jueditor;

import javax.swing.text.DefaultCaret;

/**
 *
 * @author sarin
 */
public class RandomCaret extends DefaultCaret {

    public RandomCaret() {
        super();
        
    }

    @Override
    public int getMark() {
        return getDot();// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    
    
    
    
}
