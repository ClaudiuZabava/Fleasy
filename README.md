# Platforma de booking pentru zboruri in diferite destinatii
Documentatie

## :clipboard: MySQL database - Baza de date - entitati
![DB schema](https://github.com/ClaudiuZabava/Fleasy/blob/main/DB.png?raw=true)

## :briefcase: REST ENDPOINTS - CRUD - Functionalitati
### CREATE
1. Adaugare destinatie
2. Adaugare destinatie printr-un JSON cu o lista de destinatii
3. Adaugare avion + generare automata locuri din avion in ordine crescatoare incepand cu 1
4. Adaugare avioane printr-un JSON cu o lista de avioane + enerare automata locuri din avion in ordine crescatoare incepand cu 1
5. Adaugare pasager ( user ) + duplicate exception pentru email
6. Adaugarea unei rezervari / booking facut de un passenger, la o destinatie cu o anumita data si ora, pentru un numar de locuri dat
7. Adaugare programare zbor spre o destinatie intr-un anumit avion la o anumita data si ora
### UPDATE
1. Modificarea programului (data si ora de plecare si aterizare la destinatie) a unui zbor
2. Modificarea avionului si a zborului catre o destinatie
### GET
1. Afisarea locurilor libere din avion + not found exception
2. Afisarea informatiilor unui passenger + not found exception
3. Afisarea informatiilor unui zbor (cu tot cu locurile autogenerate) + not found exception
4. Afisarea informatiilor despre o destinatie + not found exception
5. Afisarea informatiilor despre o rezervare / booking + not found exception
6. Afisarea informatiilor despre un avion + not found exception
### DELETE
1. Stergerea unui passenger si a programarilor de zbor aferente acestuia
2. Anularea unei programari / booking
3. Stergerea unei programari / booking
   
## :next_track_button: Testing
Code coverage total obtinut 69.7%

![Tests](https://github.com/bottestBot/Cinema-Management-Backend/blob/main/test.png?raw=true)

Au fost create atat teste de integrare pentru controllers cat si unit teste pentru servicii.

Testele de integrare au fost facute pentru *UserController* si *ScheduleController* acoperind cate o functionalitate CRUD.
```Java
@WebMvcTest(controllers = UserController.class) // this tells Spring Boot to auto-configure a Spring web context
												// for integration tests for the PassengerController class
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private UserService userService;
	@MockBean
	private bookingService resevrationService;
	@MockBean
	private UserMapper userMapper;

	@Test
	public void createUser() throws Exception {
		UserRequest request = new UserRequest("testbot26@gmail.com", "bot", "test-Bot");

		when(userService.createUser(any())).thenReturn(new User(1, "testbot26@gmail.com", "bot", "test-Bot"));

		mockMvc.perform(
				post("/users").contentType("application/json").content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.lastName").value(request.getLastName()))
				.andExpect(jsonPath("$.firstName").value(request.getFirstName()))
				.andExpect(jsonPath("$.email").value(request.getEmail()));
	}
    ...
}
```

Unit testele acopera marea majoritate a functionalitatilor din serviciile folosite.
```Java
@ExtendWith(MockitoExtension.class)
public class airplaneServiceTest {

	@InjectMocks
	private airplaneService airplaneService;

	@Mock
	private airplaneRepository airplaneRepository;
	
	...

	@Test
	void whenairplaneDoesntExists_getairplane_throwsairplaneNotFoundException() {

		// Act
		airplaneNotFoundException exception = assertThrows(airplaneNotFoundException.class, () -> airplaneService.getairplane(1));

		// Assert
		assertEquals("Airplane with id 1 doesn't exist ", exception.getMessage());

	}
    ...
}
```
## Controllers 
Exista 7 controllere create cate una pentru fiecare entitate in parte.
- PassengerController /passengers
- ScheduleController /schedules
- DestinationController /destinations
- AirplaneController /airplanes
- SeatController /seats
- FlightController /flights
- BookingController /bookings
  
## Services
Exista 7 servicii create cate una pentru fiecare entitate in parte.
- PassengerService 
- ScheduleService 
- DestinationService 
- AirplaneService  
- SeatService  
- FlightService  
- BookingService  
  
```Java 
@Service
public class flightService {

	private flightRepository flightRepository;

	public flightService(flightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}

	public flight updateflightairplane(flight oldFlight, airplane newAirplane) {
		oldFlight.setairplane(newAirplane);
		return flightRepository.save(oldFlight);
	}

	public flight createflight(flight flight, airplane airplane, destination destination) {
		flight.setairplane(airplane);
		flight.setdestination(destination);
		return flightRepository.save(flight);
	}

	public flight getflight(Integer id) {
		Optional<flight> flightOptional = flightRepository.findById(id);
		if (flightOptional.isPresent()) {
			return flightOptional.get();
		} else {
			throw new flightNotFoundException(id);
		}
	}

	public void deleteflight(Integer id) {
		Optional<flight> flightOptional = flightRepository.findById(id);
		if (flightOptional.isPresent()) {
			flightRepository.delete(flightOptional.get());
		} else {
			throw new flightNotFoundException(id);
		}
	}

}
```
## Repositories
- Fiecare entitate are asociata cate o interfata care extinde *JpaRepository<Entitate, Tip_ID>* unde au fost definite metodele care nu se aflau in interfata de baza (cele existente deja: findById, findAllById, delete, save etc...) 
```Java
public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value = "select * from passenger where first_name = :name", nativeQuery = true)
	User findUserByFirstNameWithNativeQuery(String name);

	Optional<User> findByEmail(String email);

}
```

## Model mapper 
- Clasele de tip *@Component* cu scopul maparii entitatilor de tip request in entitati folosite ca model in baza de date au fost create pentru fiecare entitate in parte, continand un singur constructor cu parametrii
```Java
@Component
public class UserMapper {

	public User userRequestToUser(UserRequest passengerRequest) {

		return new User(passengerRequest.getEmail(), passengerRequest.getLastName(), passengerRequest.getFirstName());
	}
}
```

## DTOs
- fiecare entitate de tip *NumeEntitateRequest* contine pentru toate campurile validari in functie de tip
- String/Enum
```Java
public class destinationRequest {

	@NotBlank(message = "Name of the destination cannot be null")
	@ApiModelProperty(value = "name", required = true, notes = "The name of the destination", example = "Avatar 2", position = 1)
	private String name;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Type cannot be null")
	@ApiModelProperty(value = "type", required = true, notes = "The type of the destination", example = "D2", position = 2)
	private destinationType type;
    ...
    }
```
- int/Integer
```Java
public class airplaneRequest {

	...

	@NotNull(message = "Capacity cannot be null")
	@Min(1)
	@ApiModelProperty(value = "capacity", required = true, notes = "The capacity of the airplane", example = "40", position = 2)
	private int capacity;

    ...
}
```

## :exclamation: Exception handling
- Pentru passenger: UserDuplicateException si UserNotFoundException
- Pentru restul entatilor doar NotFoundException
  
```Java
  @ControllerAdvice
    public class GlobalExceptionHandler {
        
        @ExceptionHandler({DuplicateUserException.class})
        public ResponseEntity handle(DuplicateUserException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        @ExceptionHandler({UserNotFoundException.class})
        public ResponseEntity<String> handle(UserNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage() + " at " + LocalDateTime.now());
        }
        
        @ExceptionHandler({destinationNotFoundException.class})
        public ResponseEntity<String> handle(destinationNotFoundException e) {
            ...
        }
        
        ...

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<String> handle(MethodArgumentNotValidException e) {
            return ResponseEntity.badRequest()
                    .body("Invalid value : " + e.getFieldError().getRejectedValue() +
                            " for field " + e.getFieldError().getField() +
                            " with message " + e.getFieldError().getDefaultMessage());
        }
    }
```

## :high_brightness: SWAGGER DOCS

![Swagger](https://github.com/ClaudiuZabava/Fleasy/blob/main/swagger.png?raw=true)

```Java
\\SwaggerConfig.java

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.backend.fleasy.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Cinema API Documentation")
                .description("API Documentation for all available operations")
                .build();
    }
}
```
Adaugarea informatiilor pentru ficare API in controllers
```Java
@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Create a passenger", notes = "Creates a new passenger based on the information received in the request")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "The Passenger was successfully created based on the received request"),
			@ApiResponse(code = 400, message = "Validation error on the received request") })
            ...
```
