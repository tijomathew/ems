package org.rms.visualizations;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tijo on 2/12/15.
 */
public final class ChartRow {

    @SerializedName("c")
    private ChartCell[] chartCells;

    public ChartRow() {
    }

    public ChartRow(List<ChartCell> chartCellList) {
        chartCells = new ChartCell[chartCellList.size()];
        this.chartCells = chartCellList.toArray(chartCells);
    }

    public ChartCell[] getChartCells() {
        return chartCells;
    }

    public void setChartCells(ChartCell[] chartCells) {
        this.chartCells = chartCells;
    }
}
