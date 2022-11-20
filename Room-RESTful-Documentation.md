# Room Service

The delete and update services have been omitted as a room cannot be deleted or updated after it is created.

## Read a Display room 
URL: /display/{id}

Path Variable: id: The id of the room to be read.

Request Body: None

HTTP Method: GET

Description: Read a display room that is stored in the display and room repository, found using its id. The service returns a DisplayDto object of the Display found.

## Read a Storage room 
URL: /storage/{id}

Path Variable: id: The id of the room to be read.

Request Body: None

HTTP Method: GET

Description: Read a storage room that is stored in the storage and room repository, found using its id. The service returns a StorageDto object of the Storage found.

## Create a Display room 
URL: /display/

Path Variable: None

Request Body: DisplayDto, containing the room Number, the maximum number of artworks allowed in the room and the museum ID.

HTTP Method: POST

Description: Create a display room that is stored in the display and room repository. The service returns a DisplayDto object of the Display created.

## Create a Storage room 
URL: /storage/

Path Variable: None

Request Body: StorageDto, containing the room Number, and the museum ID.

HTTP Method: POST

Description: Create a storage room that is stored in the storage and room repository ensuring that there is only one storage room. The service returns a StorageDto object of the Storage created.

