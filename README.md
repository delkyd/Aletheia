<img src="/logo/Aletheia-logo.png" height="115"></img>
![](https://github.com/outbrain/Aletheia/blob/master/logo/Aletheia.png)

Aletheia is a library that provides a uniform and extendable way for moving streaming data between endpoints with a built in, fine-grained visibility.

Aletheia’s key features are:
* Datum-level near real time visibility
* Pluggable endpoint types 
* Pluggable serialization formats

# Endpoint Types
The following endpoint types are supported out-of-the-box:
* Kafka 0.7 (production and consumption)
* Kafka 0.8 (production and consumption)
* Log files (production only)

Custom endpoint types are easy to write. See the [Wiki](https://github.com/outbrain/Aletheia/wiki/Production-%26-Consumption-EndPoint-types) for details.

# Datum Production Example
Build a Datum Producer once:

```java
DatumProducer<Click> datumProducer = 
    DatumProducerBuilder
      .forDomainClass(Click.class)
      .registerProductionEndPointType(KafkaTopicProductionEndPoint.class,
                                      new KafkaDatumEnvelopeSenderFactory())
      .deliverDataTo(new KafkaTopicProductionEndPoint(...), 
                     new JsonDatumSerDe<Click>(Click.class))
      .build(new DatumProducerConfig(...));
```

Then, produce away:

```java
datumProducer.deliver(new Click(...));
```

# Datum Consumption Example

Build a DatumConsumerStream list once:

```java
List<DatumConsumerStream<Click>> datumConsumerStreams =
    DatumConsumerStreamsBuilder
        .forDomainClass(Click.class)
        .registerConsumptionEndPointType(KafkaTopicConsumptionEndPoint.class,
                                         new KafkaDatumEnvelopeFetcherFactory())
        .consumeDataFrom(new KafkaTopicConsumptionEndPoint(...), 
                         new JsonDatumSerDe<Click>(Click.class))
        .build(new DatumConsumerStreamConfig(1, "localhost"));
```

Then, consume away:

```java
// parallelism is 1, so we take the first DatumConsumerStream and forget about the list
DatumConsumerStream<Click> clickStream = Iterables.getFirst(datumConsumerStreams, null);

// blocking
for (final Click click : clickStream.datums()) {
 // handling logic goes here
}
```

# Usage

First, have the aletheia-core jar included in your pom:

```xml
<dependency>
  <groupId>com.outbrain.aletheia</groupId>
  <artifactId>aletheia-core</artifactId>
  <version>0.17</version>
</dependency>
```

Then, include the aletheia extensions you'll be using, which can be one or more of the following:

```xml
<dependency>
  <groupId>com.outbrain.aletheia</groupId>
  <artifactId>aletheia-kafka0.7</artifactId>
  <version>0.17</version>
</dependency>
```

```xml
<dependency>
  <groupId>com.outbrain.aletheia</groupId>
  <artifactId>aletheia-kafka0.8</artifactId>
  <version>0.17</version>
</dependency>
```

```xml
<dependency>
  <groupId>com.outbrain.aletheia</groupId>
  <artifactId>aletheia-log4j</artifactId>
  <version>0.17</version>
</dependency>
```


If you prefer building Aletheia yourself, please see the [Hello Datum!](https://github.com/outbrain/Aletheia/wiki/Hello-Datum%21) wiki page.

# Documentation
*  [Wiki](https://github.com/outbrain/Aletheia/wiki)
*  [Javadoc](http://outbrain.github.io/Aletheia/)

# Developers
Aletheia has been developed by the data infrastructure team at Outbrain.   
Please feel free to contact us for any details:

* Stas Levin - <slevin@outbrain.com>
* Harel Ben-Attia - <harel@outbrain.com>
* Izik Shmulewitz - <ishmulewitz@outbrain.com>
