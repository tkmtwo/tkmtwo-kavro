package com.tkmtwo.kavro.serde;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import aisp.avro.RawHostEvent;  

import org.junit.Before;
import org.junit.Test;
import com.google.common.collect.ImmutableMap;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.Schema;
import java.util.Properties;

public class SerDeTest {


  private RawHostEvent newRawHostEvent() { // throws Exception {
    long someTime = 73L;
    
    RawHostEvent rhe = 
      aisp.avro.RawHostEvent.newBuilder()
      .setRecordTenantName("SomeTenantName")
      .setRecordSourceName("SomeRecordSourceName")
      .setRecordSourceId("SomeSourceId")
      .setRecordInstant(someTime)
      .setEventName("SomeEventName")
      .setEventSeverity(3)
      .setEventInstant(someTime)
      .setEventMessage("SomeEventMessage")
      .setHostName("somehost.somedomain.com")
      .build();
    return rhe;
  }
  

  @Test
  public void testSerDe() {
    RawHostEvent rheIn = newRawHostEvent();
    SpecificDatumEncoder<RawHostEvent> rheEncoder =
      new SpecificDatumEncoder<RawHostEvent>(RawHostEvent.class);
    
    byte[] ba = rheEncoder.toBytes(rheIn);
    
    SpecificDatumDecoder<RawHostEvent> rheDecoder =
      new SpecificDatumDecoder<RawHostEvent>(RawHostEvent.class);
    
    RawHostEvent rheOut = rheDecoder.fromBytes(ba);


    assertEquals(rheIn.toString(), rheOut.toString());
    assertEquals(rheIn, rheOut);

  }
    
  
}
