/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jueditor;

import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 *
 * @author sarin
 */
public class InsertEvent implements DocumentEvent {

    private final int offset;
    private final String str;
    private final Document document;

    public InsertEvent(int offset, String str, Document document) {
        this.offset = offset;
        this.str = str;
        this.document = document;
    }

    public class CE implements ElementChange {

        private final Element elem;

        private CE(Element elem) {
            this.elem = elem;
        }

        @Override
        public Element getElement() {
            return elem;
        }

        @Override
        public int getIndex() {
            return elem.getStartOffset();
        }

        @Override
        public Element[] getChildrenRemoved() {
            return new Element[0];
        }

        @Override
        public Element[] getChildrenAdded() {
            return new Element[]{elem};
        }

    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public int getLength() {
        return str.length();
    }

    @Override
    public Document getDocument() {
        return document;
    }

    @Override
    public DocumentEvent.EventType getType() {
        return DocumentEvent.EventType.INSERT;
    }

    @Override
    public DocumentEvent.ElementChange getChange(Element elem) {
        return new CE(elem);
    }

}
