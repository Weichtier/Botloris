package de.slowloris.slowbot.plugins.slowlorisessentials.utils;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.slowloris.slowbot.plugins.slowlorisessentials.core.Main;
import de.slowloris.slowbot.plugins.slowlorisessentials.sql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RankSync {

    private static RankSync rankSync;
    private MySQL sql;

    public RankSync(MySQL sql) {
        this.sql = sql;
    }

    public List<Integer> getGroups(Client client){

        Utils.checkSqlTables();

        try {

            List<Integer> groups = new ArrayList<>();

            ResultSet rs = sql.runSql("SELECT * FROM ranksync WHERE uuid = '" + client.getUniqueIdentifier() + "'");

            if(!rs.next()){
                registerUser(client, Main.getConfiguration().getBoolean("Ranksync.CopyGroupsAtRegister"));
                return getGroups(client);
            }

            for (String s : rs.getString("groups").split(";")){
                groups.add(Integer.parseInt(s));
            }

            return groups;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Integer> getGroups(String clientuuid){
        return getGroups(Main.getInstance().getApi().getClientByUId(clientuuid));
    }

    public void registerUser(Client client, boolean insertGroups){

        Utils.checkSqlTables();

        try {
            ResultSet rs = sql.runSql("SELECT * FROM ranksync WHERE uuid = '" + client.getUniqueIdentifier() + "'");
            if(rs.next()) return;

            String groups = "";

            if(insertGroups){
                for (int serverGroup : client.getServerGroups()) {
                    groups += serverGroup + ";";
                }
            }else {
                groups += Main.getInstance().getApi().getServerInfo().getDefaultServerGroup() + ";";
            }

            sql.runSql2("INSERT INTO ranksync (uuid, groups) VALUES ('" + client.getUniqueIdentifier() + "', '" + groups + "')");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void syncGroups(Client client){

        int defaultGroup = Main.getInstance().getApi().getServerInfo().getDefaultServerGroup();

        List<Integer> currentGroups = new ArrayList<>();
        List<Integer> sqlGroups = RankSync.getInstance().getGroups(client);

        for (int serverGroup : client.getServerGroups()) {
            currentGroups.add(serverGroup);
        }

        for (Integer sqlGroup : sqlGroups) {
            if(!currentGroups.contains(sqlGroup) && sqlGroup != defaultGroup){
                Main.getInstance().getApi().addClientToServerGroup(sqlGroup, client.getDatabaseId());
            }
        }

        for (Integer currentGroup : currentGroups) {
            if(!sqlGroups.contains(currentGroup) && currentGroup != defaultGroup){
                Main.getInstance().getApi().removeClientFromServerGroup(currentGroup, client.getDatabaseId());
            }
        }
    }

    public void syncGroups(String clientuuid){
        syncGroups(Main.getInstance().getApi().getClientByUId(clientuuid));
    }

    public static RankSync getInstance(){
        return rankSync != null ? rankSync : newInstance();
    }

    public static RankSync newInstance(){
        if(rankSync != null) return rankSync;
        rankSync = new RankSync(Main.getSql());
        return rankSync;
    }

}
