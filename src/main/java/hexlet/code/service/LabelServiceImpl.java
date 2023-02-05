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
    public LabelRes createLabel(final LabelReq labelDto) {
        LabelEntity newLabel = new LabelEntity();
        newLabel.setName(labelDto.name());
        labelRepository.save(newLabel);
        return new LabelRes(newLabel.getId(), newLabel.getName(), newLabel.getCreatedAt());
    }

    @Override
    public LabelRes updateLabel(final LabelReq labelDto, final long id) {
        LabelEntity label = labelRepository.findById(id).orElseThrow();
        label.setName(labelDto.name());
        labelRepository.save(label);
        return new LabelRes(label.getId(), label.getName(), label.getCreatedAt());
    }

    @Override
    public LabelRes getLabel(long id) {
        LabelEntity label = labelRepository.findById(id).orElseThrow();
        return new LabelRes(label.getId(), label.getName(), label.getCreatedAt());
    }

    @Override
    public List<LabelRes> getAll() {
        return labelRepository.findAll().stream()
                .map(label -> new LabelRes(label.getId(), label.getName(), label.getCreatedAt()))
                .toList();
    }

    @Override
    public void deleteById(long id) {
        labelRepository.deleteById(id);
    }
}
