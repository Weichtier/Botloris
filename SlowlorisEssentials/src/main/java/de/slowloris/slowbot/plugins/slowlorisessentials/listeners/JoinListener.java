package de.slowloris.slowbot.plugins.slowlorisessentials.listeners;

import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.slowloris.slowbot.plugins.slowlorisessentials.core.Main;
import de.slowloris.slowbot.plugins.slowlorisessentials.utils.RankSync;
import de.slowloris.slowbot.plugins.slowlorisessentials.utils.Utils;
import de.slowloris.slowbot.ts3.utils.BBCode;


public class JoinListener extends TS3EventAdapter {
    @Override
    public void onClientJoin(ClientJoinEvent e) {

        Client client = Main.getInstance().getApi().getClientByUId(e.getUniqueClientIdentifier());

        if(Main.getConfiguration().getBoolean("Welcomemessage.Activated")){
            Main.getInstance().getApi().sendPrivateMessage(e.getClientId(), Main.getConfiguration().getString("Welcomemessage.Message"));
        }

        for (String nickname : Main.getConfiguration().getStringList("BadNicknames")) {
            if(e.getClientNickname().contains(nickname)){
                Main.getInstance().getApi().kickClientFromServer("Please remove " + BBCode.BOLD_START + nickname + BBCode.BOLD_END + " from your Nickname!", e.getClientId());
            }
        }

        Utils.userCounter();

        if(Main.getConfiguration().getBoolean("Mysql.Activated")){
            Main.getInstance().getLogger().info("Loading groups for user " + e.getClientNickname() + " from Mysql");
            RankSync.getInstance().syncGroups(client);
        }else {
            Main.getInstance().getLogger().info("Not loading groups for user " + e.getClientNickname() + " from Mysql");
        }

    }
}
