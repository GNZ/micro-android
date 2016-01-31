package com.gang.micro.core.image;

import com.gang.micro.core.analysis.Analysis;
import com.gang.micro.core.utils.json.JsonPrintedImpl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Image extends JsonPrintedImpl {

    private UUID id;
    private List<Analysis> analyses;
    private Date created_at;
    private String description;
    private String name;

    public Image() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Analysis> getAnalyses() {
        return analyses;
    }

    public void setAnalyses(List<Analysis> analyses) {
        this.analyses = analyses;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
