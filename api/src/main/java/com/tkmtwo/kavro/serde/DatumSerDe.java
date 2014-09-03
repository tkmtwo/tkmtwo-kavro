package com.tkmtwo.kavro.serde;

import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.generic.GenericContainer;

import java.io.IOException;

public abstract class DatumSerDe<T extends GenericContainer> {
  
	private final SerDe<T> serDe;
  
	protected DatumSerDe() {
		serDe = new SerDe<T>();
	}
  
	public byte[] toBytes(final T source, final DatumWriter<T> writer) {
		try {
			return serDe.serialize(source, writer);
		} catch (IOException ioex) {
      throw new SerDeException("Failed to encode source.", ioex);
    }
	}
  
	public T fromBytes(final byte[] bytes, final DatumReader<T> reader) {
		try {
			return serDe.deserialize(bytes, reader);
		} catch (IOException ioex) {
      throw new SerDeException("Failed to decode bytes.", ioex);
    }
	}
  
}
