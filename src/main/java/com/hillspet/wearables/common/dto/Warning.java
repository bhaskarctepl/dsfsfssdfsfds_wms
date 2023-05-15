package com.hillspet.wearables.common.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hillspet.wearables.common.constants.WarningCodeInterface;

public class Warning {
    private WarningCodeInterface warningDetail;

    private List<Object> values;

    public Warning(WarningCodeInterface warningDetail, Object... values) {
        super();
        this.warningDetail = warningDetail;
        this.values = (values != null ? Arrays.asList(values) : new ArrayList<>());
    }


    public List<Object> getValues() {
        return values;
    }

    public WarningCodeInterface getWarningDetail() {
        return warningDetail;
    }


    public void setValues(List<Object> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "Warning [warningDetail=" + warningDetail + ", values=" + values + "]";
    }
}
