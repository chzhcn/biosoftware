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
public class DataGroup {

    private String name;
    private int lowPrimerLength;
    private int lowFilterLength;
    private int lowVariableRegionLength;
    private int highVariableRegionLength;

    public DataGroup(String name, int lowPrimerLength, int lowFilterLength,
            int lowVariableRegionLength, int highVariableRegionLength) {
        this.name = name;
        this.lowPrimerLength = lowPrimerLength;
        this.lowFilterLength = lowFilterLength;
        this.lowVariableRegionLength = lowVariableRegionLength;
        this.highVariableRegionLength = highVariableRegionLength;
    }

    public int getHighVariableRegionLength() {
        return highVariableRegionLength;
    }

    public int getLowFilterLength() {
        return lowFilterLength;
    }

    public int getLowPrimerLength() {
        return lowPrimerLength;
    }

    public int getLowVariableRegionLength() {
        return lowVariableRegionLength;
    }

    public String getName() {
        return name;
    }

    public static boolean addGroup(DataGroup group) {
        boolean flag = false;
        try {
//            Connection con = DriverManager.getConnection("jdbc:sqlite:BioData.db");
            String sql = "insert into DataGroup values(?, ?, ?, ?, ?)";
            PreparedStatement ps = Main.con.prepareStatement(sql);
            ps.setString(1, group.getName());
            ps.setInt(2, group.getLowPrimerLength());
            ps.setInt(3, group.getLowFilterLength());
            ps.setInt(4, group.getLowVariableRegionLength());
//            ps.setInt(5, group.getH);
            ps.executeUpdate();
//            Main.con.close();
            flag = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

    //====>
    public static int addGroupByName(String name) throws SQLException {
        int groupID = -1;

        String sql = "insert into DataGroup values(?, ?, ?, ?, ?)";
        PreparedStatement ps = Main.con.prepareStatement(sql);
        ps.setString(1, name);
        ps.executeUpdate();
        groupID = getGroupIDByName(name);
        return groupID;
    }

    public static boolean deleteGroupByName(String s) {
        boolean flag = false;
        try {
//            Connection con = DriverManager.getConnection("jdbc:sqlite:BioData.db");
            String sql = "delete from DataGroup where name = ?";
            PreparedStatement ps = Main.con.prepareStatement(sql);
            ps.setString(1, s);
            ps.executeUpdate();
//            con.close();
            flag = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }

//    public static boolean deleteGroupByID(int id) {
//        boolean flag = false;
//        try {
////            Connection con = DriverManager.getConnection("jdbc:sqlite:BioData.db");
//            String sql = "delete from DataGroup where rowid = ?";
//            PreparedStatement ps = Main.con.prepareStatement(sql);
//            ps.setInt(1, id);
//            ps.executeUpdate();
////            con.close();
//            flag = true;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return flag;
//    }
    public static int getGroupIDByName(String s) {
        int id = -1;
        try {
            String sql = "select rowid from DataGroup where name = ?";
            PreparedStatement ps = Main.con.prepareStatement(sql);
            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("rowid");
            }
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return id;
    }

    public static ArrayList<DataGroup> getAllGroups() {
        ArrayList<DataGroup> groups = new ArrayList<DataGroup>();
        try {
//            Connection con = DriverManager.getConnection("jdbc:sqlite:BioData.db");
            Statement stat = Main.con.createStatement();

            ResultSet rs = stat.executeQuery("select * from DataGroup;");

            while (rs.next()) {
//                System.out.println(rs.getInt("rowid") + rs.getString("name"));
//                System.out.println(rs.getInt("rowid"));
                groups.add(new DataGroup(rs.getString("name"), rs.getInt("lpth"),
                        rs.getInt("lfth"), rs.getInt("lvth"), rs.getInt("hvth")));
            }
            rs.close();
//            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return groups;
    }

    public static boolean setGroupName(String oldName, String newName) {
        boolean flag = false;
        try {
//            Connection con = DriverManager.getConnection("jdbc:sqlite:BioData.db");
            String sql = "update DataGroup set name = ? where name = ?";
            PreparedStatement ps = Main.con.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setString(2, oldName);
            ps.executeUpdate();
//            con.close();
            flag = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
    }
}
