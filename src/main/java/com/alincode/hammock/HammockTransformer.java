package com.alincode.hammock;

import javassist.*;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class HammockTransformer implements ClassFileTransformer {
    private final String configLocation;
    public HammockTransformer(String configLocation) {
        this.configLocation = configLocation;
    }

    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {
        if("java/nio/channels/spi/SelectorProvider".equals(className)) {
            try {
                ClassPool pool = ClassPool.getDefault();
                CtClass cc = pool.get("java.nio.channels.spi.SelectorProvider");
                CtMethod m = cc.getDeclaredMethod("provider");
                m.setName("realProvider");
                m.insertBefore("System.out.println(\"loaded "+className+"\");");
                /*CtMethod replacementProvider = CtNewMethod.make(
                        "public static java.nio.channels.spi.SelectorProvider provider() {" +
                                "return new com.alincode.hammock.net.WrappedProvider(" +
                                "java.nio.channels.spi.SelectorProvider.realProvider()," +
                                "Configuration.buildConfiguration(" +
                                "\""+configLocation+"\"));}",cc);
                                */
                CtMethod replacementProvider = CtNewMethod.make(
                        "public static java.nio.channels.spi.SelectorProvider provider() {" +
                                "return new com.alincode.hammock.net.WrappedProvider(" +
                                "java.nio.channels.spi.SelectorProvider.realProvider()," +
                                "null);}",cc);
                cc.addMethod(replacementProvider);
                byte[] bytecode = cc.toBytecode();
                cc.detach();
                return bytecode;
            } catch (NotFoundException | CannotCompileException | IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[0];
    }
}
