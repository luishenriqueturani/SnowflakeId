# SnowflakeId
 Para a geração de SnowflakeIds, IDs únicos baseados em timestamps e contadores, seguindo o estilo utilizado nos IDs do Twitter.

## Como usar

```java
SnowflakeId snowflake = new SnowflakeId(1, 1);
```

A classe possui dois parâmetros para o construtor, o workerId e o datacenterId, ambos possuem o mesmo limite de valor, podendo ser alterado em código.

```java
SnowflakeId snowflake = new SnowflakeId(31, 31); // no limite de ambos
```

Tendo criado o objeto, podemos gerar um novo ID com o método `nextId()`

```java
long newId = snowflake.nextId();
```

O método retorna um long, o qual é o ID atualizado.

### Métodos auxiliares

```java
long workerId = snowflake.extractWorkerId();
```

Retorna o workerId.

```java
long datacenterId = snowflake.extractDatacenterId();
```

Retorna o datacenterId.

```java
long timestamp = snowflake.extractTimestamp();
```

Retorna o timestamp atual.

```java
long sequence = snowflake.extractSequence();
```

Retorna o sequence atual.

```java
SnowflakeParams params = snowflake.extractAll();
```

Retorna um objeto SnowflakeParams com os valores de workerId, datacenterId, timestamp e sequence atual.

## Licença

MIT

