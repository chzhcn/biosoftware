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
public class MSTT {

    private SeqSet seqSet;
    private int primerLength;
    private int minPCR;
    private int maxPCR;
    private int shortestFrag;
    private int groupID;

    public MSTT(String starSequence, ArrayList<String> seqs, int primerLength, int minPCR, int maxPCR, int shortestFrag, int groupID) {
        seqSet = new SeqSet(starSequence, seqs);
        this.primerLength = primerLength;
        this.minPCR = minPCR;
        this.maxPCR = maxPCR;
        this.shortestFrag = shortestFrag;
        this.groupID = groupID;
    }

    public ArrayList<PrimerPair> run() throws Exception {
        StarSequence starSeq = seqSet.getStarSeq();
        starSeq.idenPrimer(primerLength);
        PrimerList primerList = starSeq.getPrimerList();
        primerList.idenPCR(minPCR, maxPCR);
        PCRList pcrList = primerList.getPcrList();
        if (pcrList.getPcrList().isEmpty()) {
            throw new Exception("Can not find a universal primer");
        }
        pcrList.cutThenScore(primerList, seqSet, shortestFrag, maxPCR);

//            for (int[] pcr : pcrList.getPcrList()) {
//                  stdOut.println("start: " + pcr[0] + ", " + primerList.getPrimerList().get(pcr[0]).getStart() + " end: " + pcr[1] + ", " + primerList.getPrimerList().get(pcr[1]).getEnd());
//            }
        ArrayList<PrimerPair> ppList = new ArrayList<PrimerPair>();
        for (int i = 0; i < pcrList.getPcrList().size(); i++) {
            int forStart = primerList.getPrimerList().get(pcrList.getPcrList().get(i)[0]).getStart();
            int forEnd = primerList.getPrimerList().get(pcrList.getPcrList().get(i)[0]).getEnd();
            int revStart = primerList.getPrimerList().get(pcrList.getPcrList().get(i)[1]).getStart();
            int revEnd = primerList.getPrimerList().get(pcrList.getPcrList().get(i)[1]).getEnd();
            ppList.add(new PrimerPair(forStart, forEnd, revStart, revEnd, pcrList.getCmeans()[i], groupID));
//            stdOut.println(i + " start: " + pcrList.getPcrList().get(i)[0]
//                    + ".  [" + forStart + ", " + forEnd + "] end: "
//                    + pcrList.getPcrList().get(i)[1]
//                    + ". [" + revStart + ", " + revEnd + "]");
        }
        return ppList;
    }
}
