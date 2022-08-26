/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jueditor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.util.Objects;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.text.Document;

/**
 *
 * @author sarin
 */
public class RandomTextArea extends JTextArea {
    
    static {
        UIManager.getDefaults().put("RandomUI", "jueditor.RandomUI");
    }

    public RandomTextArea(Document doc) {
        super(doc);
        setCaret(new RandomCaret());
      //  setHighlighter(null);
    }

    @Override
    public String getUIClassID() {
        return "RandomUI";
    }


    
    
    
    
    
}
