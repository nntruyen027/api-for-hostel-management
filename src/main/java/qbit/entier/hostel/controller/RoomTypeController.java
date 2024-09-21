package qbit.entier.hostel.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import qbit.entier.hostel.dto.ResponseListDto;
import qbit.entier.hostel.dto.RoomTypeDto;
import qbit.entier.hostel.entity.RoomType;
import qbit.entier.hostel.service.RoomTypeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room-types")
public class RoomTypeController {

    private final RoomTypeService service;

    @GetMapping("/")
    public ResponseListDto<RoomTypeDto> getAll(@RequestParam(defaultValue = "5") int limit, 
                                               @RequestParam(defaultValue = "1") int page, 
                                               @RequestParam(defaultValue = "name") String orderBy, 
                                               @RequestParam(defaultValue = "false") boolean descending) {
        return service.getAll(limit, page, orderBy, descending);
    }

    @GetMapping("/{id}")
    public RoomTypeDto getById(@PathVariable Long id) {
        return service.getOne(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @PostMapping("/")
    public RoomTypeDto createRoomType(@RequestBody RoomType roomType) {
        return service.createOne(roomType);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @PutMapping("/{id}")
    public RoomTypeDto updateRoomType(@PathVariable Long id, @RequestBody RoomType roomType) {
        return service.updateOne(id, roomType);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteRoomType(@PathVariable Long id) {
        service.deleteOne(id);
    }
}
