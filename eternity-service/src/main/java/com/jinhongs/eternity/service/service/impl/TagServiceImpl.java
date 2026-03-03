package com.jinhongs.eternity.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jinhongs.eternity.common.exception.ClientException;
import com.jinhongs.eternity.dao.mysql.repository.ArticleTagsRepository;
import com.jinhongs.eternity.dao.mysql.repository.TagsRepository;
import com.jinhongs.eternity.model.entity.ArticleTags;
import com.jinhongs.eternity.model.entity.Tags;
import com.jinhongs.eternity.service.model.dto.TagCreateDTO;
import com.jinhongs.eternity.service.model.dto.TagUpdateDTO;
import com.jinhongs.eternity.service.model.vo.TagVO;
import com.jinhongs.eternity.service.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagsRepository tagsRepository;
    private final ArticleTagsRepository articleTagsRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTag(TagCreateDTO dto) {
        Tags tag = new Tags();
        tag.setTagName(dto.getTagName());
        tagsRepository.save(tag);
        return tag.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTag(TagUpdateDTO dto) {
        Tags tag = tagsRepository.getById(dto.getId());
        if (tag == null) {
            throw new ClientException("标签不存在");
        }
        tag.setTagName(dto.getTagName());
        tagsRepository.updateById(tag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTag(Long id) {
        articleTagsRepository.remove(new LambdaQueryWrapper<ArticleTags>().eq(ArticleTags::getTagId, id));
        tagsRepository.removeById(id);
    }

    @Override
    public List<TagVO> listTagsWithCount() {
        List<Tags> tags = tagsRepository.list();
        List<ArticleTags> articleTags = articleTagsRepository.list();

        Map<Long, Long> countMap = articleTags.stream()
                .collect(Collectors.groupingBy(ArticleTags::getTagId, Collectors.counting()));

        return tags.stream().map(tag -> {
            TagVO vo = new TagVO();
            vo.setId(tag.getId());
            vo.setTagName(tag.getTagName());
            vo.setArticleCount(countMap.getOrDefault(tag.getId(), 0L).intValue());
            return vo;
        }).toList();
    }
}
