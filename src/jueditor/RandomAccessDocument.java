package jueditor;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;

/**
 *
 * @author sarin
 */
public class RandomAccessDocument implements Document {

    private List<Character> chars = new ArrayList<>();

    private RandomAccessElementCollection elementCollection = new RandomAccessElementCollection(this);
    private RandomAccessFile raf;
    private File file = new File("test/test.txt");

    private boolean paused;
    
    public String getDocumentName() {
        return file.getAbsolutePath();
    }

    public RandomAccessDocument() {
        load();
    }

    public RandomAccessDocument(File file) {

        load(file);
    }

    public void load(File f) {
        this.file = f;
        load();
    }

    public void load() {
        int bsp = 0;
        int bep = -1;
        try {
            BufferedInputStream str = new BufferedInputStream(new FileInputStream(file), 1024 * 1024);

            final byte[] buff = new byte[1024];
            int dr = str.read(buff);
            int b = buff[0];
            int charPos = 0;
            String sb = new String(buff);
            int cp = 0;
            int i = 1;
            StringBuilder bl = new StringBuilder();
            while (dr > 0) {
                if (b == '\n') {

                    int size = cp - bep;
                    if (size > i) {
                        size = i;
                    }
                    final byte[] bb = new byte[size];
                    System.arraycopy(buff, i - size, bb, 0, bb.length);
                    bl.append(new String(bb, charsetName));

                    bep = cp;
                    elementCollection.createBreak(cp, new String(bl));
                    bsp = cp + 1;
                    bl = new StringBuilder();
                    if (elementCollection.size() > 999) {
                        //      paused = true;
                        //    break;
                    }
                } else {

                }
                b = buff[i];
                i++;
                cp++;
                if (i == dr) {
                    int size = cp - bep;
                    if (size > i) {
                        size = i;
                    }
                    final byte[] bb = new byte[size];
                    System.arraycopy(buff, i - size, bb, 0, bb.length);
                    bl.append(new String(bb, charsetName));
                    i = 0;
                    dr = str.read(buff);
                }
            }
            str.close();
            raf = new RandomAccessFile(file, "r");
            if (!paused) {
                final byte[] bb = new byte[buff.length - i];
                System.arraycopy(buff, i, bb, 0, bb.length);
                //   elementCollection.createBreak(bsp, cp);
                elementCollection.finalBreak(new String(bb));
            }
            // FileChannel c = raf.getChannel();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RandomAccessDocument.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RandomAccessDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private final String charsetName = "UTF-8";

    
    @Override
    public int getLength() {
        return (int) file.length();
    }

    private List<DocumentListener> documentListeners = new ArrayList<>();
    protected EventListenerList listenerList = new EventListenerList();

    @Override
    public void addDocumentListener(DocumentListener listener) {
//        documentListeners.add(listener);
        listenerList.add(DocumentListener.class, listener);
    }

    @Override
    public void removeDocumentListener(DocumentListener listener) {
        listenerList.remove(DocumentListener.class, listener);
    }

    @Override
    public void addUndoableEditListener(UndoableEditListener listener) {
        listenerList.add(UndoableEditListener.class, listener);
    }

    @Override
    public void removeUndoableEditListener(UndoableEditListener listener) {
        listenerList.remove(UndoableEditListener.class, listener);
    }

    private Map<Object, Object> properties = new ConcurrentHashMap<>();

    @Override
    public Object getProperty(Object key) {
        return properties.get(key);
    }

    @Override
    public void putProperty(Object key, Object value) {
        properties.put(key, value);
    }

    @Override
    public void remove(int offs, int len) throws BadLocationException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        System.out.println(str);
        for (int i = 0; i < str.length(); i++) {
            chars.add(str.charAt(i));
        }
        for (Object object : listenerList.getListenerList()) {
            if (object instanceof DocumentListener) {
                ((DocumentListener) object).insertUpdate(new InsertEvent(offset, str, this));
            }
        }
    }

    @Override
    public String getText(int offset, int length) throws BadLocationException {
        String s = readTextFromFile(offset, length);
        return s;
    }

    private String readTextFromFile(int offset, int length) {
        try {
            RandomAccessElementCollection.ByteBlockOffset byteOffset = elementCollection.charOffsetToByteOffset(offset);

            raf.seek(byteOffset.blockStartBytePos);
            if (byteOffset.charDistance > 0) {
                byte[] b = new byte[byteOffset.charDistance * 4];
                int nb = raf.read();
                int br = 0;
                while (nb > -1 && br < b.length) {
                    b[br] = (byte) nb;
                    nb = raf.read();
                    br++;
                }

                if (br > 0) {
                    String s = new String(b, Charset.forName("utf-8"));
                    String substring = s.substring(0, byteOffset.charDistance);
                    raf.seek(byteOffset.blockStartBytePos + substring.getBytes("utf-8").length);
                } else {
                    return null;
                }
            }
            byte[] b = new byte[length * 4];
            int nb = raf.read();
            int br = 0;
            while (nb > -1 && br < b.length) {
                b[br] = (byte) nb;
                nb = raf.read();
                br++;
            }

            if (br > 0) {
                String s = new String(b, Charset.forName("utf-8"));
                return s.substring(0, length);
            } else {
                return "";
            }
        } catch (IOException ex) {
            Logger.getLogger(RandomAccessDocument.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void getText(int offset, int length, Segment txt) throws BadLocationException {
        if (length > 0) {
            String s = getText(offset, length);
            char[] ca = s.toCharArray();
            
            txt.array = ca;
            txt.count = ca.length;
            //   txt.copy
            txt.offset = 0;
        }
    }

    @Override
    public Position getStartPosition() {
        return createPosition(0);
    }

    @Override
    public Position getEndPosition() {
        return createPosition(getLength());
    }

    private Map<Integer, Position> positionCash = new HashMap<>();

    @Override
    public Position createPosition(final int offs) {
        if (!positionCash.containsKey(offs)) {
            positionCash.put(offs, new Position() {
                @Override
                public int getOffset() {
                    return offs;
                }
            });
        }
        return positionCash.get(offs);
    }

    @Override
    public Element[] getRootElements() {
        return rootElements;
    }
    private Element[] rootElements = new Element[]{getDefaultRootElement()};

    @Override
    public Element getDefaultRootElement() {
        return root;
    }
    private final Element root = new Element() {
        @Override
        public Document getDocument() {
            return RandomAccessDocument.this;
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
            return elementCollection.getElementAtOffset(offset);
        }

        @Override
        public int getElementCount() {
            return elementCollection.size();
        }

        @Override
        public Element getElement(int index) {
            if (paused && index > elementCollection.size() / 3) {
                //     continueLoad();
            }
            return elementCollection.get(index);
        }

        @Override
        public boolean isLeaf() {
            return false;
        }
    };

    private transient int numReaders;
    private transient Thread currWriter;

    public final synchronized void readLock() {
        try {
            while (currWriter != null) {
                if (currWriter == Thread.currentThread()) {
                    // writer has full read access.... may try to acquire
                    // lock in notification
                    return;
                }
                wait();
            }
            numReaders += 1;
        } catch (InterruptedException e) {
            throw new Error("Interrupted attempt to acquire read lock");
        }
    }

    public final synchronized void readUnlock() {
        if (currWriter == Thread.currentThread()) {
            // writer has full read access.... may try to acquire
            // lock in notification
            return;
        }
        if (numReaders <= 0) {
            throw new RuntimeException();
        }
        numReaders -= 1;
        notify();
    }

    @Override
    public void render(Runnable r) {
        readLock();
        try {
            r.run();
        } finally {
            readUnlock();
        }
    }

    public RandomAccessElementCollection getElementCollection() {
        return elementCollection;
    }

}
