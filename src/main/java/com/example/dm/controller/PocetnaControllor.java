package com.example.dm.controller;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.example.dm.repository.RoleRepository;
import com.example.dm.repository.UserRepository;

import model.*;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping(value="/Pocetna")
public class PocetnaControllor {
	@Autowired
	UserRepository ur;
	@Autowired
	GroupRepository gr;
	@Autowired
	PageRepository pr;
	@Autowired
	CategoryRepository cr;
	@Autowired
	EventRepository er;
	@Autowired
	PostRepository psr;
	@Autowired
	MessageRepository mr;
	@Autowired
	RoleRepository rr;
	
	@RequestMapping(value="stranice", method=RequestMethod.GET) 
	public String stranice (HttpServletRequest req) {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
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
	
	@RequestMapping(value="grupe", method=RequestMethod.GET) 
	public String grupe (HttpServletRequest req) {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
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
	
	@RequestMapping(value="eventi", method=RequestMethod.GET) 
	public String eventi (HttpServletRequest req) {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
		List<Event> evt = ur.eventiKorisnika(u.getUsername());
		
		List<Category> kat = cr.findAll();
		
		req.getSession().setAttribute("kategorije", kat);
		req.getSession().setAttribute("korisnik", u);
		req.getSession().setAttribute("eventi", evt);

		
		return "/Eventi";
	}
	
	@RequestMapping(value="prijatelji", method=RequestMethod.GET) 
	public String prijatelji (HttpServletRequest req) {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
		List<User> prd = ur.findAll();
		prd.removeAll(u.getUsers1());
		prd.remove(u);
		
		req.getSession().setAttribute("korisnik", u);
		req.getSession().setAttribute("predlogF", prd);
		
		return "/Prijatelji";
	}
	
	@RequestMapping(value="prtkor", method=RequestMethod.GET) 
	public String prtkor (HttpServletRequest req, String idP) {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
		User pr = ur.findByUsername(idP);
		u.getUsers1().add(pr);
		u.getUsers2().add(u);
		pr.getUsers1().add(u);
		pr.getUsers2().add(pr);
		ur.saveAndFlush(u);
		ur.saveAndFlush(pr);
		
		List<User> prd = ur.findAll();
		prd.removeAll(u.getUsers1());
		prd.remove(u);
		
		req.getSession().setAttribute("korisnik", u);
		req.getSession().setAttribute("predlogF", prd);
	
		return "/Prijatelji";
	}
	
	@RequestMapping(value="strkor", method=RequestMethod.GET) 
	public String strkor (HttpServletRequest req, Integer idS) {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
		Page p = pr.findById(idS).get();
		u.getPages().add(p);
		p.getUsers().add(u);
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
	
	@RequestMapping(value="grpkor", method=RequestMethod.GET) 
	public String grpkor (HttpServletRequest req, Integer idG) {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
		Group g = gr.findById(idG).get();
		u.getGroups().add(g);
		g.getUsers().add(u);
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
	
	@RequestMapping(value="izvestajAdmin", method=RequestMethod.GET)
	public void izvestajAdmin (HttpServletResponse res) throws Exception {
		List<User> korisnici = ur.findAll();
		
		List<AdminReport> data = new ArrayList<AdminReport>();
		for (User u : korisnici) {
			AdminReport ar = new AdminReport();
			ar.setImeprez(u.getFirstName()+" "+u.getLastName());
			ar.setBrPrijatelja(u.getUsers1().size());
			ar.setBrPoruka(u.getMessages2().size()+u.getMessages1().size());
			ar.setBrPostova(u.getPosts().size());
			data.add(ar);
		}
		
		res.setContentType("text/html");
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
		InputStream input = this.getClass().getResourceAsStream("/jasperreports/Admin.jrxml");
		JasperReport report = JasperCompileManager.compileReport(input);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("brKorisnika", ur.findAll().size());
		params.put("brStranica", pr.findAll().size());
		params.put("brGrupa", gr.findAll().size());
		params.put("brEventa", er.findAll().size());
		params.put("brPostova", psr.findAll().size());
		params.put("brPoruka", mr.findAll().size());
		
		JasperPrint print = JasperFillManager.fillReport(report, params, dataSource);
		input.close();
		
		
		res.setContentType("application/x-download");
		res.addHeader("Content-disposition", "attachment; filename=Statistika mreze.pdf");
		OutputStream out = res.getOutputStream();
		JasperExportManager.exportReportToPdfStream(print,out);
		
	}
	
	@RequestMapping(value="izvestajKorisnik", method=RequestMethod.GET)
	public void izvestajKorisnik (HttpServletResponse res, HttpServletRequest req) throws Exception {
		String currentUserName = "";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = ur.findByUsername(currentUserName);
		
		List<KorisnikReport> data = new ArrayList<KorisnikReport>();
		
		List<User> prijatelji = u.getUsers1();
		for (User usr : prijatelji) {
			
			KorisnikReport kr = new KorisnikReport();
			kr.setImeprez(usr.getFirstName()+" "+usr.getLastName());
			int brPrim = 0;
			int brPosl = 0;
			for (Message m : usr.getMessages2()) {
				if(m.getUser1().equals(u))
					brPrim++;
			}
			for (Message m : usr.getMessages1()) {
				if(m.getUser2().equals(u))
					brPosl++;
			}
			kr.setPoslPor(brPosl);
			kr.setPrimPor(brPrim);
			
			List<Group> zajGrup = ur.zajGrupe(u.getUsername(), usr.getUsername()); 
			kr.setZajGrup(zajGrup.size()/2);
			
			List<Page> zajStr = ur.zajStranice(u.getUsername(), usr.getUsername());
			kr.setZajStr(zajStr.size()/2);
			
			List<User> zajPrij = ur.zajPrij(u.getUsername(), usr.getUsername());
			kr.setZajPrij(zajPrij.size()/2);
			
			data.add(kr);
			
		}
		res.setContentType("text/html");
		
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
		InputStream input = this.getClass().getResourceAsStream("/jasperreports/Korisnik.jrxml");
		JasperReport report = JasperCompileManager.compileReport(input);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("brStranica", u.getPages().size());
		params.put("brGrupa", u.getGroups().size());
		params.put("brPoruka", u.getMessages1().size() + u.getMessages2().size());
		params.put("ime", u.getFirstName());
		params.put("prez", u.getLastName());
		LinkedList<Post> posts = new LinkedList<Post>(ur.postoviKorisnika(u.getUsername()));
		if(posts.size() > 0) {
			params.put("text", posts.getLast().getText());
			params.put("date", posts.getLast().getTime());
		}
		else {
			params.put("text", "Nema postova!");
			params.put("date", null);
		}
		
		JasperPrint print = JasperFillManager.fillReport(report, params, dataSource);
		input.close();
		
		
		res.setContentType("application/x-download");
		res.addHeader("Content-disposition", "attachment; filename=Statistika korisnika.pdf");
		OutputStream out = res.getOutputStream();
		JasperExportManager.exportReportToPdfStream(print,out);
	}
}
