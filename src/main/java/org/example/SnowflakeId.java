package org.example;

public class SnowflakeId {

  private final long epoch = 1609459200000L; // Custom epoch (e.g., January 1, 2021)
  private final long workerIdBits = 5L; // Number of bits allocated for worker ID
  private final long datacenterIdBits = 5L; // Number of bits allocated for datacenter ID
  private final long sequenceBits = 12L; // Number of bits allocated for sequence
  private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits; // Left shift for timestamp

  private long workerId; // Worker ID
  private long datacenterId; // Datacenter ID
  private long sequence = 0L; // Sequence number
  private long lastTimestamp = -1L; // Last timestamp

  public SnowflakeId(long workerId, long datacenterId) {
    // Maximum value for worker ID
    long maxWorkerId = (1L << workerIdBits) - 1;
    if (workerId > maxWorkerId || workerId < 0) {
      throw new IllegalArgumentException(String.format("Worker ID must be between 0 and %d", maxWorkerId));
    }
    // Maximum value for datacenter ID
    long maxDatacenterId = (1L << datacenterIdBits) - 1;
    if (datacenterId > maxDatacenterId || datacenterId < 0) {
      throw new IllegalArgumentException(String.format("Datacenter ID must be between 0 and %d", maxDatacenterId));
    }
    this.workerId = workerId;
    this.datacenterId = datacenterId;
  }

  public synchronized long nextId() {
    long timestamp = currentTimeMillis();

    if (timestamp < lastTimestamp) {
      throw new RuntimeException("Clock moved backwards. Refusing to generate id.");
    }

    if (timestamp == lastTimestamp) {
      // Sequence mask
      long sequenceMask = (1L << sequenceBits) - 1;
      sequence = (sequence + 1) & sequenceMask;
      if (sequence == 0) {
        timestamp = waitNextMillis(lastTimestamp);
      }
    } else {
      sequence = 0L;
    }

    lastTimestamp = timestamp;
    // Left shift for datacenter ID
    long datacenterIdShift = sequenceBits + workerIdBits;

    //cada parte do ID é deslocada para a esquerda para ocupar os bits corretos dentro do ID final, garantindo que cada campo esteja na posição correta e não sobreponha os outros.
    // Left shift for worker ID

    return ((timestamp - epoch) << timestampLeftShift) |
        (datacenterId << datacenterIdShift) |
        (workerId << sequenceBits) |
        sequence;
  }

  private long waitNextMillis(long lastTimestamp) {
    long timestamp = currentTimeMillis();
    while (timestamp <= lastTimestamp) {
      timestamp = currentTimeMillis();
    }
    return timestamp;
  }

  private long currentTimeMillis() {
    return System.currentTimeMillis();
  }

  // Additional getters for testing purposes
  public long getWorkerId() {
    return workerId;
  }

  public long getDatacenterId() {
    return datacenterId;
  }

  public long getTimestampLeftShift() {
    return timestampLeftShift;
  }

}
