package aust.edu.repository;

import aust.edu.domain.ApplicantAddress;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ApplicantAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicantAddressRepository extends JpaRepository<ApplicantAddress, Long>, JpaSpecificationExecutor<ApplicantAddress> {
}
