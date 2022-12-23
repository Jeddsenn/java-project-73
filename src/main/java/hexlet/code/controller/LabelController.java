package hexlet.code.controller;


import hexlet.code.dto.request.LabelReq;
import hexlet.code.dto.response.LabelRes;
import hexlet.code.model.LabelEntity;
import hexlet.code.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
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

import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("/api/labels")
public class LabelController {
    private final LabelService labelService;

    @Operation(summary = "Get a label by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Label was found"),
            @ApiResponse(responseCode = "404", description = "Label with this id wasn`t found")
    })
    @GetMapping("/{id}")
    public LabelRes getLabel(@PathVariable long id) {
        return labelService.getLabel(id);
    }

    @Operation(summary = "Get all labels")
    @ApiResponses(@ApiResponse (responseCode = "200"))
    @GetMapping
    public List<LabelEntity> getAll() {
        return labelService.getAll();
    }

    @Operation(summary = "Create a new label")
    @ApiResponses(@ApiResponse(responseCode = "201"))
    @ResponseStatus(CREATED)
    @PostMapping
    public LabelEntity createLabel(@RequestBody @Valid LabelReq labelDto) {
        return labelService.createLabel(labelDto);
    }

    @Operation(summary = "Update label")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Label has been updated"),
            @ApiResponse(responseCode = "404", description = "Label with this id wasn`t found")
    })
    @PutMapping("/{id}")
    public LabelEntity updateLabel(@RequestBody @Valid LabelReq labelDto, @PathVariable long id) {
        return labelService.updateLabel(labelDto, id);
    }
    @Operation(summary = "Delete label")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Label has been deleted"),
            @ApiResponse(responseCode = "404", description = "Label with this id wasn`t found")
    })
    @DeleteMapping("/{id}")
    public void deleteLabel(@PathVariable long id) {
        labelService.deleteById(id);
    }
}






