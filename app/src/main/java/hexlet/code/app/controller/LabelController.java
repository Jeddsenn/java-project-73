package hexlet.code.app.controller;


import hexlet.code.app.dto.LabelDto;
import hexlet.code.app.model.Label;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.service.LabelService;
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

import java.util.List;

import static hexlet.code.app.controller.LabelController.LABEL_CONTROLLER_PATH;
import static org.springframework.http.HttpStatus.CREATED;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + LABEL_CONTROLLER_PATH)
public class LabelController {
    public static final String LABEL_CONTROLLER_PATH = "/labels";
    private static final String ID = "/{id}";


    private final LabelRepository labelRepository;

    private final LabelService labelService;


    @GetMapping(ID)
    public Label getLabel(@PathVariable long id) {
        return labelRepository.findById(id).get();
    }

    @GetMapping("")
    public List<Label> getAll() {
        return labelRepository.findAll();
    }


    @ResponseStatus(CREATED)
    @PostMapping("")
    public Label createLabel(@RequestBody LabelDto labelDto) {
        return labelService.createLabel(labelDto);
    }

    @PutMapping(ID)
    public Label updateLabel(@RequestBody LabelDto labelDto, @PathVariable long id) {
        return labelService.updateLabel(labelDto, id);
    }

    @DeleteMapping(ID)
    public void deleteLabel(@PathVariable long id) {
        labelRepository.deleteById(id);
    }
}






