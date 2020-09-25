package aust.edu.repository.extended;

import aust.edu.domain.Applicant;
import aust.edu.domain.JobExperience;
import aust.edu.repository.JobExperienceRepository;

import java.util.Set;

public interface JobExperienceExtRepository extends JobExperienceRepository {
    Set<JobExperience> findAllByApplicant(Applicant applicant);
}
