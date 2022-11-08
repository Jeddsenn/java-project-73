package hexlet.code.app.controller;


import hexlet.code.app.dto.LabelDto;
import hexlet.code.app.model.Label;
import hexlet.code.app.repository.LabelRepository;
import hexlet.code.app.service.LabelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static hexlet.code.app.controller.LabelController.LABEL_CONTROLLER_PATH;

@AllArgsConstructor
@RestController
@RequestMapping("${base-url}" + LABEL_CONTROLLER_PATH)
public class LabelController {
    public static final String LABEL_CONTROLLER_PATH = "/labels";
    private static final String ID = "/id";

    LabelRepository labelRepository;
    LabelService labelService;


    @GetMapping(ID)
    public Label getLabel(@PathVariable long id){
        return labelRepository.findById(id).get();
    }

    @GetMapping("")
    public List<Label> getAll(){
        return labelRepository.findAll();
    }

    @PostMapping("")
    public Label createLabel(@RequestBody LabelDto labelDto){
        return labelService.createLabel(labelDto);
    }

    @PutMapping(ID)
    public Label updateLabel(@RequestBody LabelDto labelDto, @PathVariable long id){
        return labelService.updateLabel(labelDto, id);
    }

    @DeleteMapping(ID)
    public void deleteLabel(@PathVariable long id){
        labelRepository.deleteById(id);
    }
}






