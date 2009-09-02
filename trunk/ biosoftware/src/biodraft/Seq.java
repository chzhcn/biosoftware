/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biodraft;

import java.util.ArrayList;

/**
 *
 * @author chzhcn
 */
public class Seq {

    protected ArrayList<Character> seq;

    public Seq() {
        seq = new ArrayList<Character>();
    }

    public Seq(ArrayList<Character> seq) {
//            seq = new ArrayList<Character>();
        this.seq = seq;
    }

    public ArrayList<Character> getSeq() {
        return seq;
    }

    public void add(char c) {
        seq.add(c);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char c : seq) {
            sb.append(c);
        }
        return sb.toString();
    }
}
