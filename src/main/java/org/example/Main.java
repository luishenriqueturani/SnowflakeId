package org.example;

public class Main {
  public static void main(String[] args) {
    SnowflakeId snowflake = new SnowflakeId(31, 31);
    for (int i = 0; i < 10; i++) {
      System.out.println(snowflake.nextId());
    }

    long id1 = snowflake.nextId();

    System.out.println(snowflake.extractTimestamp(id1));
    System.out.println(snowflake.extractDatacenterId(id1));
    System.out.println(snowflake.extractWorkerId(id1));
    System.out.println(snowflake.extractSequence(id1));

    System.out.println(snowflake.extractAll(id1));


  }
}