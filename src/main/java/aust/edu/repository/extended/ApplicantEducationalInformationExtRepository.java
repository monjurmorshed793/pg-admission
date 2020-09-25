package aust.edu.repository.extended;

import aust.edu.domain.Applicant;
import aust.edu.domain.ApplicantEducationalInformation;
import aust.edu.repository.ApplicantEducationalInformationRepository;

import java.util.Set;

public interface ApplicantEducationalInformationExtRepository extends ApplicantEducationalInformationRepository {
    Set<ApplicantEducationalInformation> findAllByApplicant(Applicant applicant);
}
