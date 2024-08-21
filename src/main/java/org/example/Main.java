package org.example;

public class Main {
  public static void main(String[] args) {
    SnowflakeId id = new SnowflakeId(1, 10);
    SnowflakeId id2 = new SnowflakeId(15, 10);
    for (int i = 0; i < 10; i++) {
      System.out.println(id.nextId());
      System.out.println(id2.nextId());
    }
    //System.out.println(id.nextId());

  }
}