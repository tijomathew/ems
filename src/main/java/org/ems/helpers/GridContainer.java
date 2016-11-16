package org.rms.helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tijo on 1/12/14.
 */
public final class GridContainer {

    private Integer total;
    private Integer page;
    private Integer records;

    private List<? extends GridRow> rows = new ArrayList<GridRow>();

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRecords() {
        return records;
    }

    public void setRecords(Integer records) {
        this.records = records;
    }

    public List<? extends GridRow> getRows() {
        return rows;
    }

    public void setRows(List<? extends GridRow> rows) {
        this.rows = rows;
    }
}
