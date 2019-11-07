package com.chefling.app.response;

import com.chefling.app.models.SubForescastModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastResponse {
    @SerializedName("list")
    private List<SubForescastModel> list;

    public List<SubForescastModel> getList() {
        return list;
    }

    public void setList(List<SubForescastModel> list) {
        this.list = list;
    }
}
