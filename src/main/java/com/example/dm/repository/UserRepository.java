package com.example.dm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import model.*;

public interface UserRepository extends JpaRepository<User, String> {
	User findByUsername(String username);
	
	@Query("select p from Page p inner join p.users u where u.username = :u")
	public List<Page> straniceKorisnika(@Param("u")String uname);
	
	@Query("select g from Group g inner join g.users u where u.username = :u")
	public List<Group> grupeKorisnika(@Param("u")String uname);
	
	@Query("select e from Event e inner join e.users u where u.username = :u")
	public List<Event> eventiKorisnika(@Param("u")String uname);
	
	@Query("select u.users1 from User u where u.username = :u")
	public List<User> prijateljiKorisnika(@Param("u")String uname);
	
	@Query("select u from User u inner join u.users1 uu1 inner join u.users1 uu2 where (uu1.username = :uname1 and uu2.username = :uname2) or (uu1.username = :uname2 and uu2.username = :uname1)")
	public List<User> zajPrij(@Param("uname1")String uname1, @Param("uname2")String uname2);
	
	@Query("select g from Group g inner join g.users gu1 inner join g.users gu2 where (gu1.username = :uname1 and gu2.username = :uname2) or (gu1.username = :uname2 and gu2.username = :uname1)")
	public List<Group> zajGrupe(@Param("uname1")String uname1, @Param("uname2")String uname2);
	
	@Query("select p from Page p inner join p.users pu1 inner join p.users pu2 where (pu1.username = :uname1 and pu2.username = :uname2) or (pu1.username = :uname2 and pu2.username = :uname1)")
	public List<Page> zajStranice(@Param("uname1")String uname1, @Param("uname2")String uname2);
	
	@Query("select p from Post p inner join p.user pu where pu.username = :u")
	public List<Post> postoviKorisnika(@Param("u")String uname);
}
