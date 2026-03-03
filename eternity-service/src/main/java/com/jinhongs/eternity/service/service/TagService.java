package com.jinhongs.eternity.service.service;

import com.jinhongs.eternity.service.model.dto.TagCreateDTO;
import com.jinhongs.eternity.service.model.dto.TagUpdateDTO;
import com.jinhongs.eternity.service.model.vo.TagVO;

import java.util.List;

public interface TagService {

    Long createTag(TagCreateDTO dto);

    void updateTag(TagUpdateDTO dto);

    void deleteTag(Long id);

    List<TagVO> listTagsWithCount();
}
