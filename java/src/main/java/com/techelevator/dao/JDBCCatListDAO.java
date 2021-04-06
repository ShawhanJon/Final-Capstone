package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.model.CatList;

@Component
public class JDBCCatListDAO implements CatListDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	 public JDBCCatListDAO(JdbcTemplate jdbcTemplate) {
	        this.jdbcTemplate = jdbcTemplate;
	    }

	@Override
	public List<CatList> retrieveCatList() {
		
		List<CatList> users = new ArrayList<>();
        String sql = "SELECT name, age, occupation, tagline " +
        				"FROM catlist";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            CatList user = mapRowToCatList(results);
            users.add(user);
        }

        return users;
		
		
	}

	
	public List<CatList> retrieveCatDetails(int catId) {
		
		List<CatList> users = new ArrayList<>();
        String sql = "SELECT name, age, breed, color, occupation, address, summary " +
        				"FROM catlist " +
        				"WHERE cat_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, catId);
        while(results.next()) {
            CatList user = mapRowToCatDetails(results);
            users.add(user);
        }

        return users;
	}
	
	
	
	private CatList mapRowToCatList(SqlRowSet rs) {
        CatList user = new CatList();
        user.setName(rs.getString("name"));
        user.setAge(rs.getInt("age"));
        user.setOccupation(rs.getString("occupation"));
        user.setTagline(rs.getString("tagline"));
        
        return user;
    }
	
	private CatList mapRowToCatDetails(SqlRowSet rs) {
        CatList user = new CatList(); 
        user.setName(rs.getString("name"));
        user.setAge(rs.getInt("age"));
        user.setBreed(rs.getString("breed"));
        user.setColor(rs.getString("color"));
        user.setOccupation(rs.getString("occupation"));
        user.setAddress(rs.getString("address"));
        user.setSummary(rs.getString("summary"));
        return user;
    }

}
