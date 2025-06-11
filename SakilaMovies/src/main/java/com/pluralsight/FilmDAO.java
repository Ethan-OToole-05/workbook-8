package com.pluralsight;

import com.pluralsight.models.Film;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {
    private BasicDataSource dataSource;

    public FilmDAO(DataManager dataManager) {
        this.dataSource = dataManager.getDataSource();
    }


}
