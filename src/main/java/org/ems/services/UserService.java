package org.ems.services;

import org.ems.models.User;
import org.ems.visualizations.ChartResultContainer;
import org.springframework.ui.Model;

import java.util.List;

/**
 * UserService description
 * User: tijo
 */
public interface UserService {

    Boolean addUserSM(User user);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    List<User> getAllUsersForParishIds(List<Long> parishIds);

    List<User> getAllUsersForPrayerUnitIds(List<Long> prayerUnitIds);

    ChartResultContainer getChartResultContainer(String tqx);

}
