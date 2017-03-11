package edu.nju.hostelworld.service.impl;

import edu.nju.hostelworld.dao.AppDao;
import edu.nju.hostelworld.model.App;
import edu.nju.hostelworld.service.AppService;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sorumi on 17/2/11.
 */
@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private AppDao appDao;


    @Override
    public App findApp() {
        return appDao.findApp();
    }

    @Override
    public ResultMessage updateMoney(double money) {
        App app = appDao.findApp();
        app.setMoney(app.getMoney() + money);
        return appDao.updateApp(app);
    }

    @Override
    public ResultMessage updateCommission(double commission) {
        if (commission < 0 || commission > 1) return ResultMessage.FAILED;
        App app = appDao.findApp();
        app.setCommission(commission);
        return appDao.updateApp(app);
    }
}
