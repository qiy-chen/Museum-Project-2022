# Artwork Service
## Read a Artwork
URL: /artwork/{id}

Path Variable: id: The id of the artwork to be read.

Request Body: None

HTTP Method: GET

Description: Read a artwork that is stored in the artwork repository, found using its id. The service returns a ArtworkResponseDto object of the Artwork found.

## Read Artworks in storage 
URL: /storage/artworks

Path Variable: None

Request Body: None

HTTP Method: GET

Description: Read artworks inside the storage room, the artworks are stored in the artwork repository. The service returns a list of ArtworkResponseDto objects of the Artworks found.

## Read Artworks in display
URL: /display/artworks

Path Variable: None

Request Body: None

HTTP Method: GET

Description: Read artworks inside the display rooms, the artworks are stored in the artwork repository. The service returns a list of ArtworkResponseDto objects of the Artworks found.

## Read Artworks in specific room
URL: /room/artworks/{roomId}

Path Variable: roomId. The id of the room to get artworks from.

Request Body: None

HTTP Method: GET

Description: Read artworks inside the a specific room, the artworks are stored in the artwork repository. The service returns a list of ArtworkResponseDto objects of the Artworks found.

## Read all Artworks in the museum
URL: /artwork

Path Variable: None

Request Body: None

HTTP Method: GET

Description: Read all artworks inside the museum, the artworks are stored in the artwork repository. The service returns a list of ArtworkResponseDto objects of the Artworks found.

## Move a specific Artwork from a room to another 
URL: /artwork/room/{id}

Path Variable: id. The id of the artwork to move

Request Body: ArtworkRequestDto, containing the room id to move the artwork to 

HTTP Method: PUT

Description: Move a specific artwork from a room to another, using the id of the artwork as well as the id of the room.

## Delete Artwork 
URL: /artwork/room/{id}

Path Variable: id. The id of the artwork to delete

Request Body: None

HTTP Method: DELETE

Description: Delete a specific artwork, using the id of the artwork.

## Update Artwork 
URL: /artwork/{id}/

Path Variable: id. The id of the artwork to update

Request Body: ArtworkRequestDto, containing the name, the value and the property of loanable/ notLoanable of the artwork

HTTP Method: PUT

Description: Update a specific artwork, using the id of the artwork and the fields provided by the request body.

## Create Artwork 
URL: /artwork

Path Variable: None

Request Body: ArtworkRequestDto, containing the name, the room ID  to put the artwork in and the museum ID.

HTTP Method: POST

Description: Create a new artwork, using the artwork ID, giving a name to the artwork, putting the artwork in a room (using the room ID) and in a museum (using the museum ID). The value and the property of loanable/ notLoanable of the artwork are automatically set and are changed using the Update artwork.
