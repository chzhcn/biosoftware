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
public class SeqSet {

    private ArrayList<Seq> seqs;
    private StarSequence starSeq;
    int labelLength;
    String[] nameArray;

    SeqSet(String starSequence, ArrayList<String> seqs) {
        starSeq = new StarSequence(starSequence);
        this.seqs = new ArrayList<Seq>();
        for (int i = 0; i < seqs.size(); i++) {
            this.seqs.add(new Seq(seqs.get(i)));
        }
    }

    public String[] getNameArray() {
        return nameArray;
    }
//      private ArrayList<Character> starSeq;

//      private checkLabelLength() {
//
//      }
    public SeqSet(ALN aln) {
        labelLength = 0;
        int dataBegin = aln.FilterHeader();
        int numOfSeqs = aln.getNumOfSeqs();
        nameArray = new String[numOfSeqs];

        seqs = new ArrayList<Seq>();

        ArrayList<StringBuffer> sbArray = new ArrayList<StringBuffer>();
        StringBuffer sbStar = new StringBuffer();
        for (int i = 0; i < numOfSeqs; i++) {
            sbArray.add(new StringBuffer());
        }

        int size = aln.getAlnString().length();
//            for(int i = dataBegin; i < size; i++) {
//                  if
//            }

        boolean inSpace = false;
        for (int i = dataBegin; i < size; i++) {
            char c = aln.getAlnString().charAt(i);
            labelLength++;
            if (c == ' ') {
                inSpace = true;
                continue;
            }
            if (inSpace == true && c != ' ') {
                labelLength--;
                break;
            }
        }

        int enterCount = -1;
        int nameCount = 0;
//            int temp;
        int indicator = 0;
        boolean starFlag = false;
        boolean indicatorChanged = false;
        for (int i = dataBegin - 1; i < size; i++) {
            char c = aln.getAlnString().charAt(i);
//                  int indicator = 0;
            if (c != '\n') {
//                       for(int j = 0; j < temp+labelLength; j++)
//                             continue;
                if (indicatorChanged == true) {
                    indicator = enterCount % numOfSeqs;
                    indicatorChanged = false;
                }
                if (starFlag == true) {
                    if (c == '\r') {
                        continue;
                    }
                    sbStar.append((char) c);
                } else {
                    if (c == '\r' || c == ' ') {
                        continue;
                    }
//                              if (c == '\r') {
//                                    continue;
//                              }
                    sbArray.get(indicator).append((char) c);
                }
            } else {
//                StringBuffer bf = new StringBuffer();
                if (nameCount < numOfSeqs) {
                    nameArray[nameCount++] = getName(i + 1, aln);
                }

                if (starFlag == true) {
                    starFlag = false;
                    i++;//
                    enterCount++;
                    indicatorChanged = true;
                } else {
                    if (indicator == numOfSeqs - 1) {
                        starFlag = true;
                    } else {
                        enterCount++;
                        indicatorChanged = true;
                    }
                }
//                        temp = i;
                i += labelLength;
            }
        }
        starSeq = new StarSequence(sbStar.toString());
        for (int i = 0; i < numOfSeqs; i++) {
            seqs.add(new Seq(sbArray.get(i).toString()));
        }
    }

//                  for(int j = 0; j < numOfSeqs; j++) {
//                        char c = aln.getCharSeq().get(i);
//                        while(c != '\n') {
//                              seqs.get(j).add(c);
//                        }
//                        continue;
//                  }
    private String getName(int begin, ALN aln) {
        String name = null;
//        char[] nameCharArray = new char[labelLength];
//        ArrayList<Character> alnArray = aln.getCharSeq();
        String alnString = aln.getAlnString();
//        for (int i = 0; i < labelLength; i++) {
//            nameCharArray[i] = alnArray.get(begin + i);
//        }
        name = alnString.substring(begin, begin + labelLength - 1);
//        name = new String(nameCharArray);
        name = name.replace("_", ":");
        name = name.trim();
        return name;
    }

//    public SeqSet(String starString, ArrayList<String> seqStrings) {
//        starSeq = new StarSequence(stringToArrayListChar(starString));
//        seqs = new ArrayList<Seq>();
//        for (int i = 0; i < seqStrings.size(); i++) {
//            seqs.add(new Seq(stringToArrayListChar(seqStrings.get(i))));
//        }
//    }
//
//    private ArrayList<Character> stringToArrayListChar(String s) {
//        ArrayList<Character> arrayC = new ArrayList<Character>();
//        int starLength = s.length();
//        for (int i = 0; i < starLength; i++) {
//            arrayC.add(s.charAt(i));
//        }
//        return arrayC;
//    }
    public ArrayList<Seq> getSeqs() {
        return seqs;
    }

    public StarSequence getStarSeq() {
        return starSeq;
    }
}
