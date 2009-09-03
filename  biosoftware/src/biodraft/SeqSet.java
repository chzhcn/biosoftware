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
        starSeq = new StarSequence();

        for (int i = 0; i < numOfSeqs; i++) {
            seqs.add(new Seq());
        }

        int size = aln.getCharSeq().size();
//            for(int i = dataBegin; i < size; i++) {
//                  if
//            }

        boolean inSpace = false;

        for (int i = dataBegin; i < size; i++) {
            char c = aln.getCharSeq().get(i);
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
            char c = aln.getCharSeq().get(i);
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
                    starSeq.add(c);
                } else {
                    if (c == '\r' || c == ' ') {
                        continue;
                    }
//                              if (c == '\r') {
//                                    continue;
//                              }
                    seqs.get(indicator).add(c);
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


//                  for(int j = 0; j < numOfSeqs; j++) {
//                        char c = aln.getCharSeq().get(i);
//                        while(c != '\n') {
//                              seqs.get(j).add(c);
//                        }
//                        continue;
//                  }

    }

    private String getName(int begin, ALN aln) {
        String name = null;
        char[] nameCharArray = new char[labelLength];
        ArrayList<Character> alnArray = aln.getCharSeq();
        for (int i = 0; i < labelLength; i++) {
            nameCharArray[i] = alnArray.get(begin + i);
        }
        name = new String(nameCharArray);
        name = name.trim();
        return name;
    }

    public SeqSet(String starString, ArrayList<String> seqStrings) {
        starSeq = new StarSequence(stringToArrayListChar(starString));
        seqs = new ArrayList<Seq>();
        for (int i = 0; i < seqStrings.size(); i++) {
            seqs.add(new Seq(stringToArrayListChar(seqStrings.get(i))));
        }
    }

    private ArrayList<Character> stringToArrayListChar(String s) {
        ArrayList<Character> arrayC = new ArrayList<Character>();
        int starLength = s.length();
        for (int i = 0; i < starLength; i++) {
            arrayC.add(s.charAt(i));
        }
        return arrayC;
    }

    public ArrayList<Seq> getSeqs() {
        return seqs;
    }

    public StarSequence getStarSeq() {
        return starSeq;
    }
}
