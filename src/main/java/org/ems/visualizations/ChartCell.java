package org.rms.visualizations;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tijo on 2/12/15.
 */
public final class ChartCell<T> {

    @SerializedName("v")
    private T value;

    @SerializedName("f")
    private String format;

    public ChartCell() {
    }

    public ChartCell(T value, String format) {
        this.value = value;
        this.format = format;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
