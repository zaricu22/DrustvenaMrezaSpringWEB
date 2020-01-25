package com.example.dm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.*;

public interface PostRepository extends JpaRepository<Post, Integer> {
	@Query("select ps from Post ps inner join ps.page p where p.idPage = :idP")
	public List<Post> postoviStranice(@Param("idP")Integer idP);
	
	@Query("select ps from Post ps inner join ps.group g where g.idGroup = :idG")
	public List<Post> postoviGrupe(@Param("idG")Integer idG);
}