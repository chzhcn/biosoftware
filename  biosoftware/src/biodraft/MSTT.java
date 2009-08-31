/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biodraft;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chzhcn
 */
public class MSTT {

    private static PrintWriter stdOut = new PrintWriter(System.out, true);
    private BufferedReader in;
//    private String filePath;
    private int numOfSeqs;
    private int primerLength;
    private int minPCR;
    private int maxPCR;
    private int shortestFrag;
    private ALN aln;

    public MSTT(/*String filePath, */int numOfSeqs, int primerLength, int minPCR, int maxPCR, int shortestFrag, InputStream inStream) {
//        this.filePath = filePath;
        this.numOfSeqs = numOfSeqs;
        this.primerLength = primerLength;
        this.minPCR = minPCR;
        this.maxPCR = maxPCR;
        this.shortestFrag = shortestFrag;
        in = new BufferedReader(new InputStreamReader(inStream));
    }

    public void run(){
        try {
//        in = new BufferedReader(new FileReader(filePath));
            aln = new ALN(in, numOfSeqs);
        } catch (IOException ex) {
            Logger.getLogger(MSTT.class.getName()).log(Level.SEVERE, null, ex);
        }
        SeqSet seqSet = aln.getSeqSet();
        StarSequence starSeq = seqSet.getStarSeq();
        starSeq.idenPrimer(primerLength);
//            if(primerList.getPrimerList().size()<2) {
//                  return;
//            }
        PrimerList primerList = starSeq.getPrimerList();
        primerList.idenPCR(minPCR, maxPCR);
        PCRList pcrList = primerList.getPcrList();
//            stdOut.println(primerList.getPrimerList().size());
//            for (Primer p : primerList.getPrimerList()) {
//                  stdOut.println("start: " + p.getStart() + " end: " + p.getEnd());
//            }
        if (pcrList.getPcrList().isEmpty()) {
            return;
        }
        pcrList.cutThenScore(primerList, seqSet, shortestFrag, maxPCR);
//            pcrList.Sort();
        for (int i = 0; i < pcrList.getCmeans().length; i++) {
            System.out.println(i + ". " + pcrList.getCmeans()[i]);
        }

//            for (int[] pcr : pcrList.getPcrList()) {
//                  stdOut.println("start: " + pcr[0] + ", " + primerList.getPrimerList().get(pcr[0]).getStart() + " end: " + pcr[1] + ", " + primerList.getPrimerList().get(pcr[1]).getEnd());
//            }
        for (int i = 0; i < pcrList.getPcrList().size(); i++) {
            stdOut.println(i + " start: " + pcrList.getPcrList().get(i)[0] + ".  [" + primerList.getPrimerList().get(pcrList.getPcrList().get(i)[0]).getStart() + ", " + primerList.getPrimerList().get(pcrList.getPcrList().get(i)[0]).getEnd() + "] end: " + pcrList.getPcrList().get(i)[1] + ". [" + primerList.getPrimerList().get(pcrList.getPcrList().get(i)[1]).getStart() + ", " + primerList.getPrimerList().get(pcrList.getPcrList().get(i)[1]).getEnd() + "]");
        }
//            for (int[] pcr : pcrList.getPcrList()) {
//                  stdOut.println("start: " + pcr[0] + ", " + primerList.getPrimerList().get(pcr[0]).getEnd() + " end: " + pcr[1] + ", " + primerList.getPrimerList().get(pcr[1]).getStart());
//            }
//            stdOut.println(pcrList.getPcrList().size());
//            for (Sequence seq : seqSet.getSeqs()) {
//                  for (char c : seq.getSeq()) {
//                        stdOut.print(c);
//                        stdOut.flush();
//                  }
//                  stdOut.println();
//            }
//
//            for (char c : seqSet.getStarSeq()) {
//                  stdOut.print(c);
//                  stdOut.flush();
//            }
//            for (Sequence seq : seqSet.getSeqs()) {
//                  stdOut.println(seq.getSeq().size());
//            }
//            stdOut.println("star"+seqSet.getStarSeq().size());
//            aln.FilterHeader();
    }
    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) throws Exception {
//        // TODO code application logic here
//        //这一行参数都在GUI中获得
//
//        MSTT mstt = new MSTT(System.getProperty("user.dir") + "/realData.aln", 4, 20, 250, 500, 6);
//        mstt.run();
//
//    }
}
