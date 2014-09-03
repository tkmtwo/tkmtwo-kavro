package com.tkmtwo.kavro.serde;

import kafka.serializer.Decoder;

import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.generic.GenericContainer;

public class SpecificDatumDecoder<T extends GenericContainer>
  extends DatumSerDe<T> implements Decoder<T> {
  
	private final DatumReader<T> datumReader;
  
	public SpecificDatumDecoder(final Class<T> specificRecordBase) {
		datumReader = new SpecificDatumReader<T>(specificRecordBase);
	}
  
	@Override
	public T fromBytes(final byte[] bytes) {
		return fromBytes(bytes, datumReader);
	}
  
}
