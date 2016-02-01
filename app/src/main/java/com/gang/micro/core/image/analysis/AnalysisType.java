package com.gang.micro.core.image.analysis;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonDeserialize(using = AnalysisTypeDeserializer.class)
@JsonSerialize(using = AnalysisTypeSerializer.class)
public enum AnalysisType {
    BLOOD__RED_CELL_COUNT("BLOOD_RED-CELL-COUNT");

    private String value;

    AnalysisType(String value) {
        this.value = value;
    }

    static public AnalysisType getKey(String value) {
        for (AnalysisType analysisType : AnalysisType.class.getEnumConstants()) {
            if (analysisType.value.equals(value)) {
                return analysisType;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }
}

