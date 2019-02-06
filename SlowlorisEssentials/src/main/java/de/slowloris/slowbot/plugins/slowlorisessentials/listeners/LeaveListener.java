package de.slowloris.slowbot.plugins.slowlorisessentials.listeners;

import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import de.slowloris.slowbot.plugins.slowlorisessentials.utils.Utils;

public class LeaveListener extends TS3EventAdapter {
    @Override
    public void onClientLeave(ClientLeaveEvent e) {
        Utils.userCounter();
    }
}
