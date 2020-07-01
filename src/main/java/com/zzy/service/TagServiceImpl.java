package com.zzy.service;

import com.zzy.dao.TagRepository;
import com.zzy.exception.NotFoundException;
import com.zzy.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTag(Long tagId) {
        return tagRepository.getOne(tagId);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.getByName(name);
    }

    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {
        //根据标签id查询选中的标签
        return tagRepository.findAllById(convertToList(ids));
    }

    @Override
    public List<Tag> listTopTags(Integer size) {
        Pageable pageable = PageRequest.of(0,size, Sort.Direction.DESC,"blogs.size");
        return tagRepository.findTop(pageable);
    }

    private List<Long> convertToList(String ids) {
        List<Long> idList = new ArrayList<>();
        if (ids != null && !"".equals(ids)) {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                idList.add(new Long(id));
            }
        }
        return idList;
    }

    @Override
    public Tag update(Tag tag, Long tagId) {
        Tag tag1 = tagRepository.getOne(tagId);
        if (tag1 == null) {
            throw new NotFoundException("没有此标签");
        }
        BeanUtils.copyProperties(tag, tag1);
        return tagRepository.save(tag1);

    }

    @Override
    public void deleteTag(Long tagId) {

    }
}
