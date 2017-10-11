package com.springBoot.vNoticeBoard.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springBoot.vNoticeBoard.dto.RegistrationForm;
import com.springBoot.vNoticeBoard.entity.Board;
import com.springBoot.vNoticeBoard.repository.BoardRepository;

@Controller
public class BoardController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardRepository boardRepository;
	
	@RequestMapping(value="/{register}", method=RequestMethod.POST)
	public String registerBoard(@Valid RegistrationForm registrationForm, BindingResult result,
			WebRequest request, RedirectAttributes redirectAttributes) {
		
		System.out.println(request.getParameter("boardName"));
		System.out.println(request.getParameter("password"));
		
		if(result.hasErrors()) {
			System.out.println(result.getAllErrors());
			redirectAttributes.addAttribute("error", "Password must be between 4 and 100 characters long.");
			return "redirect:/" + request.getParameter("boardName");
		}
		
		Board board = new Board();
		board.setName(request.getParameter("boardName"));
		board.setPassword(request.getParameter("password"));
		
		boardRepository.save(board);
		return "redirect:/" + request.getParameter("boardName");
	}
	
	@RequestMapping(value="/{boardName}", method=RequestMethod.GET)
	public String showBoard(@PathVariable("boardName") String boardName, Model model) {
		LOGGER.debug("Rendering Board Page Named: " + boardName);
		
		Board board = boardRepository.findByName(boardName);
		
		if(board == null) {
			LOGGER.debug("Board Not Found: " + boardName);
			
			model.addAttribute("boardName", boardName);
			return "newboard";
		}
		
		model.addAttribute("boardName", boardName);
		model.addAttribute("boardContent", board.getContent());
		return "board";
	}
	
	@RequestMapping(value="/{boardName}/edit", method=RequestMethod.GET)
	public String editBoard(@PathVariable("boardName") String boardName, Model model) {
		LOGGER.debug("Rendering Board Edit Page Named: " + boardName);
		
		Board board = boardRepository.findByName(boardName);
		
		if(board == null) {
			LOGGER.debug("Board Not Found: " + boardName);
			return "home";
		}
		model.addAttribute("content", board.getContent());
		System.out.println("From Edit Mode.");
		return "editboard";
	}
	
	@RequestMapping(value="/{boardName}/edit", method=RequestMethod.POST)
	public String doEditBoard(@PathVariable("boardName") String boardName, Model model,
			WebRequest request, RedirectAttributes redirectAttributes) {
		
		Board board = boardRepository.findByName(boardName);
		
		if(board == null) {
			LOGGER.debug("Board Not found: " + boardName);
			return "home";
		}
		
		if(board.getPassword().equals(request.getParameter("password"))) {
			board.setContent(request.getParameter("content"));
			boardRepository.save(board);
			return "redirect:/" + request.getParameter("boardName");
		}
		
		redirectAttributes.addAttribute("error", "Password Missmatch");
		return "redirect:/" + request.getParameter("boardName") + "/edit";
	}
	
	
}
