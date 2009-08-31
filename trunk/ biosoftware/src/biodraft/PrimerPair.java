package biodraft;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrimerPair {
    private int forStart;
    private int forEnd;
    private int revStart;
    private int revEnd;
    private double score;
    private int groupID;

    public PrimerPair(int forStart, int forEnd, int revStart, int revEnd, double score, int groupID) {
        this.forStart = forStart;
        this.forEnd = forEnd;
        this.revStart = revStart;
        this.revEnd = revEnd;
        this.score = score;
        this.groupID = groupID;
    }

    public int getForEnd() {
        return forEnd;
    }

    public int getForStart() {
        return forStart;
    }

    public int getRevEnd() {
        return revEnd;
    }

    public int getRevStart() {
        return revStart;
    }

    public double getScore() {
        return score;
    }

    public static ArrayList<PrimerPair> getPrimerPairsByGroupID(int groupID) {
        String sql = "select * from PrimerPair where groupid = ?";
        ArrayList<PrimerPair> primerPairs = new ArrayList<PrimerPair>();
        try {
            PreparedStatement ps = Main.con.prepareStatement(sql);
            ps.setInt(1, groupID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                primerPairs.add(new PrimerPair(rs.getInt("forstart"), rs.getInt("forend"), rs.getInt("revstart"),
                        rs.getInt("revend"), rs.getInt("score"), rs.getInt("groupid")));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(PrimerPair.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return primerPairs;
    }

    public static void AddPrimerPairs() {

    }

    public static boolean deletePrimerPairsByGroupID(int id) {
        boolean flag = false;
        try{
            String sql = "delete from PrimerPair where groupid = ?";
            PreparedStatement ps = Main.con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            flag = true;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

}