package aust.edu.repository;

import aust.edu.domain.ApplicantPersonalInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ApplicantPersonalInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicantPersonalInfoRepository extends JpaRepository<ApplicantPersonalInfo, Long> {
}
