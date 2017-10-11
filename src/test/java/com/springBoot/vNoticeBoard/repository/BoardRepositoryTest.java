package com.springBoot.vNoticeBoard.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.springBoot.vNoticeBoard.VNoticeBoardApplication;
import com.springBoot.vNoticeBoard.entity.Board;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = VNoticeBoardApplication.class)
@WebAppConfiguration
public class BoardRepositoryTest {

	@Autowired
	private BoardRepository boardRepository;
	
	@Test
	public void findByNameTest() {
		Board board1 = new Board(1L, "smpilot", "std123", "this is new post content");
		Board board2 = new Board(2L, "smpilot1", "std123", "this is new post content");
		Board board3 = new Board(3L, "smpilot2", "std123", "this is new post content");
		Board board4 = new Board(4L, "smpilot3", "std123", "this is new post content");
		
		//boardRepository.save(board1);
		boardRepository.save(board2);
		boardRepository.save(board3);
		boardRepository.save(board4);
		System.out.println(boardRepository.findByName("smpilot2"));
	}
}
