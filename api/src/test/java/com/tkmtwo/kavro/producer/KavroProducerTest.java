package com.tkmtwo.kavro.producer;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import aisp.avro.RawHostEvent;  

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.common.collect.ImmutableMap;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.Schema;
import java.util.Properties;


import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class KavroProducerTest {

  @Autowired
  KavroProducer<RawHostEvent> kavroProducer;
  
  private RawHostEvent newRawHostEvent(int i) {
    long someTime = System.currentTimeMillis();
    
    RawHostEvent rhe = 
      aisp.avro.RawHostEvent.newBuilder()
      .setRecordTenantName("GregSomeTenantName" + String.valueOf(i))
      .setRecordSourceName("SomeRecordSourceName")
      .setRecordSourceId("SomeSourceId" + String.valueOf(i))
      .setRecordInstant(someTime)
      .setEventName("SomeEventName")
      .setEventSeverity(3)
      .setEventInstant(someTime)
      .setEventMessage("SomeEventMessage" + String.valueOf(i))
      .setHostName("somehost.somedomain.com")
      .build();
    return rhe;
  }
  
  
  

  @Test
  public void testThis() {
    for (int i = 0; i < 5; i++) {
      kavroProducer.send(newRawHostEvent(i));
    }
  }
  
  
}
