package com.example.dm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.*;

public interface MessageRepository extends JpaRepository<Message, Integer> {
	@Query("select m from Message m inner join m.user1 u1 inner join m.user2 u2 where (u1.username = :k and u2.username = :s) or (u1.username = :s and u2.username = :k)")
	public List<Message> porukeKorSag(@Param("k")String uname1, @Param("s")String uname2);
	
	@Query("select m from Message m inner join m.user1 u1 inner join m.user2 u2 where u1.username = :k or u2.username = :k")
	public List<Message> porukeKor(@Param("k")String uname1);

}