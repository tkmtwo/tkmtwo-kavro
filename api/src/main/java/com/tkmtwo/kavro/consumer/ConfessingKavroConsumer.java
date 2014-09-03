package com.tkmtwo.kavro.consumer;
 
import org.apache.avro.generic.GenericContainer;


public class ConfessingKavroConsumer<T extends GenericContainer>
  extends KavroConsumer<T> {
  
	public ConfessingKavroConsumer(final Class<T> dc) {
    super(dc);
	}

  protected void doWithDatum(T t) {
    System.out.println("Confession says: " + t.toString());
  }
  
}

