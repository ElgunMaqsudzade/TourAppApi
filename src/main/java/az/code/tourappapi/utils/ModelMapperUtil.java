package az.code.tourappapi.utils;


import az.code.tourappapi.models.dtos.PaginationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ModelMapperUtil {
    private final ModelMapper mapper;

    public <E, T> List<T> mapList(Collection<? extends E> list, Class<T> type) {
        if (list == null) return null;
        return list.stream().map(i -> mapper.map(i, type)).collect(Collectors.toList());
    }

    public <E, T> T map(E item, Class<T> type) {
        if (item == null) return null;
        return mapper.map(item, type);
    }

    public <E, T> void map(E item, T destination) {
        mapper.map(item, destination);
    }

    public <E, T> Set<T> mapSet(Collection<? extends E> list, Class<T> type) {
        if (list == null) return null;
        return list.stream().map(i -> mapper.map(i, type)).collect(Collectors.toSet());
    }


    public <T, E> PaginationDTO<E> toPagination(Page<T> p, Class<E> clazz) {
        if (p == null) return null;
        return new PaginationDTO<>(p.hasNext(), p.hasPrevious(), p.getTotalPages(), p.getNumber(), p.getTotalElements(), mapList(p.getContent(), clazz));
    }
}
