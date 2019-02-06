package de.slowloris.slowbot.plugins.slowlorisessentials.commands;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import de.slowloris.slowbot.plugins.slowlorisessentials.core.Main;
import de.slowloris.slowbot.plugins.slowlorisessentials.utils.Utils;
import de.slowloris.slowbot.ts3.api.command.Command;
import de.slowloris.slowbot.ts3.utils.BBCode;

public class ClientinfoCommand implements Command {
    @Override
    public boolean execute(Client client, String command, String[] args) {
        if(args.length >= 1){
            if(Utils.isAdmin(client)){
                String username = Utils.toString(args);
                Client target = Main.getInstance().getApi().getClientByNameExact(username, false);
                if(target == null){
                    Main.getInstance().getApi().sendPrivateMessage(client.getId(), "Client is offline!");
                    return false;
                }

                Main.getInstance().getApi().sendPrivateMessage(client.getId(), buildInfoMessage(client));

            }else {
                Main.getInstance().getApi().sendPrivateMessage(client.getId(), Utils.noPermissions);
            }
        }else {
            Main.getInstance().getApi().sendPrivateMessage(client.getId(), buildInfoMessage(client));
        }
        return false;
    }

    public String buildInfoMessage(Client client){
        ClientInfo info = Main.getInstance().getApi().getClientInfo(client.getId());

        StringBuilder builder = new StringBuilder("Clientinfo of " + BBCode.BOLD_START + client.getNickname() + BBCode.BOLD_END + ":\n");
        builder.append(createInfoLine("Current Time Online", (info.getTimeConnected() / 1000) + "s"));
        builder.append(createInfoLine("Total Connections", info.getTotalConnections()));
        builder.append(createInfoLine("Last Connected", info.getLastConnectedDate()));
        builder.append(createInfoLine("First Join", info.getCreatedDate()));
        builder.append(createInfoLine("Total Bytes Out/In", (info.getTotalBytesSent() / 1000000) + "MB" + "/" + (info.getTotalBytesReceived() / 1000000) + "MB"));
        builder.append("\n");
        builder.append(createInfoLine("Client OS", info.getPlatform()));
        builder.append(createInfoLine("Client Version", info.getVersion()));
        builder.append(createInfoLine("IP Adress", info.getIp()));
        return builder.toString();
    }

    public String createInfoLine(String key, Object value){
        return key + ": " + BBCode.BOLD_START + value + BBCode.BOLD_END + "\n";
    }
}
