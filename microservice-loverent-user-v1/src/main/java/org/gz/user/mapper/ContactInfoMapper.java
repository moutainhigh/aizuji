package org.gz.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.gz.user.entity.ContactInfo;
import org.gz.user.supports.Pagination;

@Mapper
public interface ContactInfoMapper {

    int insert(ContactInfo record);

    int insertSelective(ContactInfo record);

    ContactInfo selectByPrimaryKey(Long contactId);

	List<ContactInfo> selectContactByUserId(Long userId);

	void insertBatch(List<ContactInfo> partList);

	List<ContactInfo> queryContactInfoByPage(
			Pagination<ContactInfo> pagination);

}