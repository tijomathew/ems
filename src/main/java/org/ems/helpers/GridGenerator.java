package org.rms.helpers;

import java.util.List;

/**
 * Created by tijo on 9/12/14.
 */
public final class GridGenerator {


    public GridContainer createGridContainer(Integer displayRowsCount, Integer currentDisplayPageNumber, Integer totalRecords, List<? extends GridRow> rowsResult) {
        GridContainer gridContainer = new GridContainer();
        Double totalDisplayPagesCount;
        try {
            totalDisplayPagesCount = (Math.ceil(totalRecords.doubleValue() / displayRowsCount.doubleValue()));
        } catch (ArithmeticException ex) {
            totalDisplayPagesCount = 0d;
        }
        Integer totalPagesDisplayCount = totalDisplayPagesCount.intValue();
        if (totalRecords != 0 && totalPagesDisplayCount == 0)
            totalPagesDisplayCount = 1;


        if (totalRecords == 0l)
            currentDisplayPageNumber = 0;
        gridContainer.setTotal(totalPagesDisplayCount.intValue());
        gridContainer.setPage(currentDisplayPageNumber);
        gridContainer.setRecords(totalRecords);
        gridContainer.setRows(rowsResult);
        return gridContainer;
    }

}
