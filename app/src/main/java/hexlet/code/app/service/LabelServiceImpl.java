package hexlet.code.app.service;

import hexlet.code.app.dto.LabelDto;
import hexlet.code.app.model.Label;
import hexlet.code.app.repository.LabelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@AllArgsConstructor
public class LabelServiceImpl implements LabelService {

    private LabelRepository labelRepository;

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
