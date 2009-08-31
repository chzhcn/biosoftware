/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package biodraft;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTree;
import javax.swing.text.html.HTMLDocument.Iterator;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Administrator
 */
public class Controller {

    public static int createNewDataGroup(File file, String groupName) throws FileNotFoundException, IOException, SQLException {
        BufferedReader br = null;

        if (file.canRead()) {
            br = new BufferedReader(new FileReader(file));
        } else {
            throw new SecurityException();
        }

        String name = null;
//        ArrayList<GeneSeq> genes = new ArrayList<GeneSeq>();
        StringBuffer temp = new StringBuffer(8000);
        int groupID = -1;

        groupID = DataGroup.addGroupByName(groupName);
//        int groupID = DataGroup.addGroup(file.getName());

//        int groupID = DataGroup.addGroup(file.getName());

        if (groupID != -1) {
            for (String s = br.readLine(); null != s; s = br.readLine()) {
                s = s.trim();
                if (0 < s.length()) {
                    if (s.charAt(0) == '>') {
                        if (null != name) {
                            GeneSeq.addGene(new GeneSeq(name, temp.toString(), groupID));
                            name = null;
                            temp.setLength(0);
                        }

                        name = s.substring(1);

                    } else {
                        if(null != name) {
                            temp.append(s);
                        }else {
                            throw new IOException("Unexpected char before >");
                        }
                    }
                }
            }

            if(null != name) {
                GeneSeq.addGene(new GeneSeq(name, temp.toString(), groupID));
            }
        }
        return groupID;

    }

    public static void refreshTree(JTree seqTree, String groupName) {
        DefaultMutableTreeNode dataGroup = null;
        DefaultMutableTreeNode gene = null;
//        DefaultMutableTreeNode root = (DefaultMutableTreeNode)seqTree.getModel().getRoot();
        ArrayList<GeneSeq> genes = GeneSeq.getGenesByGroupID(DataGroup.getGroupIDByName(groupName));

        dataGroup = new DefaultMutableTreeNode(groupName);
//        root.add(dataGroup);

//        int count = genes.size();
        for(GeneSeq g : genes ) {
            gene = new DefaultMutableTreeNode(g.getGeneName());
            dataGroup.add(gene);
        }
        seqTree.setModel(new DefaultTreeModel(dataGroup));
        seqTree.setRootVisible(true);
        seqTree.setVisible(true);
    }

    public static void startTyping (File dataFile) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(dataFile));
            ArrayList<Double> expMassList = new ArrayList<Double>();
            String aMass = "";
            while ((aMass = in.readLine()) != null) {
                expMassList.add(Double.parseDouble(aMass));
            }
            for (int j = 0; j < expMassList.size(); j++) {
                System.out.println(expMassList.get(j));
            }
            System.out.println(expMassList.size());
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            makeNoise(expMassList);
            for (int j = 0; j < expMassList.size(); j++) {
                System.out.println(expMassList.get(j));
            }
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void makeNoise(ArrayList<Double> data) {
        int changeNum = (int) (Math.random() * 15);
        for (int i = 0; i < changeNum; i++) {
            Random location = new Random();
            int num = location.nextInt(data.size());
            Random choice = new Random();
            switch(choice.nextInt(3)) {
                case 0://add some mass
                    data.set(num, (data.get(num)+Math.random()*100));
                    break;
                case 1://reduce some mass
                    data.set(num, (data.get(num)-Math.random()*100));
                    if(data.get(num) <= 0 ) {
                        data.remove(num);
                    }
                    break;
                case 2://delete
                    data.remove(num);
            }
        }
    }

     public static void populateGroupTable( GroupDialog group) {
        String[] columnNames = {"Data Group Name", "Total number of genes"};
        ArrayList<DataGroup> groupList = DataGroup.getAllGroups();
        Object[][] data = new Object[groupList.size()][2];
        for (int i = 0; i < groupList.size(); i ++) {
            data[i][0] = groupList.get(i).getName();
            data[i][1] = GeneSeq.getGeneNumByGroupName(groupList.get(i).getName());
        }
        group.setTableModel(new MyTableModel(columnNames, data));
    }

    public static MyTableModel pupolatePrimerTable (ArrayList<PrimerPair> primerPairs) {
        String[] columnNames = {"Forward Primer Start","Forward Primer End",
        "Reverse Primer Start","Reverse Primer End","Score"};
        Object[][] data = new Object[primerPairs.size()][5];
        PrimerPair primerpair;
        for (int i = 0; i < primerPairs.size(); i ++) {
            primerpair = primerPairs.get(i);
            data[i][0] = primerpair.getForStart();
            data[i][1] = primerpair.getForEnd();
            data[i][2] = primerpair.getRevStart();
            data[i][3] = primerpair.getRevEnd();
            data[i][4] = primerpair.getScore();
        }
        return (new MyTableModel(columnNames, data));
    }
}

