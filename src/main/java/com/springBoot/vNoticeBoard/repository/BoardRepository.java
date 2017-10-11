package com.springBoot.vNoticeBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springBoot.vNoticeBoard.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

	public Board findByName(String name);
}
