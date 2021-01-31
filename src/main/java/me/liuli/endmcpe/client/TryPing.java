package me.liuli.endmcpe.client;

import com.nukkitx.protocol.bedrock.BedrockClient;
import me.liuli.endmcpe.EndMCPE;
import me.liuli.endmcpe.bedrock.ServerInfo;

import java.net.InetSocketAddress;

public class TryPing implements Runnable{
    private final ServerInfo serverInfo;

    public TryPing(ServerInfo serverInfo){
        this.serverInfo=serverInfo;
    }

    @Override
    public void run() {
        InetSocketAddress bindAddress = new InetSocketAddress("0.0.0.0", serverInfo.getLocalPort());
        BedrockClient client = new BedrockClient(bindAddress);
        InetSocketAddress addressToPing = new InetSocketAddress(serverInfo.getHost(),serverInfo.getPort());
        client.bind().join();
        try {
            client.ping(addressToPing).whenComplete((pong, throwable) -> {
                client.close();
                if (throwable==null) {
                    //server alive
                    EndMCPE.getLogger().info("Pong \""+pong.getMotd()+"\" Online player:"+pong.getPlayerCount()+" from server "+serverInfo.toString()+"!");
                    new FakeClient(serverInfo);
                }
            }).join();
        }catch (Exception e){
            EndMCPE.getLogger().info("Cannot ping server "+serverInfo.toString()+" with "+e.toString());
            client.close();
        }
    }
}
