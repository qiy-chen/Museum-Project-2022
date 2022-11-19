package ca.mcgill.ecse321.MuseumBackend.repository;

import ca.mcgill.ecse321.MuseumBackend.model.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Integer>{

    Room findRoomByRoomId(int roomId);
    
}