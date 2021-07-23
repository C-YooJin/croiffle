package com.cfww.croiffle.domain.repository;

import com.cfww.croiffle.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
