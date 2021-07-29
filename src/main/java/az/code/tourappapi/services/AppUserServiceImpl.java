package az.code.tourappapi.services;


import az.code.tourappapi.daos.interfaces.AppUserDAO;
import az.code.tourappapi.exceptions.ConflictException;
import az.code.tourappapi.models.AppUser;
import az.code.tourappapi.models.AppUserOrder;
import az.code.tourappapi.models.AppUser_;
import az.code.tourappapi.models.Order;
import az.code.tourappapi.models.dtos.AppUserDTO;
import az.code.tourappapi.models.enums.OrderStatus;
import az.code.tourappapi.services.interfaces.AppUserService;
import az.code.tourappapi.utils.ModelMapperUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserDAO userDAO;
    private final ModelMapperUtil mapper;

    @Override
    public AppUserDTO update(@NotNull Long id, @NotNull AppUserDTO appUser) {
        AppUser user = mapper.map(appUser, AppUser.class);
        return mapper.map(userDAO.save(user.toBuilder().id(id).build()), AppUserDTO.class);
    }

    @Override
    public AppUserDTO create(@NotNull AppUserDTO appUser) {
        if (!isNew(appUser)) throw new ConflictException();
        AppUser user = mapper.map(appUser, AppUser.class);
        return mapper.map(userDAO.save(user.toBuilder().createDate(LocalDateTime.now()).build()), AppUserDTO.class);
    }

    @Override
    public void addOrder(@NotNull AppUser user, @NotNull Order order,@NotNull OrderStatus status) {
        user.getAppUserOrders().add(AppUserOrder.builder()
                .order(order)
                .appUser(user)
                .status(status)
                .archived(false)
                .build());
        userDAO.save(user);
    }

    @Override
    public void delete(@NotNull Long id) {
        userDAO.delete(id);
    }

    @Override
    public AppUserDTO find(@NotNull Long id) {
        return mapper.map(userDAO.find(id), AppUserDTO.class);
    }

    @Override
    public AppUser find(String email) {
        return userDAO.find(email);
    }

    @Override
    public boolean exists(@NotNull Long id) {
        return userDAO.exists(id);
    }

    @Override
    public boolean isNew(AppUserDTO appUserDTO) {
        Specification<AppUser> spec = ((r, q, cb) ->
                cb.and(cb.equal(r.get(AppUser_.COMPANY), appUserDTO.getCompany()),
                        cb.equal(r.get(AppUser_.EMAIL), appUserDTO.getEmail())));

        return userDAO.findAll(spec).isEmpty();
    }

}
