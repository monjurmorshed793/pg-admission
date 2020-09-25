package aust.edu.resolver;

import aust.edu.domain.*;
import aust.edu.repository.*;
import aust.edu.repository.extended.ApplicantAddressExtRepository;
import aust.edu.repository.extended.ApplicantEducationalInformationExtRepository;
import aust.edu.repository.extended.ApplicantPersonalInfoExtRepository;
import aust.edu.repository.extended.JobExperienceExtRepository;
import com.coxautodev.graphql.tools.GraphQLResolver;

import java.util.Set;

public class ApplicantResolver implements GraphQLResolver<Applicant> {
    private ApplicantEducationalInformationExtRepository applicantEducationalInformationExtRepository;
    private JobExperienceExtRepository jobExperienceExtRepository;
    private ApplicantAddressExtRepository applicantAddressExtRepository;
    private ApplicantPersonalInfoExtRepository applicantPersonalInfoExtRepository;

    public ApplicantResolver(ApplicantEducationalInformationExtRepository applicantEducationalInformationExtRepository, JobExperienceExtRepository jobExperienceExtRepository, ApplicantAddressExtRepository applicantAddressExtRepository, ApplicantPersonalInfoExtRepository applicantPersonalInfoExtRepository) {
        this.applicantEducationalInformationExtRepository = applicantEducationalInformationExtRepository;
        this.jobExperienceExtRepository = jobExperienceExtRepository;
        this.applicantAddressExtRepository = applicantAddressExtRepository;
        this.applicantPersonalInfoExtRepository = applicantPersonalInfoExtRepository;
    }

    public Set<ApplicantEducationalInformation> getApplicantEducationalInformations(Applicant applicant){
       return applicantEducationalInformationExtRepository.findAllByApplicant(applicant);
    }

    public Set<JobExperience> getJobExperiences(Applicant applicant){
        return jobExperienceExtRepository.findAllByApplicant(applicant);
    }

    public Set<ApplicantAddress> getApplicantAddress(Applicant applicant){
        return applicantAddressExtRepository.findAllByApplicant(applicant);
    }

    public ApplicantPersonalInfo getApplicantPersonalInformation(Applicant applicant){
        return applicantPersonalInfoExtRepository.findByApplicant(applicant);
    }
}
