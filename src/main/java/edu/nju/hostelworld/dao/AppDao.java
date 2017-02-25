package edu.nju.hostelworld.dao;

import edu.nju.hostelworld.model.App;
import edu.nju.hostelworld.util.ResultMessage;

/**
 * Created by Sorumi on 17/2/11.
 */
public interface AppDao {

    public ResultMessage updateApp(App app);

    public App findApp();

}
