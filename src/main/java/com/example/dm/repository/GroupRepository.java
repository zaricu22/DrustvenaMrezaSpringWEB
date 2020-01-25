package com.example.dm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.*;

public interface GroupRepository extends JpaRepository<Group, Integer> {
	@Query("select g from Group g inner join g.users u where u.username = :k")
	public List<Group> grupeKor(@Param("k")String uname1);
}