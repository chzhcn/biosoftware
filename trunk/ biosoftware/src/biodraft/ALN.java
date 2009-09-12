/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biodraft;

import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author chzhcn
 */
public class ALN {

//    private ArrayList<Character> charSeq;
    private String alnString;
//      private ArrayList<Character> dataSeq;
    private int numOfSeqs;
    private SeqSet seqSet;

    public String getAlnString() {
        return alnString;
    }

    public int getNumOfSeqs() {
        return numOfSeqs;
    }

    public int FilterHeader() {
        int enterCounter = 0;

        int i = 0;
        int size = alnString.length();
        for (i = 0; i < size; i++) {
            char c = alnString.charAt(i);
            if (c == '\n') {
                enterCounter++;
            }
            if (enterCounter == 3) {
                break;
            }
        }

        int dataBegin = i + 1;
        return dataBegin;
//            return enterCounter+1;
//            return (ArrayList<Character>) charSeq.subList(dataBegin, size);
//            dataSeq = (ArrayList<Character>) charSeq.subList(dataBegin, size);
    }

//      void parseToSeqs() {
//
//      }
    public ALN(BufferedReader in, int numOfSeqs) throws IOException {
//        BufferedReader in = new BufferedReader(new FileReader(file));
        StringBuffer sb = new StringBuffer(2048);
//        charSeq = new ArrayList<Character>();
        this.numOfSeqs = numOfSeqs;
        int c;
        while ((c = in.read()) != -1) {
//            charSeq.add((char) c);
            sb.append((char) c);
        }
        alnString = sb.toString();
        seqSet = new SeqSet(this);
    }

    public SeqSet getSeqSet() {
        return seqSet;
    }
}
