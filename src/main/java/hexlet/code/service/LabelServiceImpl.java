package hexlet.code.service;

import hexlet.code.dto.LabelDto;
import hexlet.code.model.LabelEntity;
import hexlet.code.repository.LabelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@AllArgsConstructor
public class LabelServiceImpl implements LabelService {

    private LabelRepository labelRepository;

    @Override
    public LabelEntity createLabel(final LabelDto labelDto) {
        LabelEntity newLabel = new LabelEntity();
        newLabel.setName(labelDto.name());
        return labelRepository.save(newLabel);
    }

    @Override
    public LabelEntity updateLabel(final LabelDto labelDto, final long id) {
        LabelEntity label = labelRepository.findById(id).get();
        label.setName(labelDto.name());
        return labelRepository.save(label);
    }
}
