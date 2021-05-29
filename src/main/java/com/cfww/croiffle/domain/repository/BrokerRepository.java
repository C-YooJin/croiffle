package com.cfww.croiffle.domain.repository;

import com.cfww.croiffle.domain.entity.Broker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrokerRepository extends JpaRepository<Broker, Long> {
}
