package az.code.tourappapi.daos;

import az.code.tourappapi.components.specs.interfaces.ClientSpec;
import az.code.tourappapi.daos.interfaces.ClientDAO;
import az.code.tourappapi.exceptions.DataNotFound;
import az.code.tourappapi.models.Client;
import az.code.tourappapi.repos.ClientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientDAOImpl implements ClientDAO {
    private final ClientRepo clientRepo;
    private final ClientSpec clientSpec;

    @Override
    public Client save(Client client) {
        return clientRepo.save(client);
    }

    @Override
    public void delete(Long id) {
        clientRepo.deleteById(id);
    }

    @Override
    public Client find(@NotNull Long id, @NotNull Long userId) {
        Specification<Client> spec = Specification
                .where(clientSpec.byClientId(id))
                .and(clientSpec.byUserId(userId));
        Optional<Client> clientOp = clientRepo.findAll(spec).stream().findAny();
        if (clientOp.isEmpty())
            throw new DataNotFound("Client data not found");
        return clientOp.get();
    }

    @Override
    public Client findByOfferId(@NotNull Long offerId, @NotNull Long userId) {
        Specification<Client> spec = Specification
                .where(clientSpec.byOfferId(offerId))
                .and(clientSpec.byUserId(userId));
        Optional<Client> clientOp = clientRepo.findAll(spec).stream().findAny();
        if (clientOp.isEmpty())
            throw new DataNotFound("Client data not found");
        return clientOp.get();
    }

    @Override
    public boolean exists(Long id) {
        return clientRepo.existsById(id);
    }
}
