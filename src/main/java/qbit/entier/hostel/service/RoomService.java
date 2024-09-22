package qbit.entier.hostel.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import qbit.entier.hostel.dto.RequestRoomDto;
import qbit.entier.hostel.dto.ResponseListDto;
import qbit.entier.hostel.dto.ResponseListDto.Meta;
import qbit.entier.hostel.dto.RoomDto;
import qbit.entier.hostel.entity.Room;
import qbit.entier.hostel.entity.RoomImage;
import qbit.entier.hostel.entity.RoomType;
import qbit.entier.hostel.entity.User;
import qbit.entier.hostel.repository.RoomImageRepository;
import qbit.entier.hostel.repository.RoomRepository;
import qbit.entier.hostel.repository.RoomTypeRepository;
import qbit.entier.hostel.repository.UserRepository;
import qbit.entier.hostel.util.FileUtil;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoomImageRepository roomImageRepository;

    @Autowired
    private UserRepository userRepository;


    public ResponseListDto<RoomDto> getAll(int limit, int page, String orderBy, boolean descending) {
        Sort sort = descending ? Sort.by(Sort.Order.desc(orderBy)) : Sort.by(Sort.Order.asc(orderBy));
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Room> roomPage = roomRepository.findAll(pageable);

        List<RoomDto> roomDtos = roomPage.getContent().stream().map(RoomDto::toDto).collect(Collectors.toList());

        ResponseListDto<RoomDto> res = new ResponseListDto<>();
        res.setData(roomDtos);
        res.setMeta(new Meta(roomPage.getTotalPages(), roomPage.getTotalElements(), roomPage.getNumber() + 1, roomPage.getSize()));
        return res;
    }

    public RoomDto getOne(Long id) {
        return RoomDto.toDto(roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found")));
    }

    public ResponseListDto<RoomDto> getByType(Long id, int limit, int page, String orderBy, boolean descending) {
        Sort sort = descending ? Sort.by(Sort.Order.desc(orderBy)) : Sort.by(Sort.Order.asc(orderBy));
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Page<Room> roomPage = roomRepository.findByRoomTypeId(id, pageable);

        List<RoomDto> roomDtos = roomPage.getContent().stream().map(RoomDto::toDto).collect(Collectors.toList());

        ResponseListDto<RoomDto> res = new ResponseListDto<>();
        res.setData(roomDtos);
        res.setMeta(new Meta(roomPage.getTotalPages(), roomPage.getTotalElements(), roomPage.getNumber() + 1, roomPage.getSize()));
        return res;
    }

    public RoomDto createRoom(RequestRoomDto room) {
    	Room newRoom = new Room();
    	newRoom.setMax(room.getMax());
    	newRoom.setName(room.getName());
    	newRoom.setPrice(room.getPrice());
    	newRoom.setImages(new ArrayList<>());
    	newRoom.setCustomer(new ArrayList<>());
    	
    	RoomType type = roomTypeRepository.findById(room.getRoomType()).orElseThrow(() -> new RuntimeException("Type not found"));
    	
    	newRoom.setRoomType(type);

        return RoomDto.toDto(roomRepository.save(newRoom));
    }

    public RoomDto updateRoom(Long id, RequestRoomDto roomUpdates) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
        room.setName(roomUpdates.getName());
        room.setPrice(roomUpdates.getPrice());
        room.setMax(roomUpdates.getMax());
        return RoomDto.toDto(roomRepository.save(room));
    }

    public RoomDto addUserToRoom(Long userId, Long roomId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));

        if (user.getRoom() != null) {
            throw new RuntimeException("User is already in a room.");
        }

        user.setRoom(room);
        userRepository.save(user);
        room.getCustomer().add(user);
        roomRepository.save(room);

        return RoomDto.toDto(room);
    }

    public RoomDto transferUserToRoom(Long userId, Long newRoomId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Room newRoom = roomRepository.findById(newRoomId).orElseThrow(() -> new RuntimeException("Room not found"));

        Room currentRoom = user.getRoom();
        if (currentRoom != null) {
            currentRoom.getCustomer().remove(user);
            roomRepository.save(currentRoom);
        }

        user.setRoom(newRoom);
        userRepository.save(user);
        newRoom.getCustomer().add(user);
        roomRepository.save(newRoom);

        return RoomDto.toDto(newRoom);
    }

    public void deleteImage(Long imageId) throws IOException {
        RoomImage roomImage = roomImageRepository.findById(imageId).orElseThrow(() -> new RuntimeException("Image not found"));
        FileUtil.deleteFile(roomImage.getImage());
        roomImageRepository.delete(roomImage);
    }

    public void deleteRoom(Long id) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
        room.getImages().forEach(image -> {
			try {
				FileUtil.deleteFile(image.getImage());
			} catch (IOException e) {
			}
		});
        roomRepository.delete(room);
    }

    public RoomDto addImages(Long roomId, MultipartFile[] images) throws IOException {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        for(MultipartFile image : images) {
        	RoomImage roomImage = new RoomImage();
        	roomImage.setImage(FileUtil.saveFile(image));
        	roomImage.setRoom(room);
        	roomImageRepository.save(roomImage);
        	room.getImages().add(roomImage);
        }

        return RoomDto.toDto(roomRepository.save(room));
        
    }
  }
