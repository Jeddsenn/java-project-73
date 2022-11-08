package hexlet.code.app.service;

import hexlet.code.app.dto.LabelDto;
import hexlet.code.app.model.Label;
import hexlet.code.app.repository.LabelRepository;
import org.springframework.stereotype.Service;


@Service
public class LabelServiceImpl implements LabelService {

    LabelRepository labelRepository;

    @Override
    public Label createLabel(LabelDto labelDto) {
        Label newLabel = new Label();
        newLabel.setName(labelDto.getName());
        return labelRepository.save(newLabel);
    }

    @Override
    public Label updateLabel(LabelDto labelDto, long id) {
        Label label = labelRepository.findById(id).get();
        label.setName(labelDto.getName());
        return labelRepository.save(label);
    }
}
