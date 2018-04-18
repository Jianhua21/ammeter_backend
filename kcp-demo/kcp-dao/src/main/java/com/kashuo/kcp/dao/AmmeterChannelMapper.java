package com.kashuo.kcp.dao;

import com.kashuo.kcp.domain.AmmeterChannel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AmmeterChannelMapper {

    /**
     * 根绝查询条件查询渠道商信息
     */
    List<AmmeterChannel> selectByCriteria(AmmeterChannel record);

    /**
     * 新增渠道商
     */
    int insertSelective(AmmeterChannel record);

    /**
     * 更新渠道商
     */
    int updateByPrimaryKeySelective(AmmeterChannel record);

    /**
     * 根据主键查询渠道商信息
     */
    AmmeterChannel selectByPrimaryKey(Integer id);

    AmmeterChannel selectByUserKey(Integer userId);

    AmmeterChannel selectParentByChannelCode(String channelCode);

    AmmeterChannel selectByPrimaryKey2(Integer id);

    AmmeterChannel isVpartner(String levelCode);

    List<AmmeterChannel> selectBySubLevelCode(@Param("levelCode") String levelCode, @Param("channelName") String channelName, @Param("channelCode") String channelCode);

    List<AmmeterChannel> selectBySubLevelCode2(@Param("levelCode") String levelCode, @Param("channelName") String channelName, @Param("channelCode") String channelCode);

    int updateSubChannelList(Map<String, String> map);

    int updateSubChannelStatus(String levelCode);

    int deleteByPrimaryKey(Integer id);

    int insert(AmmeterChannel record);

    int updateByPrimaryKey(AmmeterChannel record);

    Map selectParentChannel(String levelCode);

    List<Map> parentChannelRole(Integer channelId);

    /**
     * 查找自身及父级们停用状态个数
     *
     * @param levelCodes 层级代码
     */
    int foundParentsIsDisable(List<String> levelCodes);

    List<AmmeterChannel> selectBranceOffice();

    List<Map> selectPrimaryCompany();

    AmmeterChannel checkChannelName(String channelName);

    List<AmmeterChannel> selectChildChannels(@Param("channelId") Integer channelId, @Param("channelName") String
            channelName, @Param("channelCode") String channelCode);

    AmmeterChannel selectChannelName(String channelCode);

    List<AmmeterChannel> selectChannelByLikeName();

    List<AmmeterChannel> selectListChannelCode(@Param("channelId") Integer channelId, @Param("channelCode") String channelCode);

    List<AmmeterChannel> selectListChannel(@Param("channelId") Integer channelId, @Param("channelCode") String channelCode);

    List<String> selectChildChannelCodes(@Param("levelCode") String levelCode, @Param("channelName") String channelName);

    /**
     * 根据当前用户所属渠道商id查询当前渠道商下面所有的渠道商信息
     *
     * @param channelId   渠道商id
     * @param channelName 渠道商名称
     * @return list
     */
    List<String> selectallChildChannelCodes(@Param("channelId") Integer channelId, @Param("channelName") String channelName, @Param("channelCode") String channelCode);

    List<String> selectPartnerChannelCodes(@Param("channelId") Integer channelId, @Param("list") List<Integer> channelTypes);

    List<Integer> getSonChannelIdsByChannelId(Integer channelId);

    List<AmmeterChannel> getKSBranchOffice(@Param("channelType") Byte channelType, @Param("status") Byte status);

    List<String> getChildrenChannelCodes(Integer channelId);
}