package qbit.entier.hostel.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import qbit.entier.hostel.dto.RequestRoomDto;
import qbit.entier.hostel.dto.ResponseListDto;
import qbit.entier.hostel.dto.RoomDto;
import qbit.entier.hostel.service.RoomService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/")
    public ResponseEntity<ResponseListDto<RoomDto>> getAllRooms(
            @RequestParam(defaultValue = "10", required = false) int limit,
            @RequestParam(defaultValue = "1", required = false) int page,
            @RequestParam(defaultValue = "name", required = false) String orderBy,
            @RequestParam(defaultValue = "false", required = false) boolean descending) {
        ResponseListDto<RoomDto> rooms = roomService.getAll(limit, page, orderBy, descending);
        return ResponseEntity.ok(rooms);
    }
    
 
    @GetMapping("/{id}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long id) {
        RoomDto room = roomService.getOne(id);
        return ResponseEntity.ok(room);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<RoomDto> createRoom(@RequestBody RequestRoomDto room) {
        RoomDto createdRoom = roomService.createRoom(room);
        return ResponseEntity.ok(createdRoom);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<RoomDto> updateRoom(@PathVariable Long id, @RequestBody RequestRoomDto room) {
        RoomDto updatedRoom = roomService.updateRoom(id, room);
        return ResponseEntity.ok(updatedRoom);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{roomId}/users/{userId}")
    public ResponseEntity<RoomDto> addUserToRoom(@PathVariable Long userId, @PathVariable Long roomId) {
        RoomDto updatedRoom = roomService.addUserToRoom(userId, roomId);
        return ResponseEntity.ok(updatedRoom);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{newRoomId}/users/{userId}")
    public ResponseEntity<RoomDto> transferUserToRoom(@PathVariable Long userId, @PathVariable Long newRoomId) {
        RoomDto updatedRoom = roomService.transferUserToRoom(userId, newRoomId);
        return ResponseEntity.ok(updatedRoom);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{roomId}/images")
    public ResponseEntity<RoomDto> addImages(@PathVariable Long roomId, @RequestParam("images") MultipartFile[] images) throws IOException {
        RoomDto updatedRoom = roomService.addImages(roomId, images);
        return ResponseEntity.ok(updatedRoom);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) throws IOException {
        roomService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }
}
