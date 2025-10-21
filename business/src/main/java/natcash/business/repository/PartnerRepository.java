package natcash.business.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import natcash.business.entity.Partner;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
	Partner findByUsername(String username);
}
