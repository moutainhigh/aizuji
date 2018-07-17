package org.gz.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.gz.user.entity.CardInfo;

@Mapper
public interface CardInfoMapper {
    int deleteByPrimaryKey(Long cardid);

    int insert(CardInfo record);

    int insertSelective(CardInfo record);

    CardInfo selectByPrimaryKey(Long cardid);

    int updateByPrimaryKeySelective(CardInfo record);

    int updateByPrimaryKey(CardInfo record);

	List<CardInfo> selectByUserId(Long userId);

	CardInfo selectByCardNo(@Param("userId") Long userId, @Param("cardNo") String cardNo);

	void updateByUserId(CardInfo cardInfo);

	List<CardInfo> selectByCondition(CardInfo cardInfo);
}