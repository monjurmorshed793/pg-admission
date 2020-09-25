package aust.edu.resolver;

import aust.edu.domain.ApplicantAddress;
import aust.edu.repository.ApplicantRepository;
import aust.edu.repository.DistrictRepository;
import aust.edu.repository.DivisionRepository;
import aust.edu.repository.ThanaRepository;
import com.coxautodev.graphql.tools.GraphQLResolver;
import graphql.GraphQL;

public class ApplicantAddressResolver implements GraphQLResolver<ApplicantAddress> {
    ApplicantRepository applicantRepository;
    DistrictRepository districtRepository;
    DivisionRepository divisionRepository;
    ThanaRepository thanaRepository;

    public ApplicantAddressResolver(ApplicantRepository applicantRepository, DistrictRepository districtRepository, DivisionRepository divisionRepository, ThanaRepository thanaRepository) {
        this.applicantRepository = applicantRepository;
        this.districtRepository = districtRepository;
        this.divisionRepository = divisionRepository;
        this.thanaRepository = thanaRepository;
    }
}
