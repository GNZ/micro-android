package com.gang.micro.core.image.analysis;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gang.micro.core.utils.json.JsonPrintedImpl;

public class Analysis extends JsonPrintedImpl {

    private AnalysisType type;

    private String result;

    public Analysis() {

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
