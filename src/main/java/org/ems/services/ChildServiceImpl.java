package org.ems.services;

import org.ems.daos.ChildDao;
import org.ems.models.StudentNode;
import org.ems.visualizations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bibin on 13/10/16.
 */

@Service
@Transactional
public class ChildServiceImpl implements ChildService {

    @Autowired
    private ChildDao childDao;

    private List<ChartCol> chartColList;

    public ChildServiceImpl() {
        chartColList = new ArrayList<>();
    }

    @Override
    public Long getAllRegisteredPeople(String registeredDate, String inOutFlag) {
        if (registeredDate == null) {
            throw new IllegalArgumentException("registeredDate cannot be null!!..");
        }

        return childDao.getAllRegisteredPeople(registeredDate, inOutFlag);
    }

    @Override
    public ChartResultContainer getChartResultContainer(String tqx, String inOutFlag) {

        ChartResultContainer chartResultContainer = new ChartResultContainer();
        chartResultContainer.setVersion("0.6");
        chartResultContainer.setReqId(tqx);
        chartResultContainer.setStatus("ok");

        ChartTable chartTable = new ChartTable();

        chartTable.setCol(getChartColumns());

        chartResultContainer.setTable(chartTable);
        chartTable.setRows(getChartRows(inOutFlag));

        return chartResultContainer;
    }

    private List<ChartCol> getChartColumns() {
        if (chartColList.isEmpty()) {

            ChartCol chartColOne = new ChartCol("date", "Date", "date", "string");
            ChartCol chartColTwo = new ChartCol("people", "People", "people", "number");

            chartColList.add(chartColOne);
            chartColList.add(chartColTwo);
        }
        return chartColList;
    }

    private List<ChartRow> getChartRows(String inOutFlag) {
        List<ChartRow> chartRowList = new ArrayList<>();

        String[] date = new String[]{"Dec-27", "Dec-28", "Dec-29"};
        String[] registeredDates = new String[]{"27-12-2016", "28-12-2016", "29-12-2016"};

        for (int i = 0; i < 3; i++) {

            List<ChartCell> chartCellList = new ArrayList<>();

            ChartCell<String> chartCellDate = new ChartCell<>(date[i], date[i]);
            ChartCell<Long> chartCellAllPeopleCount = new ChartCell<>(getAllRegisteredPeople(registeredDates[i], inOutFlag), getAllRegisteredPeople(registeredDates[i], inOutFlag).toString());

            chartCellList.add(chartCellDate);
            chartCellList.add(chartCellAllPeopleCount);

            chartRowList.add(new ChartRow(chartCellList));
        }

        return chartRowList;
    }

    @Override
    public List<StudentNode> getChildDetails(Long parentId) {
        return childDao.getChildDetails(parentId);
    }

    @Override
    public List<StudentNode> getChildsByIds(List<Long> childIds) {
        return childDao.getChildsByIds(childIds);
    }
}
