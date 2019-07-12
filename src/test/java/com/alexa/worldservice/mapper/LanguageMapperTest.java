package com.alexa.worldservice.mapper;

import com.shelberg.entity.Language;
import org.junit.Assert;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.Boolean.TRUE;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LanguageMapperTest {

    @Test
    public void mapRow() throws SQLException {
        LanguageMapper languageMapper = new LanguageMapper();

        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getString("language")).thenReturn("French");
        when(mockResultSet.getDouble("percentage")).thenReturn(0.03);
        when(mockResultSet.getBoolean("isOfficial")).thenReturn(false);

        Language language = languageMapper.mapRow(mockResultSet);

        Assert.assertNotNull(language);
        Assert.assertEquals("French",language.getName());
        Assert.assertEquals(0.03,language.getPercentage(),0.00);
        Assert.assertEquals(false, language.isOfficial());


    }
}