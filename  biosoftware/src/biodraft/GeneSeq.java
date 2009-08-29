/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package biodraft;

import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Owner
 */
public class GeneSeq {
    private String name;
    private String sequence;
    private int groupID;

    public GeneSeq(String sn, String s, int id) {
        name = sn;
        sequence = s;
        groupID = id;
    }

    public static boolean addGene(GeneSeq gene) {
        boolean flag = false;
        try{
            String sql = "insert into GeneSeq values(?, ?, ?)";
            PreparedStatement ps = Main.con.prepareStatement(sql);
            ps.setString(1, gene.getGeneName());
            ps.setString(2, gene.getGeneSequence());
            ps.setInt(3, gene.getGeneGroupID());
            ps.executeUpdate();
            flag = true;
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    public static boolean deleteGeneByName(String s) {
        boolean flag = false;
        try{
            String sql = "delete from GeneSeq where name = ?";
            PreparedStatement ps = Main.con.prepareStatement(sql);
            ps.setString(1, s);
            ps.executeUpdate();
            flag = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    public static ArrayList<GeneSeq> getGenesByName(String sn) {
        ArrayList<GeneSeq> genes = new ArrayList<GeneSeq>();
        try{
            String sql = "select * from GeneSeq where name = ?";
            PreparedStatement ps = Main.con.prepareStatement(sql);
            ps.setString(1, sn);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                genes.add(new GeneSeq(rs.getString("name"),rs.getString("sequence"),rs.getInt("groupid")));
            }
            rs.close();
        } catch( Exception ex) {
                ex.printStackTrace();
        }
        return genes;
    }

    public static GeneSeq getGeneByNameGroupid(String sn, int id) {
        GeneSeq gene = null;
        try{
            String sql = "select * from GeneSeq where name = ? and groupid = ?";
            PreparedStatement ps = Main.con.prepareStatement(sql);
            ps.setString(1, sn);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                gene = new GeneSeq(sn, rs.getString("sequence"), id);
            }
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return gene;
    }

    public static ArrayList<GeneSeq> getGenesByGroupID(int id) {
        ArrayList<GeneSeq> genes = new ArrayList<GeneSeq>();
        try{
            String sql = "select * from GeneSeq where groupid = ?";
            PreparedStatement ps = Main.con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                genes.add(new GeneSeq(rs.getString("name"), rs.getString("sequence"), id));
            }
            rs.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return genes;
    }

    public String getGeneName(){
        return name;
    }

    public String getGeneSequence(){
        return sequence;
    }

    public int getGeneGroupID(){
        return groupID;
    }

}
