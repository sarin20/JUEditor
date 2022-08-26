/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jueditor;

import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 *
 * @author sarin
 */
public class RandomAccessElement implements Element{
    
    private RandomAccessElementCollection.Break br;
    private RandomAccessDocument doc;
    

    public RandomAccessElement(RandomAccessElementCollection.Break br, RandomAccessDocument doc) {
        this.br = br;
        this.doc = doc;
    }
    
    

    @Override
    public Document getDocument() {
        return doc;
    }

    @Override
    public Element getParentElement() {
        return doc.getRootElements()[0];
    }

    @Override
    public String getName() {
        return "hello";
    }

    @Override
    public AttributeSet getAttributes() {
        return null;
    }

    @Override
    public int getStartOffset() {
        return br.getCpos();
    }

    @Override
    public int getEndOffset() {
        return br.getCendPos();
    }

    @Override
    public int getElementIndex(int offset) {
        return 0;
    }

    @Override
    public int getElementCount() {
        return 0;
    }

    @Override
    public Element getElement(int index) {
        return null;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }
    
}
