/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jueditor;

import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 *
 * @author sarin
 */
public class RandomAccessElementCollection extends AbstractList<Element> {

    private final List<Break> lineBreaks = new LinkedList<>();
    private RandomAccessDocument doc;

    private Element longestElement;
    private int lonLen = 0;

    @Override
    public Iterator<Element> iterator() {
        return new Iterator<Element>() {

            private final Iterator<Break> lb;

            {
                this.lb = lineBreaks.iterator();
            }

            @Override
            public boolean hasNext() {
                return lb.hasNext();
            }

            @Override
            public Element next() {
                Break next = lb.next();
                return new RandomAccessElement(next, doc);
            }
        };
    }

    @Override
    public int size() {
        return lineBreaks.size();
    }

    public RandomAccessElementCollection(RandomAccessDocument doc) {
        this.doc = doc;
    }

    public void createBreak(int pos, int endPos, int cp, int cep) {
        final Break b = new Break(pos, endPos, cp, cep);
        lineBreaks.add(b);
        int l = endPos - pos;
        if (l > lonLen) {
            lonLen = l;
            longestElement = new RandomAccessElement(b, doc);
            b.e = longestElement;
        }
    }

    @Override
    public Element get(int index) {
        Break br = lineBreaks.get(index);
        if (br.e == null) {
            br.e = new RandomAccessElement(br, doc);
        }
        return br.e;
    }
    
    private int searchOffset(int ln, int tn, int offset) {
        Break low = lineBreaks.get(ln);
        Break top = lineBreaks.get(tn);
        if (low.within(offset)) { 
            return ln;
        }
        if (top.within(offset)) {
            return tn;
        }
        if (low.before(offset) || top.after(offset)) {
            return -1;
        }
        int nn = ln + (tn - ln) / 2;
        Break nb = lineBreaks.get(nn);
        if (nb.getCpos() <= offset) {
            return searchOffset(nn, tn, offset);
        } else {
            return searchOffset(ln, nn, offset);
        }
    }

    public int getElementAtOffset(int offset) {
        int lown = 0;
        
        int topn = lineBreaks.size() - 1;
        Break top = lineBreaks.get(topn);
        return searchOffset(lown, topn, offset);
        /*int res = -1;
        for (int i = 0; i < lineBreaks.size(); i++) {
        Break br = lineBreaks.get(i);
        if (offset >= br.getCpos() && offset < br.getCendPos()) {
        return i;
        }
        }
        return res;*/
    }
    
    public void setString(int offset, String s) {
        int p0 = getElementAtOffset(offset);
        Break br = lineBreaks.get(p0);
        int l = 0;
        if (l >= s.length() || l + br.endPos - br.pos > s.length()) {
            return;
        }
        br.pulledString = new WeakReference<>(s.substring(0, br.getCendPos() - br.getCpos()));
        l = br.endPos - br.pos;
        while (l < s.length() - 1) {
            p0++;
            if (p0 >= lineBreaks.size()) {
                return;
            }
            br = lineBreaks.get(p0);
            if (l >= s.length() || l + br.getCendPos() - br.pos > s.length()) {
                return;
            }
            if (s.length() > l + br.getCendPos() - br.getCpos()) {
                br.pulledString = new WeakReference<>(s.substring(l + 1, l + br.getCendPos() - br.getCpos()));
            }
            l += br.getCendPos() - br.getCpos();
        }
    }

    public String getString(int offset, int length) {
        int p0 = getElementAtOffset(offset);
        Break br = lineBreaks.get(p0);
        if (br.pulledString == null || br.pulledString.get() == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(br.pulledString.get());
        while (sb.length() < length - 1) {
            p0++;
            br = lineBreaks.get(p0);
            if (br.pulledString == null || br.pulledString.get() == null) {
                return sb.toString();
            }
            sb.append(br.pulledString.get());
        }
        return sb.toString();
    }
    
    private int bpos = 0;
    private int cpos = 0;

    public void createBreak(int j, String s) {
        createBreak(bpos, j, cpos, cpos + s.length());
        bpos = j + 1;
        cpos += s.length() + 1;
    }

    public void finalBreak(String s) {
        createBreak(doc.getLength(), s);
    }
    
    public static class ByteBlockOffset {
        
        long blockStartBytePos;
        int charDistance;

        public ByteBlockOffset(long blockStartBytePos, int charDistance) {
            this.blockStartBytePos = blockStartBytePos;
            this.charDistance = charDistance;
        }
        
        
        
    }

    public ByteBlockOffset charOffsetToByteOffset(int offset) {
        for (Break b : lineBreaks) {
            if (b.cpos == offset) {
                return new ByteBlockOffset(b.pos, 0);
            }
            if (b.cpos < offset && offset < b.cendPos) {
                return new ByteBlockOffset(b.pos, offset - b.cpos);
            }
        }
        return null;
    }

    public static class Break {

        private int pos;
        private int endPos;
        private int cpos;
        private int cendPos;
        private Element e;
        private WeakReference<String> pulledString;
        
        public boolean within(int offset) {
            return offset >= getCpos() && offset < getCendPos();
        }
        public boolean before(int offset) {
            return offset < getCpos();
        }
        public boolean after(int offset) {
            return offset >= getCendPos();
        }

        public Break(int pos, int endPos, int cpos, int cendPos) {
            this.pos = pos;
            this.endPos = endPos;
            this.cpos = cpos;
            this.cendPos = cendPos;
        }

        

        public int getPos() {
            return pos;
        }

        public int getEndPos() {
            return endPos;
        }

        public int getCpos() {
            return cpos;
        }

        public int getCendPos() {
            return cendPos;
        }
        
        public String getString() {
            return pulledString == null ? null : pulledString.get();
        }

        @Override
        public String toString() {
            return "Break{" + getCpos() + ' ' + getCendPos() + '}';
        }
        
        

    }

    public Element getLongestElement() {
        return longestElement;
    }

}
