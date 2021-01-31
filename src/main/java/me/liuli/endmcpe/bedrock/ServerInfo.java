package me.liuli.endmcpe.bedrock;

import lombok.Getter;
import lombok.Setter;

public class ServerInfo {
    @Getter
    private final String host;
    @Getter
    private final int port;
    @Getter
    @Setter
    private int localPort;

    public ServerInfo(String host,int port){
        this.host=host;
        this.port=port;
    }

    @Override
    public String toString(){
        return host+":"+port;
    }
}
