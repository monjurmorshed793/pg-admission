package aust.edu.repository;

import aust.edu.domain.ApplicationDeadline;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ApplicationDeadline entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationDeadlineRepository extends JpaRepository<ApplicationDeadline, Long>, JpaSpecificationExecutor<ApplicationDeadline> {
}
