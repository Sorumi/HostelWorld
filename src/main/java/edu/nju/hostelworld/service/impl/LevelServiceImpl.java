package edu.nju.hostelworld.service.impl;

import edu.nju.hostelworld.dao.LevelDao;
import edu.nju.hostelworld.model.Level;
import edu.nju.hostelworld.service.LevelService;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sorumi on 17/3/2.
 */
@Service
public class LevelServiceImpl implements LevelService {

    @Autowired
    private LevelDao levelDao;

    @Override
    public ResultMessage addLevel(Level level) {
        int count = Math.toIntExact(levelDao.countLevels());
        level.setID(count + 1);
        return levelDao.addLevel(level);
    }

    @Override
    public ResultMessage updateLevel(Level level) {
        return levelDao.updateLevel(level);
    }

    @Override
    public ResultMessage deleteLevel(int ID) {
        return levelDao.deleteLevel(ID);
    }

    @Override
    public Level findLevelByID(int ID) {
        return levelDao.findLevelByID(ID);
    }

    @Override
    public List<Level> findAllLevels() {
        return levelDao.findAllLevels();
    }
}
