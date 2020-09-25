package aust.edu.repository.extended;

import aust.edu.domain.Applicant;
import aust.edu.domain.ApplicantPersonalInfo;
import aust.edu.repository.ApplicantPersonalInfoRepository;

public interface ApplicantPersonalInfoExtRepository extends ApplicantPersonalInfoRepository {
    ApplicantPersonalInfo findByApplicant(Applicant applicant);
}
