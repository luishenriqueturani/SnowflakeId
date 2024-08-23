package org.example;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class SnowflakeIdTest {
  private static SnowflakeId idGenerator;

  @BeforeAll
  public static void setUp() {
    idGenerator = new SnowflakeId(1, 1);
  }

  @Test
  public void testNextId() {
    long id1 = idGenerator.nextId();
    long id2 = idGenerator.nextId();

    assertNotEquals(id1, id2,"IDs should be unique");
  }

  @Test
  public void testExtractTimestamp() {
    long id = idGenerator.nextId();
    long timestamp = idGenerator.extractTimestamp(id);

    // Convert extracted timestamp to current time range
    long currentTimestamp = System.currentTimeMillis();
    assertTrue(timestamp <= currentTimestamp,"Extracted timestamp should be less than or equal to current time");
    assertTrue(timestamp >= currentTimestamp - 1000,"Extracted timestamp should be within reasonable range");
  }

  @Test
  public void testExtractDatacenterId() {
    long id = idGenerator.nextId();
    long datacenterId = idGenerator.extractDatacenterId(id);

    assertEquals(1, datacenterId,"Datacenter ID should match");
  }

  @Test
  public void testExtractWorkerId() {
    long id = idGenerator.nextId();
    long workerId = idGenerator.extractWorkerId(id);

    assertEquals(1, workerId,"Worker ID should match");
  }

  @Test
  public void testExtractSequence() {
    // Generate multiple IDs to ensure sequence is incrementing
    long id1 = idGenerator.nextId();
    long id2 = idGenerator.nextId();

    long sequence1 = idGenerator.extractSequence(id1);
    long sequence2 = idGenerator.extractSequence(id2);

    assertTrue(sequence2 > sequence1,"Sequence should increment with each ID");
  }

  @Test
  public void testInvalidWorkerId() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new SnowflakeId(32, 1); // 32 is out of the valid range (0-31 for 5 bits)
    });

    assertEquals("Worker ID must be between 0 and 31", exception.getMessage());
  }

  @Test
  public void testInvalidDatacenterId() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      new SnowflakeId(1, 32); // 32 is out of the valid range (0-31 for 5 bits)
    });

    assertEquals("Datacenter ID must be between 0 and 31", exception.getMessage());
  }
}