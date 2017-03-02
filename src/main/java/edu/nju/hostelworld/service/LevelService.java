package edu.nju.hostelworld.service;

import edu.nju.hostelworld.model.Level;
import edu.nju.hostelworld.util.ResultMessage;

import java.util.List;

/**
 * Created by Sorumi on 17/3/2.
 */
public interface LevelService {

    public ResultMessage addLevel(Level level);

    public ResultMessage updateLevel(Level level);

    public ResultMessage deleteLevel(int ID);

    public Level findLevelByID(int ID);

    public List<Level> findAllLevels();
}
