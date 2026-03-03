package com.jinhongs.eternity.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jinhongs.eternity.common.exception.ClientException;
import com.jinhongs.eternity.common.utils.result.PageResult;
import com.jinhongs.eternity.dao.mysql.repository.ArticleTagsRepository;
import com.jinhongs.eternity.dao.mysql.repository.ArticlesRepository;
import com.jinhongs.eternity.dao.mysql.repository.TagsRepository;
import com.jinhongs.eternity.model.entity.ArticleTags;
import com.jinhongs.eternity.model.entity.Articles;
import com.jinhongs.eternity.model.entity.Tags;
import com.jinhongs.eternity.service.model.converter.ServiceArticleConverter;
import com.jinhongs.eternity.service.model.dto.ArticleCreateDTO;
import com.jinhongs.eternity.service.model.dto.ArticleUpdateDTO;
import com.jinhongs.eternity.service.model.dto.security.SecurityUserDetailsImpl;
import com.jinhongs.eternity.service.model.vo.ArticleDetailVO;
import com.jinhongs.eternity.service.model.vo.ArticleListVO;
import com.jinhongs.eternity.service.model.vo.TagVO;
import com.jinhongs.eternity.service.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private static final Byte STATUS_DRAFT = 10;
    private static final Byte STATUS_PUBLISHED = 20;
    private static final Byte STATUS_DELETED = 30;

    private final ArticlesRepository articlesRepository;
    private final ArticleTagsRepository articleTagsRepository;
    private final TagsRepository tagsRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createArticle(ArticleCreateDTO dto) {
        SecurityUserDetailsImpl userDetails = getCurrentUser();

        Articles article = ServiceArticleConverter.INSTANCE.toArticles(dto);
        article.setUserId(userDetails.getId());
        article.setViewCount(0);
        if (article.getStatus() == null) {
            article.setStatus(STATUS_DRAFT);
        }

        boolean saved = articlesRepository.save(article);
        if (!saved) {
            throw new ClientException("文章保存失败");
        }

        saveArticleTags(article.getId(), dto.getTagIds());
        log.info("创建文章成功，articleId={}", article.getId());
        return article.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateArticle(ArticleUpdateDTO dto) {
        // 校验文章是否存在
        getArticleById(dto.getId());

        Articles article = new Articles();
        article.setId(dto.getId());
        article.setTitle(dto.getTitle());
        article.setContentMd(dto.getContent());
        article.setSummary(dto.getSummary());
        article.setCoverImage(dto.getCoverImage());
        if (dto.getStatus() != null) {
            article.setStatus(dto.getStatus());
        }

        boolean updated = articlesRepository.updateById(article);
        if (!updated) {
            throw new ClientException("文章更新失败");
        }

        // 先删除旧标签关联，再插入新标签关联
        articleTagsRepository.remove(
                new LambdaQueryWrapper<ArticleTags>().eq(ArticleTags::getArticleId, dto.getId())
        );
        saveArticleTags(dto.getId(), dto.getTagIds());
        log.info("更新文章成功，articleId={}", dto.getId());
    }

    @Override
    public ArticleDetailVO getArticleDetail(Long id) {
        Articles article = getArticleById(id);
        ArticleDetailVO vo = ServiceArticleConverter.INSTANCE.toArticleDetailVO(article);
        vo.setTags(getTagsByArticleId(id));
        return vo;
    }

    @Override
    public PageResult<ArticleListVO> listArticles(int page, int size) {
        Page<Articles> pageParam = new Page<>(page, size);
        var result = articlesRepository.page(pageParam,
                new LambdaQueryWrapper<Articles>()
                        .ne(Articles::getStatus, STATUS_DELETED)
                        .orderByDesc(Articles::getCreateTime)
        );

        List<ArticleListVO> records = result.getRecords().stream()
                .map(article -> {
                    ArticleListVO vo = ServiceArticleConverter.INSTANCE.toArticleListVO(article);
                    vo.setTags(getTagsByArticleId(article.getId()));
                    return vo;
                }).toList();

        return new PageResult<>(records, result.getTotal(), result.getCurrent(), result.getSize());
    }

    @Override
    public void publishArticle(Long id) {
        Articles article = getArticleById(id);
        article.setStatus(STATUS_PUBLISHED);
        articlesRepository.updateById(article);
        log.info("发布文章成功，articleId={}", id);
    }

    @Override
    public void deleteArticle(Long id) {
        Articles article = getArticleById(id);
        article.setStatus(STATUS_DELETED);
        articlesRepository.updateById(article);
        log.info("删除文章成功，articleId={}", id);
    }

    @Override
    public ArticleDetailVO getPublishedArticleDetail(Long id) {
        Articles article = articlesRepository.getOne(
                new LambdaQueryWrapper<Articles>()
                        .eq(Articles::getId, id)
                        .eq(Articles::getStatus, STATUS_PUBLISHED)
        );
        if (article == null) {
            throw new ClientException("文章不存在");
        }
        ArticleDetailVO vo = ServiceArticleConverter.INSTANCE.toArticleDetailVO(article);
        vo.setTags(getTagsByArticleId(id));
        return vo;
    }

    @Override
    public PageResult<ArticleListVO> listPublishedArticles(int page, int size) {
        Page<Articles> pageParam = new Page<>(page, size);
        var result = articlesRepository.page(pageParam,
                new LambdaQueryWrapper<Articles>()
                        .eq(Articles::getStatus, STATUS_PUBLISHED)
                        .orderByDesc(Articles::getCreateTime)
        );

        List<ArticleListVO> records = result.getRecords().stream()
                .map(article -> {
                    ArticleListVO vo = ServiceArticleConverter.INSTANCE.toArticleListVO(article);
                    vo.setTags(getTagsByArticleId(article.getId()));
                    return vo;
                }).toList();

        return new PageResult<>(records, result.getTotal(), result.getCurrent(), result.getSize());
    }

    /**
     * 查询全部标签
     */
    @Override
    public List<TagVO> listAllTags() {
        return tagsRepository.list().stream()
                .map(ServiceArticleConverter.INSTANCE::toTagVO)
                .toList();
    }

    /**
     * 根据 ID 获取文章，若不存在或已删除则抛出异常
     */
    private Articles getArticleById(Long id) {
        Articles article = articlesRepository.getById(id);
        if (article == null || STATUS_DELETED.equals(article.getStatus())) {
            throw new ClientException("文章不存在");
        }
        return article;
    }

    /**
     * 批量保存文章-标签关联
     */
    private void saveArticleTags(Long articleId, List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return;
        }
        List<ArticleTags> articleTags = tagIds.stream()
                .map(tagId -> {
                    ArticleTags at = new ArticleTags();
                    at.setArticleId(articleId);
                    at.setTagId(tagId);
                    return at;
                }).toList();
        articleTagsRepository.saveBatch(articleTags, articleTags.size());
    }

    /**
     * 查询文章的所有标签
     */
    private List<TagVO> getTagsByArticleId(Long articleId) {
        List<Long> tagIds = articleTagsRepository.list(
                new LambdaQueryWrapper<ArticleTags>().eq(ArticleTags::getArticleId, articleId)
        ).stream().map(ArticleTags::getTagId).toList();

        if (tagIds.isEmpty()) {
            return List.of();
        }

        List<Tags> tags = tagsRepository.listByIds(tagIds);
        return tags.stream()
                .map(ServiceArticleConverter.INSTANCE::toTagVO)
                .toList();
    }

    /**
     * 从 SecurityContext 中获取当前登录用户
     */
    private SecurityUserDetailsImpl getCurrentUser() {
        return (SecurityUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
