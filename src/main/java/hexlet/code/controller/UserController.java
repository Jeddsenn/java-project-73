package hexlet.code.controller;


import hexlet.code.dto.request.UserReq;
import hexlet.code.dto.response.UserRes;
import hexlet.code.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import static org.springframework.http.HttpStatus.CREATED;


@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    public static final String USER_CONTROLLER_PATH = "/users";
    private final UserService userService;

    private static final String ONLY_OWNER_BY_ID = """
            @userRepository.findById(#id).get().getEmail() == authentication.getName()
        """;


    @Operation(summary = "Get a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was found"),
            @ApiResponse(responseCode = "404", description = "User with this id wasn`t found")
    })
    @GetMapping("/{id}")
    public UserRes getUser(@PathVariable long id) throws NoSuchElementException {
        return userService.getUser(id);
    }

    @Operation(summary = "Get all users")
    @ApiResponses(@ApiResponse(responseCode = "200"))
    @GetMapping
    public List<UserRes> getAll() throws Exception {
        return userService.getAll();
    }

    @Operation(summary = "Create new user")
    @ApiResponse(responseCode = "201", description = "User created")
    @ResponseStatus(CREATED)
    @PostMapping
    public UserRes createUser(@RequestBody @Valid UserReq userDto) {
        return userService.createNewUser(userDto);
    }

    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User has been updated"),
            @ApiResponse(responseCode = "404", description = "User with this id wasn`t found")
    })
    @PreAuthorize(ONLY_OWNER_BY_ID)
    @PutMapping("/{id}")
    public UserRes updateUser(@PathVariable @Valid long id, @RequestBody @Valid UserReq userDto) {
        return userService.updateUser(id, userDto);
    }

    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User has been deleted"),
            @ApiResponse(responseCode = "404", description = "User with this id wasn`t found")
    })
    @PreAuthorize(ONLY_OWNER_BY_ID)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }
}
