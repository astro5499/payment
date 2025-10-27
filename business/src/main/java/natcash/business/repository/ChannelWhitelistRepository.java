package natcash.business.repository;

import natcash.business.entity.ChannelWhitelist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelWhitelistRepository extends JpaRepository<ChannelWhitelist, String> {

    Optional<ChannelWhitelist> findByAccount(String account);
}
