package com.tkmtwo.kavro.serde;

import kafka.serializer.Encoder;

import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.generic.GenericContainer;


public class SpecificDatumEncoder<T extends GenericContainer>
  extends DatumSerDe<T> implements Encoder<T> {
  
	private final DatumWriter<T> datumWriter;
  
	public SpecificDatumEncoder(final Class<T> specificRecordClass) {
		datumWriter = new SpecificDatumWriter<T>(specificRecordClass);
	}
  
	@Override
    public byte[] toBytes(final T source) {
		return toBytes(source, datumWriter);
	}
}
