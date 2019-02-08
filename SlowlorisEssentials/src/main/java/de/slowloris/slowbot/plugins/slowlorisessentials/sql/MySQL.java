package de.slowloris.slowbot.plugins.slowlorisessentials.sql;

import java.sql.*;

public class MySQL {

    public Connection conn = null;
    private boolean connected;
    private String connErr = "";

    public MySQL(String hostname, String database, String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://" + hostname + "/" + database + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin";
            conn = DriverManager.getConnection(url, username, password);
            connected = true;
        } catch (Exception e) {
            e.printStackTrace();
            connErr = e.getClass().getSimpleName();
            connected = false;
        }
    }
    public ResultSet runSql(String sql) {
        try {
            Statement sta = conn.createStatement();
            return sta.executeQuery(sql);
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return null;
    }
    public boolean runSql2(String sql) {
        try {
            Statement sta = conn.createStatement();
            return sta.execute(sql);
        }catch (SQLException exc){
            exc.printStackTrace();
        }
        return false;
    }

    public boolean isConnected() {
        return connected;
    }

    public String getConnErr() {
        return connErr;
    }

    public void finalize() throws Throwable {
        if (conn != null || !conn.isClosed()) {
            conn.close();
        }
    }

}
