/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jueditor;

import javax.swing.text.BadLocationException;
import javax.swing.text.GapContent;
import javax.swing.undo.UndoableEdit;

/**
 *
 * @author sarin
 */
public class MyContent extends GapContent {

    @Override
    public String getString(int where, int len) throws BadLocationException {
        return super.getString(where, len); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public UndoableEdit insertString(int where, String str) throws BadLocationException {
        return super.insertString(where, str); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
    
}
