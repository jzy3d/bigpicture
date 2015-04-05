package org.jzy3d.io.hbase.users;


import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserTool {

  private static final Logger log = LoggerFactory.getLogger(UserTool.class);

  public static final String usage = "usertool action ...\n" 
      + "  help - print this message and exit.\n"
      + "  add <id> <name> <address> <email> <phone> - add a new user.\n" 
      + "  save <id> <name> <address> <email> <phone> - update an existing user.\n"
      + "  delete <id> - delete an existing user.\n"
      + "  get <user> - retrieve a specific user.\n" 
      + "  list - list all users.\n";

  public static void main(String[] args) throws IOException {
    if (args.length == 0 || "help".equals(args[0])) {
      System.out.println(usage);
      System.exit(0);
    }

    Configuration conf = HBaseConfiguration.create();
    HConnection conn = HConnectionManager.createConnection(conf);

    try {
      UserDao dao = new UserDao(conn);

      if ("get".equals(args[0])) {
        log.debug(String.format("Getting user %s", args[1]));
        User u = dao.getUser(Integer.valueOf(args[1]));
        System.out.println(u);
      }

      if ("add".equals(args[0]) || "save".equals(args[0])) {
        log.debug("Adding user...");
        dao.addUser(Integer.valueOf(args[1]), args[2], args[3], args[4], args[5]);
        User u = dao.getUser(Integer.valueOf(args[1]));
        System.out.format("Successfully %s user ===> \n%s\n", args[0].equals("add") ? "added" : "saved", u);
      }

      if ("delete".equals(args[0])) {
        dao.deleteUser(Integer.valueOf(args[1]));
        System.out.println("Deleted user " + args[1]);
        log.info(String.format("Deleted user %s\n", args[1]));
      }
      
      if ("list".equals(args[0])) {
        List<User> users = dao.getUsers();
        log.info(String.format("Found %s users.", users.size()));
        for (User u : users) {
          System.out.println(u + "\n");
        }
      }
    }
    finally {
      conn.close();
    }
  }
}