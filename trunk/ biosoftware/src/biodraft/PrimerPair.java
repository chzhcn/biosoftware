package biodraft;

import java.sql.*;
import java.util.ArrayList;

public class PrimerPair {

    private int forStart;
    private int forEnd;
    private int revStart;
    private int revEnd;
    private double score;
    private int groupID;

    public PrimerPair(int forStart, int forEnd, int revStart,
            int revEnd, double score, int groupID) {
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

    /*
    public int getGroupID() {
    return groupID;
    }
     */
    public static ArrayList<PrimerPair> getPrimerPairsByGroupID(int groupID)
            throws SQLException {
        String sql = "select * from PrimerPair where groupid = ? order by score Desc";
        ArrayList<PrimerPair> primerPairs = new ArrayList<PrimerPair>();
        PreparedStatement ps = Main.con.prepareStatement(sql);
        ps.setInt(1, groupID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            primerPairs.add(new PrimerPair(rs.getInt("forstart"),
                    rs.getInt("forend"), rs.getInt("revstart"),
                    rs.getInt("revend"), rs.getDouble("score"),
                    rs.getInt("groupid")));
        }
        rs.close();
        ps.close();
        return primerPairs;
    }

    public static void AddPrimerPair(PrimerPair p) throws SQLException {
        String sql = "insert into PrimerPair values(?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = Main.con.prepareStatement(sql);
        ps.setInt(1, p.forStart);
        ps.setInt(2, p.forEnd);
        ps.setInt(3, p.revStart);
        ps.setInt(4, p.revEnd);
        ps.setDouble(5, p.score);
        ps.setInt(6, p.groupID);
        ps.executeUpdate();
        ps.close();
    }

    public static boolean deletePrimerPairsByGroupID(int id)
            throws SQLException {
        boolean flag = false;
        String sql = "delete from PrimerPair where groupid = ?";
        PreparedStatement ps = Main.con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
        flag = true;
        ps.close();
        return flag;
    }
}
