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
public class PrimerList {

    private ArrayList<Primer> primerList;
    private PCRList pcrList;

    public ArrayList<Primer> getPrimerList() {
        return primerList;
    }

    public PrimerList() {
        primerList = new ArrayList<Primer>();
    }

    public void add(int start, int end) {
        primerList.add(new Primer(start, end));
    }

    //包含primer
    public void idenPCR(int minPCR, int maxPCR) {
        pcrList = new PCRList();
        int size = primerList.size();
        if (size < 2) {
//                  return pcrList;
            return;
        }
        for (int i = 0; i < size - 1; i++) {
            int startOfFirstPrimer = primerList.get(i).getStart();
            for (int j = i + 1; j < size; j++) {
                int lengthPCR = primerList.get(j).getEnd() - startOfFirstPrimer;
                if (lengthPCR >= minPCR && lengthPCR <= maxPCR) {
                    pcrList.add(i, j);
                }
            }
        }
//            return pcrList;
    }

    public PCRList getPcrList() {
        return pcrList;
    }
    //不包含primer
//      public PCRList idenPCR(int minPCR, int maxPCR) {
//            PCRList pcrList = new PCRList();
//            int size = primerList.size();
//            if (size < 2) {
//                  return pcrList;
//            }
//            for (int i = 0; i < size - 1; i++) {
//                  int startOfFirstPrimer = primerList.get(i).getEnd();
//                  for (int j = i + 1; j < size; j++) {
//                        int lengthPCR = primerList.get(j).getStart() - startOfFirstPrimer;
//                        if (lengthPCR >= minPCR && lengthPCR <= maxPCR) {
//                              pcrList.add(i, j);
//                        }
//                  }
//            }
//            return pcrList;
//      }
}
