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
    public Long getAllRegisteredPeople(String registeredDate, String inOutFlag, String ageRange) {
        if (registeredDate == null) {
            throw new IllegalArgumentException("registeredDate cannot be null!!..");
        }

        return childDao.getAllRegisteredPeople(registeredDate, inOutFlag, ageRange);
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
            ChartCol chartColTwo = new ChartCol("0 and < 4", "0 and < 4", "0 and < 4", "number");
            ChartCol chartColThree = new ChartCol("4 and < 7", "4 and < 7", "4 and < 7", "number");
            ChartCol chartColFour = new ChartCol("7 and < 13", "7 and < 13", "7 and < 13", "number");
            ChartCol chartColFive = new ChartCol("13 and above", "13 and above", "13 and above", "number");
            ChartCol chartColSix = new ChartCol("total", "Total", "total", "number");
            ChartCol chartColSeven = new ChartCol("familyCount", "Family Count", "familyCount", "number");

            chartColList.add(chartColOne);
            chartColList.add(chartColTwo);
            chartColList.add(chartColThree);
            chartColList.add(chartColFour);
            chartColList.add(chartColFive);
            chartColList.add(chartColSix);
            chartColList.add(chartColSeven);
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
            ChartCell<Long> chartCell0to4Count = new ChartCell<>(getAllRegisteredPeople(registeredDates[i], inOutFlag, "0 and < 4"), getAllRegisteredPeople(registeredDates[i], inOutFlag, "0 and < 4").toString());
            ChartCell<Long> chartCell4to7Count = new ChartCell<>(getAllRegisteredPeople(registeredDates[i], inOutFlag, "4 and < 7"), getAllRegisteredPeople(registeredDates[i], inOutFlag, "4 and < 7").toString());
            ChartCell<Long> chartCell7to13Count = new ChartCell<>(getAllRegisteredPeople(registeredDates[i], inOutFlag, "7 and < 13"), getAllRegisteredPeople(registeredDates[i], inOutFlag, "7 and < 13").toString());
            ChartCell<Long> chartCellGreater13Count = new ChartCell<>(getAllRegisteredPeople(registeredDates[i], inOutFlag, "13 and above"), getAllRegisteredPeople(registeredDates[i], inOutFlag, "13 and above").toString());

            Long totalCountByRowWise = getAllRegisteredPeople(registeredDates[i], inOutFlag, "0 and < 4") + getAllRegisteredPeople(registeredDates[i], inOutFlag, "4 and < 7") + getAllRegisteredPeople(registeredDates[i], inOutFlag, "7 and < 13") + getAllRegisteredPeople(registeredDates[i], inOutFlag, "13 and above");

            ChartCell<Long> chartCellTotalCount = new ChartCell<>(totalCountByRowWise, totalCountByRowWise.toString());

            ChartCell<Long> chartCellFamilyCount = new ChartCell<>(getAllRegisteredFamily(registeredDates[i], inOutFlag, null), getAllRegisteredFamily(registeredDates[i], inOutFlag, null).toString());

            chartCellList.add(chartCellDate);
            chartCellList.add(chartCell0to4Count);
            chartCellList.add(chartCell4to7Count);
            chartCellList.add(chartCell7to13Count);
            chartCellList.add(chartCellGreater13Count);
            chartCellList.add(chartCellTotalCount);
            chartCellList.add(chartCellFamilyCount);

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

    @Override
    public Long getAllRegisteredFamily(String registeredDate, String inOutFlag, String ageRange) {
        if (registeredDate == null) {
            throw new IllegalArgumentException("registeredDate cannot be null!!..");
        }

        return childDao.getAllRegisteredFamilyCount(registeredDate, inOutFlag, ageRange);
    }
}
