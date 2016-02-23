package com.gang.micro.image.analysis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gang.micro.utils.json.JsonPrintedImpl;

public class Analysis extends JsonPrintedImpl {

    private AnalysisType type;

    private String result;

    public Analysis() {
    }

    public Analysis(AnalysisType analysisType) {
        type = analysisType;
    }

    @JsonProperty
    public String getResult() {
        return result;
    }

    @JsonIgnore
    public void setResult(String result) {
        this.result = result;
    }

    public AnalysisType getType() {
        return type;
    }

    public void setType(AnalysisType type) {
        this.type = type;
    }

}
