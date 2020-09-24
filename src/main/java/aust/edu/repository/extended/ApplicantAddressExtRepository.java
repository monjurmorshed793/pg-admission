package aust.edu.repository.extended;

import aust.edu.domain.Applicant;
import aust.edu.domain.ApplicantAddress;
import aust.edu.repository.ApplicantAddressRepository;

import java.util.Set;

public interface ApplicantAddressExtRepository extends ApplicantAddressRepository {
    Set<ApplicantAddress> findAllByApplicant(Applicant applicant);
}
