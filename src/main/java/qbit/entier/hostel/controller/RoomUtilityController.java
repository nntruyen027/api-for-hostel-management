package qbit.entier.hostel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import qbit.entier.hostel.dto.RequestRoomUtilityDto;
import qbit.entier.hostel.dto.ResponseListDto;
import qbit.entier.hostel.dto.RoomUtilityDto;
import qbit.entier.hostel.service.RoomUtilityService;

@RestController
@RequestMapping("/room-utilities")
public class RoomUtilityController {

    @Autowired
    private RoomUtilityService roomUtilityService;

    @GetMapping
    public ResponseListDto<RoomUtilityDto> getAllRoomUtilities(
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "id") String orderBy,
            @RequestParam(defaultValue = "false") boolean descending) {
        return roomUtilityService.getAll(limit, page, orderBy, descending);
    }

    @GetMapping("/room/{roomId}")
    public ResponseListDto<RoomUtilityDto> getRoomUtilitiesByRoomId(
            @PathVariable Long roomId,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "id") String orderBy,
            @RequestParam(defaultValue = "false") boolean descending) {
        return roomUtilityService.findByRoom(roomId, limit, page, orderBy, descending);
    }

    @GetMapping("/service/{categoryId}")
    public ResponseListDto<RoomUtilityDto> getRoomUtilitiesByExpenseCategoryId(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "id") String orderBy,
            @RequestParam(defaultValue = "false") boolean descending) {
        return roomUtilityService.findByCategory(categoryId, limit, page, orderBy, descending);
    }

    @GetMapping("/{id}")
    public RoomUtilityDto getRoomUtilityById(@PathVariable Long id) {
        return roomUtilityService.findById(id);
    }

    @PostMapping
    public RoomUtilityDto createRoomUtility(@RequestBody RequestRoomUtilityDto newObject) {
        return roomUtilityService.createOne(newObject);
    }

    @PutMapping("/{id}")
    public RoomUtilityDto updateRoomUtility(@PathVariable Long id, @RequestBody RequestRoomUtilityDto updatedObject) {
        return roomUtilityService.updateOne(id, updatedObject);
    }

    @DeleteMapping("/{id}")
    public void deleteRoomUtility(@PathVariable Long id) {
        roomUtilityService.deleteOne(id);
    }
}
