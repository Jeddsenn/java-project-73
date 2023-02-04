package hexlet.code.service;

import hexlet.code.dto.request.LabelReq;
import hexlet.code.dto.response.LabelRes;
import java.util.List;

public interface LabelService {

    LabelRes createLabel(LabelReq labelDto);
    LabelRes updateLabel(LabelReq labelDto, long id);
    LabelRes getLabel(long id);
    List<LabelRes> getAll();
    void deleteById(long id);
}
