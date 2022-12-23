package hexlet.code.service;

import hexlet.code.dto.request.LabelReq;
import hexlet.code.dto.response.LabelRes;
import hexlet.code.model.LabelEntity;
import hexlet.code.repository.LabelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@AllArgsConstructor
public class LabelServiceImpl implements LabelService {

    private LabelRepository labelRepository;

    @Override
    public LabelEntity createLabel(final LabelReq labelDto) {
        LabelEntity newLabel = new LabelEntity();
        newLabel.setName(labelDto.name());
        return labelRepository.save(newLabel);
    }

    @Override
    public LabelEntity updateLabel(final LabelReq labelDto, final long id) {
        LabelEntity label = labelRepository.findById(id).orElseThrow();
        label.setName(labelDto.name());
        return labelRepository.save(label);
    }

    @Override
    public LabelRes getLabel(long id) {
        LabelEntity label = labelRepository.findById(id).orElseThrow();
        return new LabelRes(label.getId(), label.getName());
    }

    @Override
    public List<LabelEntity> getAll() {
        return labelRepository.findAll();
    }

    @Override
    public void deleteById(long id) {
        labelRepository.deleteById(id);
    }
}
