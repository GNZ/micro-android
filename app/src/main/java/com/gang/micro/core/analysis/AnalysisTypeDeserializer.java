package com.gang.micro.core.analysis;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class AnalysisTypeDeserializer extends JsonDeserializer<AnalysisType> {

    @Override
    public AnalysisType deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {

        JsonNode node = jp.getCodec().readTree(jp);

        AnalysisType analysisType = AnalysisType.getKey(node.asText());

        return analysisType;
    }
}
