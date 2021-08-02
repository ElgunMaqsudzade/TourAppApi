package az.code.tourappapi.daos.interfaces;

import az.code.tourappapi.models.Client;

import javax.validation.constraints.NotNull;

public interface ClientDAO {
    Client save(@NotNull Client client);

    void delete(@NotNull Long id);

    Client find(@NotNull Long id, @NotNull Long userId);

    Client findByOfferId(@NotNull Long offerId, @NotNull Long userId);

    boolean exists(@NotNull Long id);
}
