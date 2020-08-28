package com.github.plutobe.plugin.thrift.util;

/**
 * @author plutobe@outlook.com
 * @since 2020-08-27 00:07
 */
public class CommandExecutor {

    public static int execute(String command) throws Exception {
        Process process = Runtime.getRuntime().exec(command);
        new StreamGobbler(process.getErrorStream(), "ERROR").start();
        new StreamGobbler(process.getInputStream(), "STDOUT").start();
        return process.waitFor();
    }

}
