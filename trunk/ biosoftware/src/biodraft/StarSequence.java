/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biodraft;

/**
 *
 * @author chzhcn
 */
public class StarSequence extends Seq {

//      private ArrayList<Character> starSeq;
    private PrimerList primerList;
//      private PCRList pcrList;

    public StarSequence(String s) {
//            seq = new ArrayList<Character>();
        super(s);
    }

//    public StarSequence(ArrayList<Character> arrayC) {
//        super(arrayC);
//    }
//      public void add(char c) {
//            seq.add(c);
//      }
//    boolean inStar = false;
//    int start = 0, end = 0;
    public void idenPrimer(int primerLength) {
        boolean inStar = false;
        int start = 0, end = 0;
        primerList = new PrimerList();
        for (int i = 0; i < seq.length(); i++) {
            char c = seq.charAt(i);
            if (c == '*') {
                if (inStar == false) {
                    inStar = true;
                    start = i;
//                              num = 0;
                }
            } else {
                if (inStar == true) {
                    inStar = false;
                    end = --i;
                    if (end - start + 1 >= primerLength) {
                        primerList.add(start, end);
                    }
                }
            }
        }
//            return primerList;
    }

    public PrimerList getPrimerList() {
        return primerList;
    }
}
