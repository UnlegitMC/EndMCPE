package me.liuli.endmcpe.client;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.BedrockSession;
import com.nukkitx.protocol.bedrock.handler.BatchHandler;
import com.nukkitx.protocol.bedrock.packet.DisconnectPacket;
import io.netty.buffer.ByteBuf;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

public class ClientBatchHandler implements BatchHandler {
    @Override
    public void handle(BedrockSession bedrockSession, ByteBuf byteBuf, Collection<BedrockPacket> collection) {
        for (BedrockPacket packet : collection) {
            if(packet instanceof DisconnectPacket){
                DisconnectPacket disconnectPacket=new DisconnectPacket();
                disconnectPacket.setKickMessage("disconnect");
                disconnectPacket.setMessageSkipped(false);
                bedrockSession.sendPacket(disconnectPacket);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        bedrockSession.disconnect();
                    }
                },100);
            }
        }
    }
}
