package me.liuli.endmcpe;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.v422.Bedrock_v422;
import lombok.Getter;
import me.liuli.endmcpe.bedrock.ServerInfo;
import me.liuli.endmcpe.client.TryPing;
import me.liuli.endmcpe.utils.FileUtils;
import me.liuli.endmcpe.utils.RandUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class EndMCPE {
    @Getter
    private static Logger logger;
    @Getter
    private static int checkDelay;
    @Getter
    private static Vector<ServerInfo> servers;
    @Getter
    private static Timer timer;
    @Getter
    private static final BedrockPacketCodec bedrockCodec= Bedrock_v422.V422_CODEC;

    public static void main(String[] args) {
        logger=LogManager.getLogger(EndMCPE.class);
        logger.warn("Powered by the EZ4H technology!");
        logger.info("Made by Liulihaocai！");

        File cfgFile=new File("./config.json");
        if(!cfgFile.exists()){
            try {
                Writer writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(cfgFile), StandardCharsets.UTF_8));
                writer.write("{\n" +
                        "  \"check\": 60,\n" +
                        "  \"servers\": [\n" +
                        "    {\"host\": \"127.0.0.1\",\"port\": 19132}\n" +
                        "  ]\n" +
                        "}");
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        JSONObject config=JSONObject.parseObject(FileUtils.readFile(cfgFile));
        checkDelay=config.getInteger("check");
        JSONArray serverList=config.getJSONArray("servers");
        servers=new Vector<>();
        for(Object obj:serverList){
            JSONObject data=(JSONObject)obj;
            servers.add(new ServerInfo(data.getString("host"),data.getInteger("port")));
        }

        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                EndMCPE.logger.warn("Scanning server...");
                for(ServerInfo serverInfo:servers){
                    serverInfo.setLocalPort(RandUtils.rand(10000,50000));
                    new Thread(new TryPing(serverInfo)).start();
                }
            }
        },1000,checkDelay*1000L);

        logger.info("Loaded.");
    }
}
