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
    private String starSeq;
//    private int groupid;

    public DataGroup(String name, int lowPrimerLength, int lowFilterLength,
            int lowVariableRegionLength, int highVariableRegionLength) {
        this.name = name;
//        this.groupid = id;
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

//    public int getGroupid() {
//        return groupid;
//    }
    //======>
    /*
    public static byte[] getALNbyGroupID(int groupID) throws SQLException {
    byte[] data = null;
    //        InputStream inStream = null;
    //        throw new UnsupportedOperationException("Not yet implemented");
    //        String aln = null;
    String sql = "select aln from DataGroup where rowid = ?";
    PreparedStatement ps = Main.con.prepareStatement(sql);
    ps.setInt(1, groupID);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
    data = rs.getBytes("aln");
    }
    rs.close();
    ps.close();
    return data;
    }
     */

    /*
    public static void addALNByGroupID(String path, int groupID) throws SQLException, FileNotFoundException, IOException {
    File file = new File(path);
    byte[] data = new byte[(int) file.length()];
    FileInputStream fileInputStream = new FileInputStream(file);
    fileInputStream.read(data);
    String sql = "update DataGroup set aln = ? where rowid = ?";
    PreparedStatement ps = Main.con.prepareStatement(sql);
    //        InputStream alnStream = new FileInputStream(path);
    //        ps.setBlob(1, alnStream);
    ps.setBytes(1, data);
    //        ps.setBinaryStream(1, alnStream, file.length());
    ps.setInt(2, groupID);
    ps.executeUpdate();
    ps.close();
    }
     */
    //====>
    public static int addGroupByName(String name)
            throws SQLException {
        int groupID = -1;
        String sql = "insert into DataGroup values(?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = Main.con.prepareStatement(sql);
        ps.setString(1, name);
        ps.executeUpdate();
        ps.close();
        groupID = getGroupIDByName(name);
        return groupID;
    }

    /*
    public static boolean addGroup(DataGroup group) {
    boolean flag = false;
    try {
    //            Connection con = DriverManager.getConnection("jdbc:sqlite:BioData.db");
    String sql = "insert into DataGroup values(?, ?, ?, ?, ?, ?)";
    PreparedStatement ps = Main.con.prepareStatement(sql);
    ps.setString(1, group.getName());
    ps.setInt(2, group.getLowPrimerLength());
    ps.setInt(3, group.getLowFilterLength());
    ps.setInt(4, group.getLowVariableRegionLength());
    //            ps.setInt(5, group.getH);
    ps.executeUpdate();
    //            Main.con.close();
    flag = true;
    ps.close();
    } catch (Exception ex) {
    ex.printStackTrace();
    }
    return flag;
    }
     */
    public static boolean deleteGroupByName(String s)
            throws SQLException {
        boolean flag = false;
//            Connection con = DriverManager.getConnection("jdbc:sqlite:BioData.db");
        String sql = "delete from DataGroup where name = ?";
        PreparedStatement ps = Main.con.prepareStatement(sql);
        ps.setString(1, s);
        ps.executeUpdate();
//            con.close();
        flag = true;
        ps.close();
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
    public static int getGroupIDByName(String s)
            throws SQLException {
        int id = -1;
        String sql = "select rowid from DataGroup where name = ?";
        PreparedStatement ps = Main.con.prepareStatement(sql);
        ps.setString(1, s);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            id = rs.getInt("rowid");
        }
        rs.close();
        ps.close();
        return id;
    }

    public static String getGroupNameByID(int groupID)
            throws SQLException {
        String name = null;
        String sql = "select name from DataGroup where rowid = ?";
        PreparedStatement ps = Main.con.prepareStatement(sql);
        ps.setInt(1, groupID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            name = rs.getString("name");
        }
        rs.close();
        ps.close();
        return name;
    }

    /*
    public static DataGroup getGroupByName(String name) {
    DataGroup group = null;
    try {
    String sql = "select * from DataGroup where name = ?";
    PreparedStatement ps = Main.con.prepareStatement(sql);
    ps.setString(1, name);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
    group = new DataGroup(name, rs.getInt("lpth"), rs.getInt("lfth"), rs.getInt("lvth"), rs.getInt("hvth"));
    }
    rs.close();
    ps.close();
    } catch (Exception ex) {
    ex.printStackTrace();
    }
    return group;
    }
     */
    public static DataGroup getGroupByID(int groupID)
            throws SQLException {
        DataGroup group = null;
        String sql = "select * from DataGroup where rowid = ?";
        PreparedStatement ps = Main.con.prepareStatement(sql);
        ps.setInt(1, groupID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            group = new DataGroup(rs.getString("name"), rs.getInt("lpth"),
                    rs.getInt("lfth"), rs.getInt("lvth"), rs.getInt("hvth"));
        }
        rs.close();
        ps.close();

        return group;
    }

    public static ArrayList<DataGroup> getAllGroups()
            throws SQLException {
        ArrayList<DataGroup> groups = new ArrayList<DataGroup>();
        Statement stat = Main.con.createStatement();
        ResultSet rs = stat.executeQuery("select * from DataGroup;");
        while (rs.next()) {
            groups.add(new DataGroup(rs.getString("name"), rs.getInt("lpth"),
                    rs.getInt("lfth"), rs.getInt("lvth"), rs.getInt("hvth")));            
        }
        rs.close();
        return groups;
    }
    /*
    public static boolean setGroupName(String oldName, String newName) {
    boolean flag = false;
    try {
    String sql = "update DataGroup set name = ? where name = ?";
    PreparedStatement ps = Main.con.prepareStatement(sql);
    ps.setString(1, newName);
    ps.setString(2, oldName);
    ps.executeUpdate();
    ps.close();
    flag = true;
    } catch (Exception ex) {
    ex.printStackTrace();
    }
    return flag;
    }
     */

    public static void setStarSeqByGroupID(String starSeq, int groupID)
            throws SQLException {
        String sql = "update DataGroup set starseq = ? where rowid = ?";
        PreparedStatement ps = Main.con.prepareStatement(sql);
        ps.setString(1, starSeq);
        ps.setInt(2, groupID);
        ps.executeUpdate();
        ps.close();
    }

    public static String getStarSeqByGroupID(int groupID)
            throws SQLException {
        String starSeq = null;
        String sql = "select starseq from DataGroup where rowid = ?";
        PreparedStatement ps = Main.con.prepareStatement(sql);
        ps.setInt(1, groupID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            starSeq = rs.getString("starseq");
        }
        rs.close();
        ps.close();
        return starSeq;
    }

    public static void setThesholdByID(int lpth, int lfth,
            int lvth, int hvth, int groupID) throws SQLException {
        String sql = "update DataGroup set lpth = ?, lfth = ?," +
                " lvth = ?, hvth = ? where rowid = ?";
        PreparedStatement ps = Main.con.prepareStatement(sql);
        ps.setInt(1, lpth);
        ps.setInt(2, lfth);
        ps.setInt(3, lvth);
        ps.setInt(4, hvth);
        ps.setInt(5, groupID);
        ps.executeUpdate();
        ps.close();
    }
}
