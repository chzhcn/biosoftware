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

      private ArrayList<Sequence> seqs;
      private StarSequence starSeq;
//      private ArrayList<Character> starSeq;

//      private checkLabelLength() {
//
//      }
      public SeqSet(ALN aln) {
            int dataBegin = aln.FilterHeader();
            int numOfSeqs = aln.getNumOfSeqs();

            seqs = new ArrayList<Sequence>();
            starSeq = new StarSequence();

            for (int i = 0; i < numOfSeqs; i++) {
                  seqs.add(new Sequence());
            }

            int size = aln.getCharSeq().size();
//            for(int i = dataBegin; i < size; i++) {
//                  if
//            }

            boolean inSpace = false;
            int labelLength = 0;
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

      public ArrayList<Sequence> getSeqs() {
            return seqs;
      }

      public StarSequence getStarSeq() {
            return starSeq;
      }

}
