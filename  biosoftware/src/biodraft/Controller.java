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
import javax.swing.JTree;
import javax.swing.text.html.HTMLDocument.Iterator;
import javax.swing.tree.DefaultMutableTreeNode;

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
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)seqTree.getModel().getRoot();
        ArrayList<GeneSeq> genes = GeneSeq.getGenesByGroupID(DataGroup.getGroupIDByName(groupName));

        dataGroup = new DefaultMutableTreeNode(groupName);
        root.add(dataGroup);

//        int count = genes.size();
        for(GeneSeq g : genes ) {
            gene = new DefaultMutableTreeNode(g.getGeneName());
            dataGroup.add(gene);
        }
        seqTree.setVisible(true);
    }
}
