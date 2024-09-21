package qbit.entier.hostel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import qbit.entier.hostel.dto.ResponseListDto;
import qbit.entier.hostel.dto.ResponseListDto.Meta;
import qbit.entier.hostel.dto.RoomDto;
import qbit.entier.hostel.entity.Room;
import qbit.entier.hostel.entity.RoomImage;
import qbit.entier.hostel.repository.RoomRepository;
import qbit.entier.hostel.repository.RoomImageRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomImageRepository roomImageRepository;

    private final String uploadDir = "uploads/";

    public ResponseListDto<RoomDto> getAll(int limit, int page, String orderBy, boolean descending) {
		Sort sort;
	    if (descending) {
	        sort = Sort.by(Sort.Order.desc(orderBy));
	    } else {
	        sort = Sort.by(Sort.Order.asc(orderBy));
	    }

        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Room> roomPage = roomRepository.findAll(pageable);

        List<RoomDto> roomDtos = roomPage.getContent().stream().map(RoomDto::toDto).collect(Collectors.toList());

        ResponseListDto<RoomDto> res = new ResponseListDto<>();
        res.setData(roomDtos);
        res.setMeta(new Meta(roomPage.getTotalPages(), roomPage.getTotalElements(), roomPage.getNumber() + 1, roomPage.getSize()));
        return res;
    }

    public RoomDto getOne(Long id) {
        return RoomDto.toDto(roomRepository.findById(id).orElse(null));
    }

    public ResponseListDto<RoomDto> getByType(Long id, int limit, int page, String orderBy, boolean descending) {
		Sort sort;
	    if (descending) {
	        sort = Sort.by(Sort.Order.desc(orderBy));
	    } else {
	        sort = Sort.by(Sort.Order.asc(orderBy));
	    }

        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Room> roomPage = roomRepository.findByRoomTypeId(id, pageable);

        List<RoomDto> roomDtos = roomPage.getContent().stream().map(RoomDto::toDto).collect(Collectors.toList());

        ResponseListDto<RoomDto> res = new ResponseListDto<>();
        res.setData(roomDtos);
        res.setMeta(new Meta(roomPage.getTotalPages(), roomPage.getTotalElements(), roomPage.getNumber() + 1, roomPage.getSize()));
        return res;
    }

    public RoomDto createRoom(Room room, MultipartFile[] images) {
        createUploadDirIfNotExists();
        Room savedRoom = roomRepository.save(room);
        saveImages(savedRoom, images);
        return RoomDto.toDto(savedRoom);
    }

    public RoomDto updateRoom(Long id, Room roomUpdates) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        room.setName(roomUpdates.getName());
        room.setPrice(roomUpdates.getPrice());
        room.setMax(roomUpdates.getMax());
        return RoomDto.toDto(roomRepository.save(room));
    }
    
    public void deleteImage(Long imageId) {
        RoomImage roomImage = roomImageRepository.findById(imageId).orElseThrow(() -> new RuntimeException("Image not found"));
        File fileToDelete = new File(roomImage.getImage());
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
        roomImageRepository.delete(roomImage); 
    }

    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
        room.getImages().forEach(image -> deleteImage(image.getId()));;
        roomRepository.delete(room);
    }

    public void addImages(Long roomId, MultipartFile[] images) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        saveImages(room, images);
    }


    private void createUploadDirIfNotExists() {
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdir();
        }
    }

    private void saveImages(Room room, MultipartFile[] images) {
        if (images != null) {
            for (MultipartFile image : images) {
                String fileName = image.getOriginalFilename();
                File dest = new File(uploadDir + fileName);
                try {
                    // Lưu file vào thư mục
                    image.transferTo(dest);
                    // Lưu đường dẫn file vào RoomImage
                    RoomImage roomImage = new RoomImage();
                    roomImage.setRoom(room);
                    roomImage.setImage(uploadDir + fileName); // Lưu đường dẫn
                    // Lưu vào database
                    roomImageRepository.save(roomImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
