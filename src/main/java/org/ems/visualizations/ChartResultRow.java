package org.rms.visualizations;


import java.util.List;

/**
 * Created by tijo on 2/12/15.
 */
public interface ChartResultRow {

    List<ChartCol> getChartColumns();

    List<ChartRow> getChartRows();

}
