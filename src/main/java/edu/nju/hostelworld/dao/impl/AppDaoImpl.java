package edu.nju.hostelworld.dao.impl;

import edu.nju.hostelworld.dao.AppDao;
import edu.nju.hostelworld.model.App;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.stereotype.Repository;

/**
 * Created by Sorumi on 17/2/11.
 */
@Repository
public class AppDaoImpl extends BaseDaoImpl implements AppDao {

    public ResultMessage updateApp(App app) {
       return update(app);
    }

    public App findApp() {
       return (App) findByID(App.class, "0");
    }
}
