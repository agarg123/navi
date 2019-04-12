package com.mayavi.repository;

import com.mayavi.models.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Abhishek Garg on 2019-04-12
 */

public interface PartnerRepository extends JpaRepository<Partner, Long> {

    Partner findByPartnerId(int partnerId);
}
