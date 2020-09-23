package aust.edu.repository;

import aust.edu.domain.JobExperience;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the JobExperience entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobExperienceRepository extends JpaRepository<JobExperience, Long> {
}
