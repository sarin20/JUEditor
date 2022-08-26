/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jueditor;

import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextAreaUI;
import javax.swing.text.Element;
import javax.swing.text.PlainView;
import javax.swing.text.View;

/**
 *
 * @author sarin
 */
public class RandomUI extends BasicTextAreaUI {
    
    public static RandomAccessDocument doc;

    
    @Override
    public View create(Element elem) {
        return new RandomView(elem, doc);
        
    }

    
    public static ComponentUI createUI(JComponent ta) {
        return new RandomUI();
    }   
    
    
}
