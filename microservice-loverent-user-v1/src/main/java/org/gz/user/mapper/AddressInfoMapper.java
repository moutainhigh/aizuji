package org.gz.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.gz.user.entity.AddressInfo;

@Mapper
public interface AddressInfoMapper {
    int deleteByPrimaryKey(Long addrId);

    int insert(AddressInfo record);

    int insertSelective(AddressInfo record);

    AddressInfo selectByPrimaryKey(Long addrId);

    int updateByPrimaryKeySelective(AddressInfo record);

    int updateByPrimaryKey(AddressInfo record);

	AddressInfo queryAddressByUserId(Long userId);

	void updateAddressByUserId(AddressInfo addressInfo);

	void removeAddressByPrimaryKey(Long addrId);

	void removeAddressByUserId(Long userId);
}