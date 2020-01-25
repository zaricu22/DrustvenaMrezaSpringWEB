package com.example.dm.controller;


import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dm.repository.CategoryRepository;
import com.example.dm.repository.EventRepository;
import com.example.dm.repository.GroupRepository;
import com.example.dm.repository.MessageRepository;
import com.example.dm.repository.PageRepository;
import com.example.dm.repository.PostRepository;
import com.example.dm.repository.UserRepository;

import model.*;

@Controller
@RequestMapping(value="/Post")
public class PostController {
	@Autowired
	UserRepository ur;
	@Autowired
	GroupRepository gr;
	@Autowired
	PageRepository pr;
	@Autowired
	EventRepository er;
	@Autowired
	CategoryRepository cr;
	@Autowired
	PostRepository psr;
	@Autowired
	MessageRepository mr;
	
	
	@RequestMapping(value="grupa", method=RequestMethod.GET) 
	public String grupa (HttpServletRequest req, Integer idG) {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
		Group g = gr.findById(idG).get();
		List<Post> postovi = psr.postoviGrupe(idG);

		req.getSession().setAttribute("postovi", postovi);
		req.getSession().setAttribute("korisnik", u);
		req.getSession().setAttribute("grupa", g);
		
		return "/Group";
	}
	
	@RequestMapping(value="stranica", method=RequestMethod.GET) 
	public String stranica (HttpServletRequest req, Integer idS) {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
		Page p = pr.findById(idS).get();
		List<Post> postovi = psr.postoviStranice(idS);

		req.getSession().setAttribute("postovi", postovi);
		req.getSession().setAttribute("korisnik", u);
		req.getSession().setAttribute("str", p);
		
		return "/Page";
	}
	
	@RequestMapping(value="dodajP", method=RequestMethod.POST) 
	public String post (HttpServletRequest req,  String text, Integer idS, Integer idG) {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
		Post post = new Post();
		post.setText(text);
		post.setTime(new Date());
		
		u.getPosts().add(post);
		post.setUser(u);
		ur.saveAndFlush(u);
		List<Post> postovi;
		if(idS != null) {
			Page p = pr.findById(idS).get();
			post.setPage(p);
			p.getPosts().add(post);
			req.getSession().setAttribute("str", p);
			pr.saveAndFlush(p);
			psr.saveAndFlush(post); 
			postovi = psr.postoviStranice(idS);
		}
		else {
			Group g = gr.findById(idG).get();
			post.setGroup(g);
			g.getPosts().add(post);
			req.getSession().setAttribute("grupa", g);
			gr.saveAndFlush(g);
			psr.saveAndFlush(post); 
			postovi = psr.postoviGrupe(idG);
		}

		req.getSession().setAttribute("postovi", postovi);
		req.getSession().setAttribute("korisnik", u);
		
		
		if(idS != null) 
			return "/Page";
		return "/Group";
	}

	@RequestMapping(value="razgovor", method=RequestMethod.GET) 
	public String razgovor (HttpServletRequest req, String idSagov) {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		User sag = ur.findById(idSagov).get();
		
		List<Message> poruke = mr.porukeKorSag(u.getUsername(), sag.getUsername());
		poruke.sort(new Comparator<Message>() {
			@Override
			public int compare(Message m1, Message m2) {
				if(m1.getTime().after(m2.getTime()))
					return -1;
				if(m1.getTime().before(m2.getTime()))
					return 1;
				return 0;
			}
		});
		
		req.getSession().setAttribute("poruke", poruke);
		req.getSession().setAttribute("sagovornik", sag);
		
		return "index";
	}
	
	@RequestMapping(value="izbrisiSag", method=RequestMethod.GET) 
	public String izbrisiSag (HttpServletRequest req) {		
		req.getSession().setAttribute("sagovornik", null);
		
		return "index";
	}
	
	@RequestMapping(value="poruka", method=RequestMethod.GET) 
	public String poruka (HttpServletRequest req, String text, String idSag) {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		User sag = ur.findById(idSag).get();
		
		Message mess = new Message();
		mess.setText(text);
		mess.setTime(new Date());
		mess.setUser2(u);
		u.getMessages2().add(mess);
		mess.setUser1(sag);
		sag.getMessages1().add(mess);
		ur.saveAndFlush(u);
		ur.saveAndFlush(sag);
		mr.saveAndFlush(mess);
		
		List<Message> poruke = mr.porukeKorSag(u.getUsername(), sag.getUsername());
		poruke.sort(new Comparator<Message>() {
			@Override
			public int compare(Message m1, Message m2) {
				if(m1.getTime().after(m2.getTime()))
					return -1;
				if(m1.getTime().before(m2.getTime()))
					return 1;
				return 0;
			}
		});
		
		req.getSession().setAttribute("poruke", poruke);
		req.getSession().setAttribute("sagovornik", sag);
		
		return "index";
	}
}