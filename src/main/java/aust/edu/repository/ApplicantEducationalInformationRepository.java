package aust.edu.repository;

import aust.edu.domain.ApplicantEducationalInformation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ApplicantEducationalInformation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicantEducationalInformationRepository extends JpaRepository<ApplicantEducationalInformation, Long>, JpaSpecificationExecutor<ApplicantEducationalInformation> {
}
