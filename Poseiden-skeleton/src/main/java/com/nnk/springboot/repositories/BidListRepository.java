package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for the BidList entity, providing CRUD operations.
 * <p>
 * This interface extends JpaRepository, which provides methods for interacting with the BidList entity.
 * <p>
 */
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
