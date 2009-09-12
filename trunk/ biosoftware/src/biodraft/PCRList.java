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
public class PCRList {

    private ArrayList<int[]> pcrList;
    private double[] cmeans;

    public PCRList() {
        pcrList = new ArrayList<int[]>();
    }

    public void add(int startPrimer, int endPrimer) {
        int[] pcr = {startPrimer, endPrimer};
        pcrList.add(pcr);
    }

    public ArrayList<int[]> getPcrList() {
        return pcrList;
    }

    public void Sort() {
//            double max = 0;
        for (int i = 0; i < cmeans.length; i++) {
            double max = 0;
            int pos = -1;
            for (int j = i; j < cmeans.length; j++) {
                if (max < cmeans[j]) {
                    max = cmeans[j];
                    pos = j;
                }
            }
            cmeans[pos] = cmeans[i];
            cmeans[i] = max;
        }
    }

    public void cutThenScore(PrimerList primerList, SeqSet seqSet, int shortestFrag, int maxPCR) {
//            System.out.println("shortestFrag" + shortestFrag);
        int pcrSize = pcrList.size();
        int numOfSeqs = seqSet.getSeqs().size();
//            int maxFrags = maxPCR / shortestFrag + 1;
        int maxFrags = maxPCR / 2;
        double[][][] fragmentTable = new double[pcrSize][numOfSeqs][maxFrags];
        for (int i = 0; i < pcrSize; i++) {
            for (int j = 0; j < numOfSeqs; j++) {
                for (int k = 0; k < maxFrags; k++) {
                    fragmentTable[i][j][k] = -1.0;
                }
            }
        }
        for (int i = 0; i < pcrSize; i++) {
            int pcrStart = primerList.getPrimerList().get(pcrList.get(i)[0]).getStart();
            int pcrEnd = primerList.getPrimerList().get(pcrList.get(i)[1]).getEnd();
            for (int j = 0; j < numOfSeqs; j++) {
                double fragmentSum = 0;
                int fragmentNum = 0;
                int cutCount = 0;
                for (int k = pcrStart; k <= pcrEnd; k++) {
                              char c = seqSet.getSeqs().get(j).getSeq().charAt(k);
                    if (c != '-') {
                        fragmentNum++;
                    }
                    fragmentSum += getDA(c);
//                              if((c == 'G'||c == 'g') && (fragmentNum >= shortestFrag)) {
//
//                              }
                    if (c == 'G' || c == 'g') {
                        if (fragmentNum >= shortestFrag) {
                            boolean hasTheSame = false;
                            for (int t = 0; fragmentTable[i][j][t] != -1; t++) {
                                if (fragmentTable[i][j][t] == fragmentSum) {
                                    hasTheSame = true;
                                    break;
                                }
                            }
                            if (!hasTheSame) {
                                fragmentTable[i][j][cutCount++] = fragmentSum;
                            }
                        }
                        fragmentSum = 0;
                        fragmentNum = 0;
                    }
                }
            }
        }
//            cmeans = new double[pcrSize];
        cmeans = Score(fragmentTable);
//            for (int i = 0; i < pcrSize; i++) {
//                  System.out.print("*" + i + "*");
//
//                  for (int j = 0; j < numOfSeqs; j++) {
//                        System.out.print("{");
//                        System.out.print("[" + j + "]");
//                        for (int k = 0; k < maxFrags; k++) {
//                              System.out.print(fragmentTable[i][j][k]);
//                              System.out.print(" ");
//                        }
//                        System.out.print("}");
//                  }
//                  System.out.println();
//            }
//            for(int j = 0; j < numOfSeqs; j++) {
//                  for(int i = 0; i < pcrSize; i++) {
//                        for(int k = 0; k < maxFrags; k++) {
//                              System.out.print("seq"+j+" pcr"+i+" frag"+k+" ");
//                              System.out.print("begin: "+primerList.getPrimerList().get(pcrList.get(i)[0]).getStart()+" end: "+primerList.getPrimerList().get(pcrList.get(i)[1]).getEnd()+" ");
//                              System.out.println(fragmentTable[i][j][k]);
//                        }
//                  }
//            }
    }

    public void convertToDatabaseType() {
    }

    public double[] getCmeans() {
        return cmeans;
    }

    private double[] Score(double[][][] fragmentTable) {
//            System.out.println(fragmentTable.length+" "+fragmentTable[0].length+" "+fragmentTable[0][0].length);
//            double mote = 1E-99;
        cmeans = new double[fragmentTable.length];
        for (int i = 0; i < fragmentTable.length; i++) {
            int count = 0;
            double sumOfCij = 0;
            for (int j = 0; j < fragmentTable[0].length; j++) {
                for (int k = j + 1; k < fragmentTable[0].length; k++) {
                    count++;
                    double Cij = 0;
                    double Mii = 0;
                    double Mij = 0;
                    for (int p1 = 0; fragmentTable[i][j][p1] != -1; p1++) {
                        for (int p2 = 0; fragmentTable[i][j][p2] != -1; p2++) {
//                                          if (fragmentTable[i][j][p1] - fragmentTable[i][j][p2] < mote) {
//                                                Mii++;
//                                          }
//                                          if (fragmentTable[i][j][p1] - fragmentTable[i][j][p2] == 0) {
//                                                Mii++;
//                                          }
                            Mii += scoreHelper(fragmentTable[i][j][p1], fragmentTable[i][j][p2]);
                        }
                        for (int o = 0; fragmentTable[i][k][o] != -1; o++) {
//                                          if (fragmentTable[i][j][p1] - fragmentTable[i][k][o] < mote) {
//                                                Mij++;
//                                          }
//                                          if (fragmentTable[i][j][p1] - fragmentTable[i][k][o] == 0) {
//                                                Mij++;
//                                          }
                            Mij += scoreHelper(fragmentTable[i][j][p1], fragmentTable[i][k][o]);
                        }
                    }
                    double Mjj = 0;
                    for (int q1 = 0; fragmentTable[i][k][q1] != -1; q1++) {
                        for (int q2 = 0; fragmentTable[i][k][q2] != -1; q2++) {
//                                          if (fragmentTable[i][k][q1] - fragmentTable[i][k][q2] < mote) {
//                                                Mjj++;
//                                          }
//                                          if (fragmentTable[i][k][q1] - fragmentTable[i][k][q2] == 0) {
//                                                Mjj++;
//                                          }
                            Mjj += scoreHelper(fragmentTable[i][k][q1], fragmentTable[i][k][q2]);
                        }
                    }
//                              System.out.println("ij: "+Mij+" ii: "+Mii+" jj: "+Mjj);
                    Cij = 2 * Mij / (Mii + Mjj);
//                              System.out.println(Cij);
                    sumOfCij += Cij;
//                              System.out.println(sumOfCij);
                }
            }
//                  System.out.println(count);                  
            cmeans[i] = sumOfCij / count;
//                  System.out.println("cmeans: " + cmeans[i]);
//                  System.out.println(i + "\n");
            }
//            for(int i = 0; i < cmeans.length; i++) {
//                  System.out.println(i+".. "+cmeans[i]);
//            }
        return cmeans;
    }

    private double scoreHelper(double d1, double d2) {
        double mote = Double.MIN_VALUE;
//            if (d1 - d2 < mote) {
//                  return 1.0;
//            }
        if (d1 - d2 == 0) {
            return 1.0;
        }
        return 0.0;
    }

    private double getDA(char c) {
        switch (c) {
            case 'A':
            case 'a':
                return 329.2;
            case 'C':
            case 'c':
                return 305.2;
            case 'G':
            case 'g':
                return 345.2;
            case 'T':
            case 't':
                return 361.2;
            default:
                return 0.0;
        }
    }
}
