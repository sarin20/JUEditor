/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jueditor;

import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.PlainView;
import javax.swing.text.Utilities;
import javax.swing.text.View;


/**
 *
 * @author sarin
 */
public class RandomView extends PlainView {

    private RandomAccessDocument doc;

    public RandomView(Element elem, RandomAccessDocument doc) {
        super(elem);
        this.doc = doc;
    }

    private Element stubE;

    @Override
    public Document getDocument() {
        return super.getDocument(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public Element getElement() {
        if (stubE == null) {
            return super.getElement();
        } else {
            return stubE;
        }
    }

    public float getPreferredSpan(int axis) {
        updateMetrics();
        switch (axis) {
            case View.X_AXIS:
                return getLineWidth(doc.getElementCollection().getLongestElement());
            case View.Y_AXIS:
                return getElement().getElementCount() * metrics.getHeight();
            default:
                throw new IllegalArgumentException("Invalid axis: " + axis);
        }
    }

    @Override
    protected synchronized void updateMetrics() {
        stubE = new Element() {
            @Override
            public Document getDocument() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public Element getParentElement() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public String getName() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public AttributeSet getAttributes() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public int getStartOffset() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public int getEndOffset() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public int getElementIndex(int offset) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public int getElementCount() {
                return 1;
            }

            @Override
            public Element getElement(int index) {
                return doc.getElementCollection().getLongestElement();
            }

            @Override
            public boolean isLeaf() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        };
        super.updateMetrics();
        stubE = null;
    }

    private float getLineWidth(Element longLine) {
        return (float)(metrics.getFont().getStringBounds(new char[]{' '}, 0, 1, metrics.getFontRenderContext()).getWidth() * (longLine.getEndOffset() - longLine.getStartOffset() ));
    }

}
