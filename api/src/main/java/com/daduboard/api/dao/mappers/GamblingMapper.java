package com.daduboard.api.dao.mappers;

import com.daduboard.api.representations.Gambling;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by swzhou on 15/6/3.
 */
public class GamblingMapper implements ResultSetMapper<Gambling> {
    @Override
    public Gambling map(int index, ResultSet r, StatementContext ctx) throws SQLException {
        return new Gambling(r.getInt("id"),r.getString("title"),r.getString("description"));
    }
}
