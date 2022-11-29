package hexlet.code.controller;


import hexlet.code.dto.LabelDto;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;
import hexlet.code.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
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

import static hexlet.code.controller.LabelController.LABEL_CONTROLLER_PATH;
import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + LABEL_CONTROLLER_PATH)
public class LabelController {
    public static final String LABEL_CONTROLLER_PATH = "/labels";
    private static final String ID = "/{id}";


    private final LabelRepository labelRepository;

    private final LabelService labelService;

    @Operation(summary = "Get a label by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Label was found"),
            @ApiResponse(responseCode = "404", description = "Label with this id wasn`t found")
    })
    @GetMapping(ID)
    public Label getLabel(@PathVariable long id) {
        return labelRepository.findById(id).get();
    }

    @Operation(summary = "Get all labels")
    @ApiResponses(@ApiResponse (responseCode = "200"))
    @GetMapping
    public List<Label> getAll() {
        return labelRepository.
                findAll();
    }

    @Operation(summary = "Create a new label")
    @ApiResponses(@ApiResponse(responseCode = "201", content =
    @Content(schema =
    @Schema (implementation = Label.class)
    )))
    @ResponseStatus(CREATED)
    @PostMapping
    public Label createLabel(@RequestBody @Valid LabelDto labelDto) {
        return labelService.createLabel(labelDto);
    }

    @Operation(summary = "Update label")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Label has been updated"),
            @ApiResponse(responseCode = "404", description = "Label with this id wasn`t found")
    })
    @PutMapping(ID)
    public Label updateLabel(@RequestBody @Valid LabelDto labelDto, @PathVariable long id) {
        return labelService.updateLabel(labelDto, id);
    }
    @Operation(summary = "Delete label")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Label has been deleted"),
            @ApiResponse(responseCode = "404", description = "Label with this id wasn`t found")
    })
    @DeleteMapping(ID)
    public void deleteLabel(@PathVariable long id) {
        labelRepository.deleteById(id);
    }
}






