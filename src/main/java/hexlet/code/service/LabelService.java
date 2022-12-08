package hexlet.code.service;

import hexlet.code.dto.LabelDto;
import hexlet.code.model.LabelEntity;

public interface LabelService {

    LabelEntity createLabel(LabelDto labelDto);
    LabelEntity updateLabel(LabelDto labelDto, long id);
}
