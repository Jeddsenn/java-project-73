package hexlet.code.service;

import hexlet.code.dto.request.ReqLabelDto;
import hexlet.code.model.LabelEntity;

import java.util.List;

public interface LabelService {

    LabelEntity createLabel(ReqLabelDto labelDto);
    LabelEntity updateLabel(ReqLabelDto labelDto, long id);
    LabelEntity getLabel(long id);
    List<LabelEntity> getAll();
    void deleteById(long id);
}
