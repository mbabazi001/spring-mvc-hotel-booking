package edu.apartmentbooking.service;

import edu.apartmentbooking.model.Hotel;
import edu.apartmentbooking.model.Room;
import edu.apartmentbooking.model.dto.RoomDTO;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    Room saveRoom(RoomDTO roomDTO, Hotel hotel);

    List<Room> saveRooms(List<RoomDTO> roomDTOs, Hotel hotel);

    Optional<Room> findRoomById(Long id);

    List<Room> findRoomsByHotelId(Long hotelId);

    Room updateRoom(RoomDTO roomDTO);

    void deleteRoom(Long id);

    Room mapRoomDtoToRoom(RoomDTO roomDTO, Hotel hotel);

    RoomDTO mapRoomToRoomDto(Room room);

}
