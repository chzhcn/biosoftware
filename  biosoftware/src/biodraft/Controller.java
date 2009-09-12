/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biodraft;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.StringTokenizer;

/**
 *
 * @author Administrator
 */
public class Controller {

    int groupID = -1;
    private final String[] primerTableNames = {"Forward Primer\nStart",
        "Forward Primer\nEnd", "Forward Primer\nSequence", "Reverse Primer\nStart",
        "Reverse Primer\nEnd", "Reverse Primer\nSequence", "Score"};
    private final String[] resultTableNames = {"Gene Name", "Coincidence"};
//    private final Object[][] emptyprimerTableData = new Object[5][5];

    public String[] getPrimerTableNames() {
        return primerTableNames;
    }

    public String[] getResultTableNames() {
        return resultTableNames;
    }

//    public Object[][] getEmptyprimerTableData() {
//        return emptyprimerTableData;
//    }
    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }
    private static Controller controller = new Controller();

    public static Controller getInstance() {
        return controller;
    }

    private String clutalw(File file) throws SQLException,
            IOException, InterruptedException, Exception {
        String aln = null;
        if (file.exists()) {
            String para = "1\n" + file.getAbsolutePath() + "\n2\n1\n\n\nX\n\nX\n";
            String command = "clustalw2.exe";
            Runtime rt = Runtime.getRuntime();
            Process p2 = null;
            aln = null;
            p2 = rt.exec(command);
            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(p2.getOutputStream()));
//        BufferedReader b = new BufferedReader(
//                new InputStreamReader(p2.getInputStream()));
            char[] cat = para.toCharArray();
            for (int i = 0; i < cat.length; i++) {
                bw.write(cat[i]);
            }
            bw.close();
            aln = file.getPath().substring(0, file.getPath().lastIndexOf(".")) + ".aln";
            p2.getInputStream().close();
            if (p2.waitFor() != 0) {
                System.out.println("exit value = " +
                        p2.exitValue());
            }
        } else {
            throw new Exception("Invalid Fasta File");
        }
//            DataGroup.addALNByGroupID(aln, groupID);
        return aln;
    }

    private ArrayList<String> decomposeFasta(File file) throws
            IOException, Exception {
        ArrayList<String> names = null;
        names = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String name = null;
//        int numOfSeqs = 0;
        StringBuffer temp = new StringBuffer(2048);
        if (groupID != -1) {
            for (String s = br.readLine(); null != s; s = br.readLine()) {
                s = s.trim();
                if (0 < s.length()) {
                    if (s.charAt(0) == '>') {
//                        numOfSeqs++;
                        if (null != name) {
//                            GeneSeq.addGene(new GeneSeq(
//                                    name, temp.toString(), groupID));
                            names.add(name);
                            name = null;
                            temp.setLength(0);
                        }
                        name = s.substring(1);
                    } else {
                        if (null != name) {
                            temp.append(s);
                        } else {
                            throw new Exception("Invalid fasta file!");
                        }
                    }
                }
            }
            if (null != name) {
//                GeneSeq.addGene(new GeneSeq(name, temp.toString(), groupID));
                names.add(name);
//                numOfSeqs++;
                }
        }
        return names;
    }

    private SeqSet decomposeAln(File file, int numOfSeqs)
            throws FileNotFoundException, IOException {
//        ArrayList<String> sequences = null;
        BufferedReader br = new BufferedReader(new FileReader(file));
        ALN aln = new ALN(br, numOfSeqs);
        return aln.getSeqSet();
    }

    private void parseSeqs(File file, File alnFile) throws
            SQLException, IOException, Exception {
//        ArrayList<String> names = decomposeFasta(file);
        ArrayList<String> fastaNames = decomposeFasta(file);
        int size = fastaNames.size();
        SeqSet ss = decomposeAln(alnFile, size);
        ArrayList<Seq> seqs = ss.getSeqs();
        String[] nameArray = ss.getNameArray();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (fastaNames.get(i).startsWith(nameArray[j])) {
                    if (GeneSeq.addGene(new GeneSeq(fastaNames.get(i),
                            seqs.get(j).getSeq(), groupID))) {
//                            System.out.println("true");
//                            System.out.println(groupID);
                        break;
                    } else {
//                        System.out.println("false");
                    }
                }
            }
        }
        DataGroup.setStarSeqByGroupID(
                ss.getStarSeq().getSeq(), groupID);
    }

    public DefaultTreeModel createNewDataGroup(File file, String groupName)
            throws SQLException, IOException, InterruptedException, Exception {
        groupID = DataGroup.addGroupByName(groupName);
        File alnFile = new File(clutalw(file));
        parseSeqs(file, alnFile);
        return populateTreeModel(groupID);
    }

    public DefaultTreeModel populateTreeModel(int groupID) throws SQLException {
        DefaultMutableTreeNode dataGroup = null;
        DefaultMutableTreeNode gene = null;
        ArrayList<GeneSeq> genes = GeneSeq.getGenesByGroupID(groupID);
        dataGroup = new DefaultMutableTreeNode(
                DataGroup.getGroupNameByID(groupID));
        for (GeneSeq g : genes) {
            gene = new DefaultMutableTreeNode(g.getGeneName());
            dataGroup.add(gene);
        }
        return new DefaultTreeModel(dataGroup);
    }

    public MyTableModel populateGroupTableModel(GroupDialog group)
            throws SQLException {
        String[] columnNames = {"Data Group Name", "Total number of genes"};
        ArrayList<DataGroup> groupList = DataGroup.getAllGroups();
        Object[][] data = new Object[groupList.size()][2];
        for (int i = 0; i < groupList.size(); i++) {
            int tempID = DataGroup.getGroupIDByName(groupList.get(i).getName());
            data[i][0] = groupList.get(i).getName();
            data[i][1] = GeneSeq.getGeneNumByGroupID(tempID);
        }
        return new MyTableModel(columnNames, data);
    }

    public MyTableModel pupolatePrimerTableModel(
            ArrayList<PrimerPair> primerPairs) throws SQLException {
//        int groupID = primerPairs.get(0).getGroupID();
        primerPairs = PrimerPair.getPrimerPairsByGroupID(groupID);
        Object[][] data = new Object[primerPairs.size()][7];
        PrimerPair primerpair;
        for (int i = 0; i < primerPairs.size(); i++) {
            primerpair = primerPairs.get(i);
            data[i][0] = primerpair.getForStart();
            data[i][1] = primerpair.getForEnd();
            data[i][2] = getPrimerSeq(primerpair.getForStart(), primerpair.getForEnd(), groupID);
            data[i][3] = primerpair.getRevStart();
            data[i][4] = primerpair.getRevEnd();
            data[i][5] = getPrimerSeq(primerpair.getRevStart(),
                    primerpair.getRevEnd(), groupID);
            data[i][6] = primerpair.getScore();
        }
        return (new MyTableModel(primerTableNames, data));
    }

    public String getPrimerSeq(int start, int end, int groupid)
            throws SQLException {
        String primer = GeneSeq.getSeqsByGroupID(
                groupid).get(0).substring(start, end + 1);
        return primer;
    }

    public void modifyTableHeader(JTable table) {
        MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer();
        Enumeration e = table.getColumnModel().getColumns();
        while (e.hasMoreElements()) {
            ((TableColumn) e.nextElement()).setHeaderRenderer(renderer);
        }
    }
//    public void makeNoise(ArrayList<Double> data) {
//        int changeNum = (int) (Math.random() * 15);
//        for (int i = 0; i < changeNum; i++) {
//            Random location = new Random();
//            int num = location.nextInt(data.size());
//            Random choice = new Random();
//            switch (choice.nextInt(3)) {
//                case 0://add some mass
//                    data.set(num, (data.get(num) + Math.random() * 100));
//                    break;
//                case 1://reduce some mass
//                    data.set(num, (data.get(num) - Math.random() * 100));
//                    if (data.get(num) <= 0) {
//                        data.remove(num);
//                    }
//                    break;
//                case 2://delete
//                    data.remove(num);
//            }
//        }
//    }
//    public void startTyping(File dataFile) {
//        try {
//            BufferedReader in = new BufferedReader(new FileReader(dataFile));
//            ArrayList<Double> expMassList = new ArrayList<Double>();
//            String aMass = "";
//            while ((aMass = in.readLine()) != null) {
//                expMassList.add(Double.parseDouble(aMass));
//            }
//            for (int j = 0; j < expMassList.size(); j++) {
//                System.out.println(expMassList.get(j));
//            }
//            System.out.println(expMassList.size());
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            makeNoise(expMassList);
//            for (int j = 0; j < expMassList.size(); j++) {
//                System.out.println(expMassList.get(j));
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public Object[][] typing(ArrayList<GeneSeq> genesList,
            ArrayList<Double> expData, PrimerPair primerpair, int filter, Enzyme enzyme, double tolerance) {
        Object[][] typingResult = new Object[genesList.size()][2];
        int start = primerpair.getForStart();
        int end = primerpair.getRevEnd();
        String sequence = "";
        double tempMass = 0;
        int cursor = 0;
        for (int i = 0; i < genesList.size(); i++) {
            ArrayList<Double> modData = new ArrayList<Double>();
            sequence = genesList.get(i).getGeneSequence().substring(
                    start, end + 1).replace("-", "");
            if (enzyme == Enzyme.SP6) {
                for (int j = 0; j < sequence.length(); j++) {
                    switch (sequence.charAt(j)) {
                        case 'A':
                        case 'a':
                            tempMass += 313.21;
                            break;
                        case 'C':
                        case 'c':
                            tempMass += 289.19;
                            break;
                        case 'G':
                        case 'g':
                            tempMass += 329.21;
                            if (j - cursor + 1 >= filter) {
                                modData.add(tempMass);
                            }
                            cursor = j + 1;
                            tempMass = 0;
                            break;
                        case 'T':
                        case 't':
                            tempMass += 304.2;
                            break;
                        default:
                            tempMass += 0.0;
                            break;
                    }
                }
            } else {
                for (int j = 0; j < sequence.length(); j++) {
                    switch (sequence.charAt(j)) {
                        case 'T':
                        case 't':
                            tempMass += 313.21;
                            break;
                        case 'G':
                        case 'g':
                            tempMass += 289.19;
                            break;
                        case 'C':
                        case 'c':
                            tempMass += 329.21;
                            if (j - cursor + 1 >= filter) {
                                modData.add(tempMass);
                            }
                            cursor = j + 1;
                            tempMass = 0;
                            break;
                        case 'A':
                        case 'a':
                            tempMass += 304.2;
                            break;
                        default:
                            tempMass += 0.0;
                            break;
                    }
                }
            }
            typingResult[i][0] = genesList.get(i).getGeneName();
            typingResult[i][1] = score(expData, modData, tolerance);
//            System.out.println(genesList.get(i).getGeneName());
//            System.out.println(genesList.get(i).getGeneSequence());
//            System.out.println(genesList.get(i).getGeneName());
//            System.out.println(genesList.get(i).getGeneSequence());
        }
        return typingResult;
    }

    public double score(ArrayList<Double> expData, ArrayList<Double> modData, double tolerance) {
        double coincidence = 0;
        double mii = 0;
        double mjj = 0;
        double mij = 0;
//        double mote = Double.MIN_VALUE;
        for (int i = 0; i < expData.size(); i++) {
            for (int j = 0; j < expData.size(); j++) {
                if ((expData.get(i) - expData.get(j)) <= tolerance) {
                    mii += 1;
                }
            }
        }
        for (int i = 0; i < modData.size(); i++) {
            for (int j = 0; j < modData.size(); j++) {
                if ((modData.get(i) - modData.get(j) <= tolerance)) {
                    mjj += 1;
                }
            }
        }
        for (int i = 0; i < expData.size(); i++) {
            for (int j = 0; j < modData.size(); j++) {
                if ((expData.get(i) - modData.get(j) == 0)) {
                    mij += 1;
                }
            }
        }
        coincidence = 2 * mij / (mii + mjj);
        return coincidence;
    }

    public ArrayList<Double> getExpMassList(File dataFile, double intensity)
            throws FileNotFoundException, IOException, Exception {
        ArrayList<Double> expMassList = new ArrayList<Double>();
        BufferedReader in = new BufferedReader(new FileReader(dataFile));
        String firstLine = in.readLine();
        if (!firstLine.startsWith("TITLE")) {
            throw new Exception("Invalid File Format");
        } else {
            String hIntensity = firstLine.substring(firstLine.indexOf(",")+1, firstLine.indexOf("]")).trim();
            intensity = intensity * Double.parseDouble(hIntensity) / 100;
        }
        if (!in.readLine().equals("TYPE MASSSPEC")) {
            throw new Exception("Invalid File Format");
        }
        String aLine = "";
        double aMass = 0;
        double aIntensity = 0;
        StringTokenizer tokenizer;
        while ((aLine = in.readLine()) != null) {
//            expMassList.add(Double.parseDouble(aMass));
            tokenizer = new StringTokenizer(aLine);
            if (tokenizer.countTokens() != 2) {
                throw new Exception("Invalid File Format");
            }
            aMass = Double.parseDouble(tokenizer.nextToken());
            aIntensity = Double.parseDouble(tokenizer.nextToken());
            if (aIntensity > intensity) {
                expMassList.add(aMass);
            }
        }
//            for (int j = 0; j < expMassList.size(); j++) {
//                System.out.println(expMassList.get(j));
//            }
//            System.out.println(expMassList.size());
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            makeNoise(expMassList);//加噪�?
//            for (int j = 0; j < expMassList.size(); j++) {
//                System.out.println(expMassList.get(j));
//            }

        return expMassList;

    }
}
