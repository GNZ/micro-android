package com.gang.micro.core.image.analysis;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class AnalysisTypeSerializer extends JsonSerializer<AnalysisType> {

    @Override
    public void serialize(AnalysisType value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeString(value.getValue());
    }
}
