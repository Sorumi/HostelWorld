package edu.nju.hostelworld.service;

import edu.nju.hostelworld.model.App;
import edu.nju.hostelworld.util.ResultMessage;

/**
 * Created by Sorumi on 17/2/11.
 */
public interface AppService {

    public App findApp();

    public ResultMessage updateMoney(double money);

    public ResultMessage updateCommission(double commission);
}
