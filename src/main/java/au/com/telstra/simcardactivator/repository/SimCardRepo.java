package au.com.telstra.simcardactivator.repository;

import au.com.telstra.simcardactivator.entity.SimCard;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SimCardRepo extends CrudRepository<SimCard, Long> {
    Optional<SimCard> findById(Long id);
    Optional<SimCard> findSimCardByIccid(String iccid);
}
