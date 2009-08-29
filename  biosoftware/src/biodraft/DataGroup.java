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

    public DataGroup(String s) throws SQLException {
        name = s;
    }

    public static boolean addGroup(DataGroup group) {
        boolean flag = false;
        try {
//            Connection con = DriverManager.getConnection("jdbc:sqlite:BioData.db");
            String sql = "insert into DataGroup values(?)";
            PreparedStatement ps = Main.con.prepareStatement(sql);
            ps.setString(1, group.getGroupName());
            ps.executeUpdate();
//            Main.con.close();
            flag = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return flag;
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

    public static int getGroupID(String s) {
        int id = -1;
        try {
            String sql = "select rowid from DataGroup where name = ?";
            PreparedStatement ps = Main.con.prepareStatement(sql);
            ps.setString(1, s);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
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
                groups.add(new DataGroup(rs.getString("name")));
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

    public String getGroupName() {
        return name;
    }
}
