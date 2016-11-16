package org.rms.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import org.rms.visualizations.ChartCol;
import org.rms.visualizations.ChartResultRow;
import org.rms.visualizations.ChartRow;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by tijo on 2/12/15.
 */
public final class ChartResultRowSerializer implements JsonSerializer<ChartResultRow> {

    private static final Type CHART_COLS_RESULT_TYPE = new TypeToken<List<ChartCol>>() {
    }.getType();

    private static final Type CHART_ROWS_RESULT_TYPE = new TypeToken<List<ChartRow>>() {
    }.getType();


    @Override
    public JsonElement serialize(final ChartResultRow arg0, final Type arg1, final JsonSerializationContext arg2) {
        JsonObject chartDataTable = new JsonObject();
        JsonElement jsonDataTableCols = arg2.serialize(arg0.getChartColumns(), CHART_COLS_RESULT_TYPE);
        JsonElement jsonDataTableRows = arg2.serialize(arg0.getChartRows(), CHART_ROWS_RESULT_TYPE);
        chartDataTable.add("cols", jsonDataTableCols);
        chartDataTable.add("rows", jsonDataTableRows);
        return chartDataTable;
    }
}
