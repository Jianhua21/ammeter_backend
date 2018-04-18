package com.kashuo.kcp.core;

import com.google.common.collect.Lists;
import com.kashuo.kcp.dao.AmmeterChannelMapper;
import com.kashuo.kcp.dao.condition.ChannelTree;
import com.kashuo.kcp.domain.AmmeterChannel;
import com.kashuo.kcp.domain.AmmeterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fangzhong
 */
@Service
public class AmmeterChannelService {

    @Autowired
    private AmmeterChannelMapper channelMapper;

    private static ChannelTree tn = new ChannelTree(new AmmeterChannel());

    private static List<AmmeterChannel> channelNameList = new ArrayList<>();

    /**
     * 获取所有渠道商信息
     */
    private List<AmmeterChannel> buildTreeAll() {
        return channelMapper.selectByCriteria(new AmmeterChannel());
    }

    /**
     * 批量更新渠道商
     */
    public int updateSubChannelList(Map<String, String> map) {
        return channelMapper.updateSubChannelList(map);
    }


    /**
     * 新增渠道商
     *
     * @param channel 渠道商信息
     * @return int 成功与否
     */
    @Transactional
    public Map<String, Object> addChannel(AmmeterChannel channel) {
        Map<String, Object> map = new HashMap<>();
        //判断添加的渠道商是否存在
        List<AmmeterChannel> list = channelMapper.selectByCriteria(channel);
        if (list != null && list.size() > 0) {
            for (AmmeterChannel c : list) {
                if (c.getChannelName() == null) {
                    c.setChannelName("");
                }
                if (c.getChannelName().equals(channel.getChannelName())) {
                    map.put("code", -1);
                    map.put("message", "渠道商已存在，保存失败");
                    break;
                }
            }
        } else {
            int success;
            //增加渠道商
            success = channelMapper.insertSelective(channel);

            if (success == 1) {
                map.put("code", 0);
            } else {
                map.put("code", 1);
                map.put("message", "保存失败");
            }
        }
        return map;
    }

    /**
     * 更新渠道商
     *
     * @param channel 渠道商信息
     * @return int 成功与否
     */
    @Transactional
    public int editChannel(AmmeterChannel channel) {
        int success;
        //更新渠道商
        success = channelMapper.updateByPrimaryKeySelective(channel);
        return success;
    }

    /**
     * 渠道商列表
     */
    public ChannelTree channelList(AmmeterUser user) {
        //获取当前用户下的渠道商信息
        AmmeterChannel channel = selectByPrimaryKey(user.getChannelId());
        //加载所有渠道商信息
        List<AmmeterChannel> allList = selectChannelLikeLevelCode(channel.getLevelCode(), null);
        ChannelTree rootTree;
        rootTree = new ChannelTree(channel);
        rootTree.buildTree(allList);
        rootTree.setTotalNum(allList.size());
        return rootTree;
    }

    public ChannelTree channelList2(AmmeterUser user) {
        //获取当前用户下的渠道商信息
        AmmeterChannel channel = selectByPrimaryKey2(user.getChannelId());
        //加载所有渠道商信息
        List<AmmeterChannel> allList = selectChannelLikeLevelCode2(channel.getLevelCode(), null);
        ChannelTree rootTree;
        rootTree = new ChannelTree(channel);
        rootTree.buildTree(allList);
        rootTree.setTotalNum(allList.size());
        return rootTree;
    }

    /**
     * ChannelTree中通过channelName查找Tree
     *
     * @return TreeNode
     */
    public ChannelTree getTreeChannelName(ChannelTree node, String channelName) {
        if (channelName.equals(node.getChannelName())) {
            tn = node;
            return tn;
        }
        for (ChannelTree n : node.getNodes()) {
            if (n.getChannelName().equals(channelName)) {
                tn = n;
                break;
            } else {
                getTreeChannelName(n, channelName);
            }
        }
        return tn;
    }

    /**
     * 加载当前用户下渠道商名称下拉框
     *
     * @return ChannelTree
     */
    public List<AmmeterChannel> channelAffiliation(ChannelTree tree) {
        channelNameList.clear();
        buildTree2(tree);
        return channelNameList;
    }

    private ChannelTree buildTree2(ChannelTree node) {
        AmmeterChannel channel = new AmmeterChannel();
        channel.setId(node.getId());
        channel.setChannelCode(node.getChannelCode());
        channel.setChannelName(node.getChannelName());
        channel.setChannelType(node.getChannelType());
        channel.setLevelCode(node.getLevelCode());
        channel.setMobilePhone(node.getMobilePhone());
        channel.setAddress(node.getAddress());
        channel.setStatus(node.getStatus());
        channelNameList.add(channel);
        node.getNodes().forEach(this::buildTree2);
        return node;
    }


    public List<AmmeterChannel> selectChannelLikeLevelCode(String levelCode, String channelName) {
        return channelMapper.selectBySubLevelCode(levelCode, channelName, null);
    }

    public List<AmmeterChannel> selectChannelLikeLevelCode2(String levelCode, String channelName) {
        return channelMapper.selectBySubLevelCode2(levelCode, channelName, null);
    }

    public int updateSubChannelStatus(String levelCode) {
        return channelMapper.updateSubChannelStatus(levelCode);
    }

    public AmmeterChannel selectByPrimaryKey(Integer id) {
        return channelMapper.selectByPrimaryKey(id);
    }

    public AmmeterChannel selectByUserKey(Integer id) {
        return channelMapper.selectByUserKey(id);
    }

    public AmmeterChannel selectParentByChannelCode(String channelCode){
        return channelMapper.selectParentByChannelCode(channelCode);
    }

    public AmmeterChannel selectByPrimaryKey2(Integer id) {
        return channelMapper.selectByPrimaryKey2(id);
    }

    public AmmeterChannel isVpartner(String levelCode) {
        return channelMapper.isVpartner(levelCode);
    }


    public int updateByPrimaryKey(AmmeterChannel record) {
        return channelMapper.updateByPrimaryKey(record);
    }

    public int insertSelective(AmmeterChannel record) {
        return channelMapper.insertSelective(record);
    }

    public int insert(AmmeterChannel record) {
        return channelMapper.insert(record);
    }

    public int deleteByPrimaryKey(Integer id) {
        return channelMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(AmmeterChannel record) {
        return channelMapper.updateByPrimaryKeySelective(record);
    }


    public Map selectParentChannel(String levelCode) {
        if (levelCode != null && levelCode.length() >= 3)
            levelCode = levelCode.substring(0, levelCode.length() - 3);
        return channelMapper.selectParentChannel(levelCode);
    }

    public List<Map> parentChannelRole(Integer channelId) {
        return channelMapper.parentChannelRole(channelId);
    }

    /**
     * 查找自身及父级们是否有停用状态
     *
     * @param levelCode 渠道商层级代码
     */
    public boolean foundParentsIsDisable(String levelCode) {
        List<String> levelCodes = Lists.newArrayList("");
        Matcher matcher = Pattern.compile("\\d{3}").matcher(levelCode);
        while (matcher.find())
            levelCodes.add(levelCode.substring(0, matcher.start()).concat(matcher.group()));
        return channelMapper.foundParentsIsDisable(levelCodes) > 0;
    }

    public List<AmmeterChannel> selectBranceOffice() {
        return channelMapper.selectBranceOffice();
    }

    List<Map> selectPrimaryCompany() {
        return channelMapper.selectPrimaryCompany();
    }

    public AmmeterChannel checkChannelName(String channelName) {
        return channelMapper.checkChannelName(channelName);
    }

    public List<AmmeterChannel> selectChildChannels(Integer channelId, String channelName, String channelCode) {
        return channelMapper.selectChildChannels(channelId, channelName, channelCode);
    }

    public List<AmmeterChannel> selectBySubLevelCode(String levelCode, String channelName, String channelCode) {
        return channelMapper.selectBySubLevelCode(levelCode, channelName, channelCode);
    }


    public AmmeterChannel selectChannelName(String channelCode) {
        return channelMapper.selectChannelName(channelCode);
    }

    public List<AmmeterChannel> selectChannelByLikeName() {
        return channelMapper.selectChannelByLikeName();
    }

    public List<AmmeterChannel> selectListChannelCode(Integer channelId, String channelCode) {
        return channelMapper.selectListChannelCode(channelId, channelCode);
    }

    public List<AmmeterChannel> selectListChannel(Integer channelId,String channelCode) {
        return channelMapper.selectListChannel(channelId,channelCode);
    }

    /**
     * 根据levelcode获取自己及其一级子渠道商的channelcode
     *
     * @param levelCode
     * @return List<String>
     */
    public List<String> selectChildChannelCodeList(String levelCode, String name) {
        return channelMapper.selectChildChannelCodes(levelCode, name);
    }

    /**
     * 根据channelId获取自己及其一级子渠道商的channelcode
     *
     * @param channelId 渠道商id
     * @return List<String> list
     */
    public List<String> selectallChildChannelCodes(Integer channelId, String name,String code) {
        return channelMapper.selectallChildChannelCodes(channelId, name,code);
    }

    public List<String> selectPartnerChannelCodes(Integer channelId,List<Integer> channelTypes) {
        return channelMapper.selectPartnerChannelCodes(channelId, channelTypes);
    }

    /**
     * 根据channelId获取自己及其一级子渠道商的chanelId
     *
     * @param channelId 渠道商id
     * @return List<Integer> list
     */
    public List<Integer> getSonChannelIdsByChannelId(Integer channelId) {
        return channelMapper.getSonChannelIdsByChannelId(channelId);
    }

    /**
     * 卡说分公司
     *
     * @param channelType 渠道商类别
     * @param status      状态，0:停用 1:启用
     * @return List<Channel> getKSBranchOffice
     */
    public List<AmmeterChannel> getKSBranchOffice(Byte channelType, Byte status) {
        return channelMapper.getKSBranchOffice(channelType, status);
    }

    public List<String> getChildrenChannelCodes(Integer channelId) {
        return channelMapper.getChildrenChannelCodes(channelId);
    }
}
