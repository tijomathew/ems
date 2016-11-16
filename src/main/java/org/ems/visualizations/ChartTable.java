package org.rms.visualizations;

import java.util.List;

/**
 * Created by tijo on 2/12/15.
 */
public final class ChartTable implements ChartResultRow {

    private List<ChartCol> col;

    private List<ChartRow> row;

    public ChartTable() {
    }

    public List<ChartCol> getCol() {
        return col;
    }

    public void setCol(List<ChartCol> col) {
        this.col = col;
    }

    public List<ChartRow> getRows() {
        return row;
    }

    public void setRows(List<ChartRow> rows) {
        this.row = rows;
    }

    @Override
    public List<ChartCol> getChartColumns() {
        return this.col;
    }

    @Override
    public List<ChartRow> getChartRows() {
        return this.row;
    }
}
