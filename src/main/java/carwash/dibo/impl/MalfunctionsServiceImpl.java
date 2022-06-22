package carwash.dibo.impl;

import carwash.dibo.model.Malfunctions;
import carwash.dibo.repository.MalfunctionsRepository;
import carwash.dibo.service.MalfunctionsService;
import carwash.dibo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class MalfunctionsServiceImpl implements MalfunctionsService {
    private final MalfunctionsRepository malfunctionsRepository;
    private final UserService userService;

    @Override
    public List<Malfunctions> findAllByResolvedIsFalse() {
        List<Malfunctions> malfunctions = malfunctionsRepository.findAllByResolvedIsFalse();
        if (malfunctions == null) {
            return new ArrayList<>();
        }

        return malfunctions;
    }

    @Override
    public Malfunctions findById(Long id) {
        return malfunctionsRepository.findById(id).orElseThrow(() -> new NullPointerException("Id не найден"));
    }

    @Override
    public List<Malfunctions> findAllByOpenByDateDesc() {
        List<Malfunctions> malfunctions = malfunctionsRepository.getAllByOpenByDateDesc();
        if (malfunctions == null) {
            return new ArrayList<>();
        }

        return malfunctions;
    }

    @CacheEvict(value = "malfList", allEntries = true)
    @Override
    public void addMalfunction(Malfunctions malfunctions) {
        malfunctions.setOpenDate(new Date());
        malfunctions.setUser(userService.getCurrentUser());
        malfunctions.setResolved(false);
        malfunctionsRepository.save(malfunctions);
        log.info("The new malfunction added: " + malfunctions.getCategory().getName() +
                " by" + malfunctions.getUser().getUsername());
    }

    @CacheEvict(value = "malfList", allEntries = true)
    @Override
    public void closeMalfunction(Malfunctions malfunctions) {
        malfunctions.setResolved(true);
        malfunctions.setClosedDate(new Date());
        malfunctionsRepository.save(malfunctions);
        log.info("The malfunction is resolved from: " + malfunctions.getCategory().getName());
    }
}
