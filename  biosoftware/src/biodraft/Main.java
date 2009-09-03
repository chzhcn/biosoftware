/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package biodraft;

import java.sql.*;
/**
 *
 * @author Owner
 */
public class Main {
    //for r2
    /**
     * @param args the command line arguments
     */

    public static Connection con;
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        Class.forName("org.sqlite.JDBC");

// Initialize Database
        con = DriverManager.getConnection("jdbc:sqlite:BioData.db");
//        Statement stat = con.createStatement();
//        stat.executeUpdate("create table DataGroup(name varchar not null primary key, lpth integer," +
//                " lfth integer, lvth integer, hvth integer)");
//        stat.executeUpdate("create table GeneSeq(name varchar primary key, sequence varchar not null, " +
//                "groupid integer not null)");
//        stat.executeUpdate("create table PrimerPair(forstart integer not null, forend integer not null, " +
//                "revstart integer not null, revend integer not null, " +
//                "score numeric, groupid integer not null)");

//        DataGroup.addGroup(new DataGroup("testGroup3"));
//        DataGroup.addGroup(new DataGroup("testGroup4"));
//        DataGroup.addGroup(new DataGroup("testGroup5"));
//        DataGroup.addGroup(new DataGroup("testGroup6"));
//        DataGroup.deleteGroupByName("testGroup2");
//        DataGroup.setGroupName("testGroup", "testGroup2");
//        DataGroup.deleteGroupByID(3);
//        DataGroup.getAllGroups();
//        DataGroup.getGroupIDByName("testGroup");
//        GeneSeq.deleteGenesByGroupID(10);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });

//        ArrayList<GeneSeq> genes = GeneSeq.getGenesByGroupID(64);
//        for (int i = 0 ; i < genes.size(); i ++) {
//            System.out.println(genes.get(i).getGeneSequence());
//        }

//        DataGroup.getGroupByID(64);
//         PreparedStatement stat = Main.con.prepareStatement("select * from DataGroup;");
//            ResultSet rs = stat.executeQuery();
//            while (rs.next()) {
//                System.out.println(rs.getInt("rowid"));
//            }
//            rs.close();
//        ArrayList<GeneSeq> genes = GeneSeq.getGenesByGroupID(63);
//        for (int i = 0 ; i < genes.size(); i ++) {
//            System.out.println(genes.get(i).getGeneName());
//            System.out.println(genes.get(i).getGeneSequence());
//        }

    }

}
