package hexlet.code.service;

import hexlet.code.dto.LabelDto;
import hexlet.code.model.LabelEntity;

import java.util.List;

public interface LabelService {

    LabelEntity createLabel(LabelDto labelDto);
    LabelEntity updateLabel(LabelDto labelDto, long id);
    LabelEntity getLabel(long id);
    List<LabelEntity> getAll();
    void deleteById(long id);
}
