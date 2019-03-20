package database.entityDAO;

import database.entity.BaseEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BaseDAO<T extends BaseEntity> {
    void insert(T entry, Connection conn) throws SQLException;

    List<T> selectAll(Connection conn) throws SQLException;

    T selectById(Integer id, Connection conn) throws SQLException;

    List<T> selectAllOrderByDate(Connection conn) throws SQLException;
}
