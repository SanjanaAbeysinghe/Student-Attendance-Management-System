package Dao;

import java.util.ArrayList;

public interface CrudDao<T, ID> extends Dao.SuperDao {
    boolean save(T t) throws Exception;
    boolean update(T t) throws Exception;
    boolean delete(ID id) throws Exception;
    T search(ID id) throws Exception;
    ArrayList<T> getALL() throws Exception;
}