package org.jzy3d.io.hbase.users;


import java.io.IOException;
import java.util.List;

import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class UserDao {

  public static final byte[] TABLE_NAME = Bytes.toBytes("users");
  public static final byte[] COL_FAMILY = Bytes.toBytes("a");

  public static final byte[] NAME_COL = Bytes.toBytes("name");
  public static final byte[] ADDRESS_COL = Bytes.toBytes("address");
  public static final byte[] EMAIL_COL = Bytes.toBytes("email");
  public static final byte[] PHONE_COL = Bytes.toBytes("phone");

  private static final Logger log = LoggerFactory.getLogger(UserDao.class);

  private HConnection conn;

  public UserDao(HConnection conn) {
    this.conn = conn;
  }

    private static Get buildGet(int id) throws IOException {
    log.debug("Creating Get for {}", id);
    Get g = new Get(Bytes.toBytes(Integer.toString(id)));
    g.addFamily(COL_FAMILY);
    return g;
  }

  private static Put buildPut(User u) {
    log.debug("Creating Put for {}", u);
    Put p = new Put(Bytes.toBytes(Integer.toString(u.getId())));
    p.add(COL_FAMILY, NAME_COL, Bytes.toBytes(u.getName()));
    p.add(COL_FAMILY, ADDRESS_COL, Bytes.toBytes(u.getAddress()));
    p.add(COL_FAMILY, EMAIL_COL, Bytes.toBytes(u.getEmail()));
    p.add(COL_FAMILY, PHONE_COL, Bytes.toBytes(u.getPhone()));
    return p;
  }

  private static Delete buildDelete(int id) {
    log.debug("Creating Delete for {}", id);
    return new Delete(Bytes.toBytes(id));
  }

  private static Scan buildScan() {
    Scan s = new Scan();
    s.addFamily(COL_FAMILY);
    return s;
  }

  private static User buildUser(Result result) {
    int id = Bytes.toInt(result.getRow());
    String name = Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell(COL_FAMILY, NAME_COL)));
    String address = Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell(COL_FAMILY, ADDRESS_COL)));
    String email = Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell(COL_FAMILY, EMAIL_COL)));
    String phone = Bytes.toString(CellUtil.cloneValue(result.getColumnLatestCell(COL_FAMILY, PHONE_COL)));
    return new User(id, name, address, email, phone);
  }

  public void addUser(int id, String name, String address, String email, String phone) throws IOException {
    HTableInterface users = conn.getTable(TABLE_NAME);
    try {
      Put p = buildPut(new User(id, name, address, email, phone));
      users.put(p);
    }
    finally {
      users.close();
    }
  }

  public User getUser(int id) throws IOException {
    HTableInterface users = conn.getTable(TABLE_NAME);
    try {
      Get g = buildGet(id);
      Result result = users.get(g);
      if (result.isEmpty()) {
        log.info("User {} not found.", id);
        return null;
      }

      return buildUser(result);
    }
    finally {
      users.close();
    }
  }

  public void deleteUser(int id) throws IOException {
    HTableInterface users = conn.getTable(TABLE_NAME);
    try {
      Delete d = buildDelete(id);
      users.delete(d);
    }
    finally {
      users.close();
    }
  }

  public List<User> getUsers() throws IOException {
    HTableInterface users = conn.getTable(TABLE_NAME);
    try {
      ResultScanner results = users.getScanner(buildScan());
      List<User> ret = Lists.newArrayList();
      for (Result r : results) {
        ret.add(buildUser(r));
      }
      return ret;
    }
    finally {
      users.close();
    }
  }

}