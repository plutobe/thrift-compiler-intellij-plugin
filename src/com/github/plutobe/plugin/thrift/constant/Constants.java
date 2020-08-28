package com.github.plutobe.plugin.thrift.constant;

/**
 * @author plutobe@outlook.com
 * @since 2020-08-23 21:01
 */
public interface Constants {

    String THRIFT_STR = "thrift";

    String JAVA_STR = "java";

    String THRIFT_SUFFIX = ".thrift";

    String THRIFT_DEFAULT_PATH = "/usr/local/opt/thrift/bin/thrift";

    String THRIFT_FILE_PATH = "/src/main/thrift";

    String COMMAND_TEMPLATE = "%s -out %s -r -gen java %s";

    String THRIFT_PATH_PROPERTY_KEY = "THRIFT_PATH";

}
