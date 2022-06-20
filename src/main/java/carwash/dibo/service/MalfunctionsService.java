package carwash.dibo.service;

import carwash.dibo.model.Malfunctions;

import java.util.List;

public interface MalfunctionsService {
    Malfunctions findById(Long id);

    List<Malfunctions> findAllByResolvedIsFalse();
    List<Malfunctions> findAllByOpenByDateDesc();

    void addMalfunction(Malfunctions malfunctions);
    void closeMalfunction(Malfunctions malfunctions);
}
