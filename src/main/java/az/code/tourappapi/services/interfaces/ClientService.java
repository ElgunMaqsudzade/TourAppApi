package az.code.tourappapi.services.interfaces;

import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.Client;
import az.code.tourappapi.models.dtos.ClientDTO;

import javax.validation.constraints.NotNull;

public interface ClientService {

    ClientDTO create(@NotNull ClientDTO clientDTO);

    ClientDTO find(@NotNull AppUser user, @NotNull Long offerId);
}
