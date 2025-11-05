package natcash.business.repository;

import natcash.business.entity.DummyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface CodeSeqRepository extends CrudRepository<DummyEntity, Long> {
    @Query(value = "SELECT nextval('code_seq')", nativeQuery = true)
    Long getNextSequenceValue();
}


