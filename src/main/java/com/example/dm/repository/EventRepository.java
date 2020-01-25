package com.example.dm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.*;

public interface EventRepository extends JpaRepository<Event, Integer> {
	@Query("select e from Event e inner join e.users u where u.username = :k")
	public List<Event> eventiKor(@Param("k")String uname1);
}