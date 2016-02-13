package com.alincode.hammock;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.net.URLDecoder;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HammockAgent {
    static final Logger logger = LoggerFactory.getLogger(HammockAgent.class);

    public static void premain(String agentArgument, Instrumentation instrumentation) {
        try {
            //stackoverflow.com/questions/320542/how-to-get-the-path-of-a-running-jar-file
            String jarFileLocation = HammockAgent.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String decodedPath = URLDecoder.decode(jarFileLocation, "UTF-8");

            instrumentation.appendToBootstrapClassLoaderSearch(new JarFile(decodedPath));
            instrumentation.addTransformer(new HammockTransformer(agentArgument));
            logger.info("loaded agent");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
