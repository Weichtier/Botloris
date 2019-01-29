package de.slowloris.slowbot.plugins.slowlorisessentials.listeners;

import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import de.slowloris.slowbot.plugins.slowlorisessentials.core.Main;

public class JoinListener extends TS3EventAdapter {
    @Override
    public void onClientJoin(ClientJoinEvent e) {
        if(Main.getConfiguration().getBoolean("Welcomemessage.Activated")){
            Main.getInstance().getApi().sendPrivateMessage(e.getClientId(), Main.getConfiguration().getString("Welcomemessage.Message"));
        }
    }
}
