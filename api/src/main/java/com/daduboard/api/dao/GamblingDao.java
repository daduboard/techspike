package com.daduboard.api.dao;

import com.daduboard.api.dao.mappers.GamblingMapper;
import com.daduboard.api.representations.Gambling;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

/**
 * Created by swzhou on 15/6/3.
 */
public interface GamblingDao {

    @Mapper(GamblingMapper.class)
    @SqlQuery("select * from gambling where id = :id")
    Gambling getGamblingById(@Bind("id")int id);

    @GetGeneratedKeys
    @SqlUpdate("insert into gambling (id, title, description) values (NULL, :title, :description)")
    int createGambling(@Bind("title")String title, @Bind("description")String description);
}