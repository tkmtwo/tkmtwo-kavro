package com.tkmtwo.kavro.producer;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.MoreConditions.checkNotBlank;

import java.util.Properties;
import kafka.message.Message;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.javaapi.producer.Producer;
import org.apache.avro.generic.IndexedRecord;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.avro.generic.GenericContainer;
import com.tkmtwo.kavro.serde.SpecificDatumEncoder;


public final class KavroProducer<T extends GenericContainer>
  implements InitializingBean, DisposableBean, FactoryBean {
  
  private final Logger logger = LoggerFactory.getLogger(KavroProducer.class);
  
  private Producer<Integer, byte[]> producer;
  private Properties properties = new Properties();
  
  private Class<T> datumClass;
  private SpecificDatumEncoder<T> datumEncoder;
  
	public KavroProducer(final Class<T> dc) {
    datumClass = dc;
	}
  
  public Class<T> getDatumClass() { return datumClass; }
  public void setDatumClass(Class<T> c) { datumClass = c; }
  
  private SpecificDatumEncoder<T> getDatumEncoder() {
    if (datumEncoder == null) {
      datumEncoder = new SpecificDatumEncoder<T>(getDatumClass());
    }
    return datumEncoder;
  }
  
  
  
  
  public void setProducer(Producer<Integer, byte[]> p) { producer = p; }
  public Producer<Integer, byte[]> getProducer() {
    if (producer == null) {
      producer = new Producer<Integer, byte[]>(new ProducerConfig(getProperties()));
    }
    return producer;
  }
  
  public void setProperties(Properties props) { properties = props; }
  public Properties getProperties() {
    if (properties == null) {
      properties = new Properties();
    }
    return properties;
  }

  private String getTopicName(T t) {
    return t.getSchema().getName();
  }
  public void send(T t) {
    send(getTopicName(t), getDatumEncoder().toBytes(t));
  }
  public void send(String topicName, byte[] bytes) {
    producer.send(new KeyedMessage<Integer, byte[]>(topicName, bytes));
  }
    
  
  /*
  public void send(GenericContainer input) {
    SpecificDatumEncoder sde = new SpecificDatumEncoder(input.getClass());
    byte[] bs = sde.toBytes(input);
  }
  
  protected void send(String topicName, byte[] bytes) {
    producer.send(new KeyedMessage<Integer, byte[]>(topicName, bytes));
  }
  */
    
    
  /*    
  public void send(String topicName, IndexedRecord indexedRecord) {
    byte[] bytes = kaEncoder.toBytes(indexedRecord);
    producer.send(new KeyedMessage<Integer, byte[]>(topicName, bytes));
  }
  */
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  private void checkForProperty(String s) {
    checkNotBlank(getProperties().getProperty(s), "Need a property value for " + s);
  }
  /**
   * @see InitializingBean#afterPropertiesSet()
   */
  @Override
  public void afterPropertiesSet() {
    checkForProperty("serializer.class");
    checkForProperty("metadata.broker.list");
    checkForProperty("request.timeout.ms");
    checkForProperty("timeout.ms");
    checkForProperty("message.send.max.retries");


    //datumEncoder = new SpecificDatumEncoder<T>(RawHostEvent.class);
    
    //Go ahead and create the producer
    getProducer();
  }
  
  /**
   * @see FactoryBean#getObject()
   */
  @Override
  public Object getObject() {
    return this;
  }
  
  /**
   * @see FactoryBean#getObjectType()
   */
  @Override
  public Class getObjectType() {
    return KavroProducer.class;
  }
  
  /**
   * @see FactoryBean#isSingleton()
   */
  @Override
  public boolean isSingleton() {
    return true;
  }
  
  /**
   * @see DisposableBean#destroy()
   */
  @Override
  public void destroy() {
  }
  
  
}

