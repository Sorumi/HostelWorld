package edu.nju.hostelworld.dao.impl;

import edu.nju.hostelworld.dao.LevelDao;
import edu.nju.hostelworld.model.Level;
import edu.nju.hostelworld.util.ResultMessage;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sorumi on 17/3/2.
 */
@Repository
public class LevelDaoImpl extends BaseDaoImpl implements LevelDao {
    @Override
    public ResultMessage addLevel(Level level) {
        return add(level);
    }

    @Override
    public ResultMessage updateLevel(Level level) {
        return update(level);
    }

    @Override
    public ResultMessage deleteLevel(int ID) {
        try {
            Session session = setUpSession();
            Object object = session.get(Level.class, ID);
            session.delete(object);
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return ResultMessage.FAILED;
        }
        return ResultMessage.SUCCESS;
    }

    @Override
    public long countLevels() {
        return count(Level.class);
    }

    @Override
    public Level findLevelByID(int ID) {
        Level level = null;
        try {
            Session session = setUpSession();
            level = session.get(Level.class, ID);
            commitAndClose(session);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return level;
    }

    @Override
    public List<Level> findAllLevels() {
        return findAll(Level.class);
    }
}
