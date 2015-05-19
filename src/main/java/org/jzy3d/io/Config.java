package org.jzy3d.io;

public class Config {
    public static String PORT_DEFAULT_HIVE = "10000";
    
    public String ip = "172.16.255.136";
    public String port = PORT_DEFAULT_HIVE;
    public String domain = "default";
    public String user = "root";
    public String password = "hadoop";
    
    public Config(String ip, String port, String domain, String user, String password) {
        this.ip = ip;
        this.port = port;
        this.domain = domain;
        this.user = user;
        this.password = password;
    }

    public static class HiveConnection extends Config{
        public HiveConnection(String ip, String domain, String user, String password) {
            super(ip, PORT_DEFAULT_HIVE, domain, user, password);
        }
        
        public HiveConnection(String ip, String port, String domain, String user, String password) {
            super(ip, port, domain, user, password);
        }
    }
}
