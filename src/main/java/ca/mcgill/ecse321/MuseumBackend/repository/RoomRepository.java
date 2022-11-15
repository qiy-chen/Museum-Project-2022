package ca.mcgill.ecse321.MuseumBackend.repository;

import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321.MuseumBackend.model.Room;

public interface RoomRepository extends CrudRepository<Room, Integer>{

    Room findRoomByRoomId(int roomId);
    
}