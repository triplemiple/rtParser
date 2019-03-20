package database.entityDAO;

import database.entity.NewsEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NewsDAO implements BaseDAO<NewsEntity> {

    public void insert(NewsEntity entry, Connection conn) throws SQLException {
        String query = "INSERT INTO news(description, headline, html_file_path, pub_time, url)" +
                " VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stm = conn.prepareStatement(query)) {
            stm.setString(1, entry.getDescription());
            stm.setString(2, entry.getHeadline());
            stm.setString(3, entry.getPathToHtmlFile());
            stm.setTimestamp(4, Timestamp.valueOf(entry.getPubDate()));
            stm.setString(5, entry.getUrl());
            stm.execute();
        }
    }

    public List<NewsEntity> selectAll(Connection conn) throws SQLException {
        String query = "SELECT * FROM NEWS";

        try (PreparedStatement stm = conn.prepareStatement(query)) {
            stm.execute();
            ResultSet rs = stm.getResultSet();

            return parseResults(rs);
        }
    }

    public NewsEntity selectById(Integer id, Connection conn) throws SQLException {
        String query = "SELECT * FROM NEWS WHERE ID = ?";

        try (PreparedStatement stm = conn.prepareStatement(query)) {
            stm.setInt(1, id);
            stm.execute();
            ResultSet rs = stm.getResultSet();

            return parseResults(rs).get(0);
        }
    }

    public List<NewsEntity> selectByFields(NewsEntity entry, Connection conn) throws SQLException {
        String query = "SELECT * FROM NEWS WHERE DESCRIPTION = ? " +
                "AND HEADLINE = ?" +
                "AND HTML_FILE_PATH = ?" +
                "AND PUB_TIME = ?" +
                "AND URL = ?";

        List<NewsEntity> result;

        try (PreparedStatement stm = conn.prepareStatement(query)) {
            stm.setString(1, entry.getDescription());
            stm.setString(2, entry.getHeadline());
            stm.setString(3, entry.getPathToHtmlFile());
            stm.setTimestamp(4, Timestamp.valueOf(entry.getPubDate()));
            stm.setString(5, entry.getUrl());
            stm.execute();
            ResultSet rs = stm.getResultSet();
            result = parseResults(rs);

            if (result.isEmpty()){
                return null;
            }

            return result;
        }
    }

    public List<NewsEntity> selectAllOrderByDate(Connection conn) throws SQLException {
        String query = "SELECT * FROM NEWS ORDER BY PUB_TIME DESC";

        List<NewsEntity> result;

        try (PreparedStatement stm = conn.prepareStatement(query)) {
            stm.execute();
            ResultSet rs = stm.getResultSet();
            result = parseResults(rs);

            if (result.isEmpty()){
                return null;
            }

            return result;
        }
    }

    protected List<NewsEntity> parseResults(ResultSet rs) throws SQLException {
        List<NewsEntity> result = new ArrayList<>();

        while (rs.next()) {
            NewsEntity entry = new NewsEntity();
            entry.setDescription(rs.getString("DESCRIPTION"));
            entry.setHeadline(rs.getString("HEADLINE"));
            entry.setPathToHtmlFile(rs.getString("HTML_FILE_PATH"));
            entry.setPublicationDate(rs.getTimestamp("PUB_TIME").toLocalDateTime());
            entry.setURL(rs.getString("URL"));
            entry.setId(rs.getInt("ID"));
            result.add(entry);
        }
        return result;
    }
}
