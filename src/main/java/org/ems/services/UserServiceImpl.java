package org.rms.services;

import org.rms.daos.UserDao;
import org.rms.models.User;
import org.rms.visualizations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * UserServiceImpl description
 * User: tijo
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    private List<ChartCol> chartColList;

    public UserServiceImpl() {
        chartColList = new ArrayList<>();
    }

    @Override
    public Boolean addUserSM(User user) {
        userDao.addUserDM(user);
        return true;
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public List<User> getAllUsersForParishIds(List<Long> parishIds) {
        return userDao.getAllUsersForParishIds(parishIds);
    }

    @Override
    public List<User> getAllUsersForPrayerUnitIds(List<Long> prayerUnitIds) {
        return userDao.getAllUsersForPrayerUnitIds(prayerUnitIds);
    }

    @Override
    public ChartResultContainer getChartResultContainer(String tqx) {
        ChartResultContainer chartResultContainer = new ChartResultContainer();
        chartResultContainer.setVersion("0.6");
        chartResultContainer.setReqId(tqx);
        chartResultContainer.setStatus("ok");

        ChartTable chartTable = new ChartTable();

        chartTable.setCol(getChartColumns());

        chartResultContainer.setTable(chartTable);
        chartTable.setRows(getChartRows());

        return chartResultContainer;
    }

    private List<ChartCol> getChartColumns() {
        if (chartColList.isEmpty()) {

            ChartCol chartColOne = new ChartCol("email", "Email", "email", "string");
            ChartCol chartColTwo = new ChartCol("systemrole", "System Role", "systemrole", "string");

            chartColList.add(chartColOne);
            chartColList.add(chartColTwo);
        }
        return chartColList;
    }

    private List<ChartRow> getChartRows() {
        List<ChartRow> chartRowList = new ArrayList<>();

        List<User> allUsers = getAllUsers();

        for (User user : allUsers) {
            List<ChartCell> chartCellList = new ArrayList<>();

            ChartCell<String> chartCellDate = new ChartCell<>(user.getEmail(), user.getEmail());
            ChartCell<String> chartCellJuniorCount = new ChartCell<>(user.getSystemRole().toString(), user.getSystemRole().toString());

            chartCellList.add(chartCellDate);
            chartCellList.add(chartCellJuniorCount);

            chartRowList.add(new ChartRow(chartCellList));
        }

        return chartRowList;
    }

}
