package com.example.dm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.*;

public interface PageRepository extends JpaRepository<Page, Integer> {
	@Query("select p from Page p inner join p.users u where u.username = :k")
	public List<Page> straniceKor(@Param("k")String uname1);
}