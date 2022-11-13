/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package jueditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.GapContent;

/**
 *
 * @author sarin
 */
public class JUEditor extends JFrame {

    private static RandomAccessDocument doc;// = new RandomAccessDocument();
    private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    static final AbstractDocument.Content data = new MyContent();
    public static Document doc2 = new AbstractDocument(data) {

        private final Element root = new Element() {
            @Override
            public Document getDocument() {
                return doc2;
            }

            @Override
            public Element getParentElement() {
                return null;
            }

            @Override
            public String getName() {
                return "root";
            }

            @Override
            public AttributeSet getAttributes() {
                return null;
            }

            @Override
            public int getStartOffset() {
                return 0;
            }

            @Override
            public int getEndOffset() {
                return getLength();
            }

            @Override
            public int getElementIndex(int offset) {
                return -1;
            }

            @Override
            public int getElementCount() {
                return getLength();
            }

            @Override
            public Element getElement(int index) {
                return this;
            }

            @Override
            public boolean isLeaf() {
                return true;
            }
        };

        @Override
        public Element getDefaultRootElement() {
            return root;
        }

        @Override
        public Element getParagraphElement(int pos) {
            return root;
        }
    };

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JUEditor editorFrame = new JUEditor();
        editorFrame.setBounds(100, 200, 1024, 768);
        editorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JFileChooser fc = new JFileChooser();
        int fcres = fc.showOpenDialog(editorFrame);
        if (fcres == JFileChooser.APPROVE_OPTION) {
            doc = new  RandomAccessDocument(fc.getSelectedFile());
            RandomUI.doc = doc;
            editorFrame.setTitle(doc.getDocumentName());
            UIManager.getDefaults().put("RandomUI", "jueditor.RandomUI");
            textArea = new RandomTextArea(doc);
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 16));

            try {
                KeyStroke ks = KeyStroke.getKeyStroke("control C");
                textArea.getInputMap().put(ks, new AbstractAction("copy") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        StringSelection stringSelection = new StringSelection(textArea.getSelectedText());
                        clipboard.setContents(stringSelection, null);
                    }
                });
            } catch (Exception ex) {
                Logger.getLogger(JUEditor.class.getName()).log(Level.SEVERE, null, ex);
            }

            final JScrollPane sp = new RandomScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            sp.setVisible(true);
            editorFrame.add(sp);

            editorFrame.setVisible(true);
        }
    }
    private static JTextArea textArea;

}
