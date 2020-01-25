package com.example.dm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@RequestMapping(value="Crud")
public class CrudController {
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

	
	@RequestMapping(value="brisiG", method=RequestMethod.GET) 
	public String brisiG (HttpServletRequest req, Integer idG) {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
		Group g = gr.findById(idG).get();
		u.getGroups().remove(g);
		g.getUsers().remove(u);
		ur.saveAndFlush(u);
		gr.saveAndFlush(g);
		
		List<Group> grp = ur.grupeKorisnika(u.getUsername());
		List<Group> prd = gr.findAll();
		prd.removeAll(grp);
		
		List<Category> kat = cr.findAll();
		
		req.getSession().setAttribute("kategorije", kat);
		req.getSession().setAttribute("korisnik", u);
		req.getSession().setAttribute("grupe", grp);
		req.getSession().setAttribute("predlogG", prd);
		
		return "/Grupe";
	}
	
	@RequestMapping(value="brisiS", method=RequestMethod.GET) 
	public String brisiS (HttpServletRequest req, Integer idS) {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
		Page p = pr.findById(idS).get();
		u.getPages().remove(p);
		p.getUsers().remove(u);
		ur.saveAndFlush(u);
		pr.saveAndFlush(p);
		
		List<Page> str = ur.straniceKorisnika(u.getUsername());
		List<Page> prd = pr.findAll();
		prd.removeAll(str);
		
		List<Category> kat = cr.findAll();
		
		req.getSession().setAttribute("kategorije", kat);
		req.getSession().setAttribute("korisnik", u);
		req.getSession().setAttribute("stranice", str);
		req.getSession().setAttribute("predlogS", prd);
		
		return "/Stranice";
	}
	
	@RequestMapping(value="brisiP", method=RequestMethod.GET) 
	public String brisiP (HttpServletRequest req, String idP) {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
		User pr = ur.findByUsername(idP);
		u.getUsers1().remove(pr);
		u.getUsers2().remove(u);
		pr.getUsers1().remove(u);
		pr.getUsers2().remove(pr);
		ur.saveAndFlush(u);
		ur.saveAndFlush(pr);
		
		List<User> prd = ur.findAll();
		prd.removeAll(u.getUsers1());
		prd.remove(u);
		
		req.getSession().setAttribute("korisnik", u);
		req.getSession().setAttribute("predlogF", prd);
	
		return "/Prijatelji";
	}
	
	@RequestMapping(value="brisiE", method=RequestMethod.GET) 
	public String brisiE (HttpServletRequest req, Integer idE) {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
		Event e = er.findById(idE).get();
		u.getEvents().remove(e);
		e.getUsers().remove(u);
		ur.saveAndFlush(u);
		er.delete(e);
		
		List<Event> evt = ur.eventiKorisnika(u.getUsername());
		
		List<Category> kat = cr.findAll();
		
		req.getSession().setAttribute("kategorije", kat);
		req.getSession().setAttribute("korisnik", u);
		req.getSession().setAttribute("eventi", evt);

		
		return "/Eventi";
	}
	
	@RequestMapping(value="brisiAdmG", method=RequestMethod.GET) 
	public String brisiAdmG (HttpServletRequest req, Integer idG) {
		Group g = gr.findById(idG).get();
		g.getCategory().getGroups().remove(g);
		for (Post p : psr.postoviGrupe(g.getIdGroup())) {
			psr.delete(p);
		}
		for (User usr : g.getUsers()) {
			usr.getGroups().remove(g);
			ur.saveAndFlush(usr);
		}
		gr.delete(g);
		
		
		List<User> korisnici = ur.findAll();
		List<Page> stranice = pr.findAll();
		List<Group> grupe = gr.findAll();
		
		req.getSession().setAttribute("korisnici", korisnici);
		req.getSession().setAttribute("stranice", stranice);
		req.getSession().setAttribute("grupe", grupe);
		
		return "/KontrolnaTabla";
	}
	
	@RequestMapping(value="brisiAdmS", method=RequestMethod.GET) 
	public String brisiAdmS (HttpServletRequest req, Integer idS) {
		Page p = pr.findById(idS).get();
		p.getCategory().getPages().remove(p);
		for (Post ps : psr.postoviStranice(p.getIdPage())) {
			psr.delete(ps);
		}
		for (User usr : p.getUsers()) {
			usr.getPages().remove(p);
			ur.saveAndFlush(usr);
		}
		pr.delete(p);
		
		List<User> korisnici = ur.findAll();
		List<Page> stranice = pr.findAll();
		List<Group> grupe = gr.findAll();
		
		req.getSession().setAttribute("korisnici", korisnici);
		req.getSession().setAttribute("stranice", stranice);
		req.getSession().setAttribute("grupe", grupe);
		
		return "/KontrolnaTabla";
	}
	
	@RequestMapping(value="brisiAdmK", method=RequestMethod.GET) 
	public String brisiAdmK (HttpServletRequest req, String idK) {
		User u = ur.findByUsername(idK);
		
		for (Message m : mr.porukeKor(u.getUsername())) {
			m.getUser1().getMessages1().remove(m);
			m.getUser2().getMessages2().remove(m);
			ur.saveAndFlush(m.getUser1());
			ur.saveAndFlush(m.getUser2());
			mr.delete(m);
		}
		for (Page p : pr.straniceKor(u.getUsername())) {
			p.getCategory().getPages().remove(p);
			for (Post ps : psr.postoviStranice(p.getIdPage())) {
				psr.delete(ps);
			}
			for (User usr : p.getUsers()) {
				usr.getPages().remove(p);
				ur.saveAndFlush(usr);
			}
			pr.delete(p);
		}
		for (Group g : gr.grupeKor(u.getUsername())) {
			g.getCategory().getGroups().remove(g);
			for (Post p : psr.postoviGrupe(g.getIdGroup())) {
				psr.delete(p);
			}
			for (User usr : g.getUsers()) {
				usr.getGroups().remove(g);
				ur.saveAndFlush(usr);
			}
			gr.delete(g);
		}
		for (Event evt : er.eventiKor(u.getUsername())) {
			evt.getCategory().getEvents().remove(evt);
			for (User usr : evt.getUsers()) {
				usr.getEvents().remove(evt);
				ur.saveAndFlush(usr);
			}
			er.delete(evt);
		}
		for (User usr : ur.prijateljiKorisnika(u.getUsername())) {
			usr.getUsers1().remove(u);
			usr.getUsers2().remove(u);
			ur.saveAndFlush(usr);
		}
		ur.delete(u);
		
		List<User> korisnici = ur.findAll();
		List<Page> stranice = pr.findAll();
		List<Group> grupe = gr.findAll();
		
		req.getSession().setAttribute("korisnici", korisnici);
		req.getSession().setAttribute("stranice", stranice);
		req.getSession().setAttribute("grupe", grupe);
		
		return "/KontrolnaTabla";
	}
	
	@RequestMapping(value="dodajS", method=RequestMethod.POST) 
	public String dodajS (HttpServletRequest req, String name, String date, String opis, String idKat) throws Exception{
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);  
		Page p = new Page();
		p.setName(name);
		p.setDateFounded(date1);
		p.setDescription(opis);
		Category c = cr.findById(Integer.parseInt(idKat)).get();
		p.setCategory(c);
		c.getPages().add(p);
		u.getPages().add(p);
		p.getUsers().add(u);
		pr.saveAndFlush(p);
		ur.saveAndFlush(u);
		cr.saveAndFlush(c);
		
		List<Page> str = ur.straniceKorisnika(u.getUsername());
		List<Page> prd = pr.findAll();
		prd.removeAll(str);
		
		List<Category> kat = cr.findAll();
		
		req.getSession().setAttribute("kategorije", kat);
		req.getSession().setAttribute("korisnik", u);
		req.getSession().setAttribute("stranice", str);
		req.getSession().setAttribute("predlogS", prd);
		
		return "/Stranice";
	}
	
	@RequestMapping(value="dodajG", method=RequestMethod.POST) 
	public String dodajG (HttpServletRequest req, String name, String date, String opis, String idKat) throws Exception {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);  
		Group g = new Group();
		g.setName(name);
		g.setDateFounded(date1);
		g.setDescription(opis);
		Category c = cr.findById(Integer.parseInt(idKat)).get();
		g.setCategory(c);
		c.getGroups().add(g);
		u.getGroups().add(g);
		g.getUsers().add(u);
		gr.saveAndFlush(g);
		ur.saveAndFlush(u);
		cr.saveAndFlush(c);
		
		
		List<Group> grp = ur.grupeKorisnika(u.getUsername());
		List<Group> prd = gr.findAll();
		prd.removeAll(grp);
		
		List<Category> kat = cr.findAll();
		
		req.getSession().setAttribute("kategorije", kat);
		req.getSession().setAttribute("korisnik", u);
		req.getSession().setAttribute("grupe", grp);
		req.getSession().setAttribute("predlogG", prd);

		
		return "/Grupe";
	}
	
	@RequestMapping(value="dodajE", method=RequestMethod.POST) 
	public String dodajE (HttpServletRequest req, String name, String date, String idKat) throws Exception {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);  
		
		Event e = new Event();
		e.setName(name);
		e.setDate(date1);
		Category c = cr.findById(Integer.parseInt(idKat)).get();
		e.setCategory(c);
		c.getEvents().add(e);
		u.getEvents().add(e);
		e.getUsers().add(u);
		er.saveAndFlush(e);
		ur.saveAndFlush(u);
		cr.saveAndFlush(c);
		
		List<Event> evt = ur.eventiKorisnika(u.getUsername());
		List<Category> kat = cr.findAll();
		
		req.getSession().setAttribute("kategorije", kat);
		req.getSession().setAttribute("korisnik", u);
		req.getSession().setAttribute("eventi", evt);

		
		return "/Eventi";
	}
	
	
	@RequestMapping(value="register", method=RequestMethod.POST) 
	public String register (HttpServletRequest req, String uname, String pass, String ime, String prez, String date, String pol) throws Exception {
		User u = new User();

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);  
		
		u.setUsername(uname);
		u.setPassword(pass);
		u.setFirstName(ime);
		u.setLastName(prez);
		u.setBirthday(date1);
		u.setGender(pol);
		ur.saveAndFlush(u);

		
		return "/index";
	}
	
	@RequestMapping(value="kontrolna", method=RequestMethod.GET) 
	public String kontrolna (HttpServletRequest req) throws Exception {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
		List<User> korisnici = ur.findAll();
		List<Page> stranice = pr.findAll();
		List<Group> grupe = gr.findAll();
		korisnici.remove(u);
		
		req.getSession().setAttribute("korisnici", korisnici);
		req.getSession().setAttribute("stranice", stranice);
		req.getSession().setAttribute("grupe", grupe);

		
		return "/KontrolnaTabla";
	}
	
}
