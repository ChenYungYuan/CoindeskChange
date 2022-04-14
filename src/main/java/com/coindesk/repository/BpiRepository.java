package com.coindesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coindesk.entities.Bpi;

@Repository
public interface BpiRepository  extends JpaRepository<Bpi, String> {
	
	
}
