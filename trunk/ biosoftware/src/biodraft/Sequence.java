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
public class Sequence {
     private ArrayList<Character> seq;/* = new ArrayList<Character>();*/

     /**
      *
      * @return
      */
     public ArrayList<Character> getSeq() {
            return seq;
      }

     /**
      *
      */
     public Sequence() {
            seq = new ArrayList<Character>();
      }

     /**
      *
      * @param c
      */
     public void add(char c) {
            seq.add(c);
      }
}
