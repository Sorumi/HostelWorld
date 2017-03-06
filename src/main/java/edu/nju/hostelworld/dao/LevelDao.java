package edu.nju.hostelworld.dao;

import edu.nju.hostelworld.model.Level;
import edu.nju.hostelworld.util.ResultMessage;

import java.util.List;

/**
 * Created by Sorumi on 17/3/2.
 */
public interface LevelDao {

    public ResultMessage addLevel(Level level);

    public ResultMessage updateLevel(Level level);

    public ResultMessage deleteLevel(int ID);

    public long countLevels();

    public Level findLevelByID(int ID);

    public Level findLevelByPoints(int points);

    public List<Level> findAllLevels();

}
