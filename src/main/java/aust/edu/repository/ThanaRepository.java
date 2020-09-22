package aust.edu.repository;

import aust.edu.domain.Thana;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Thana entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThanaRepository extends JpaRepository<Thana, Long>, JpaSpecificationExecutor<Thana> {
}
