package com.kashuo.kcp.core;

import com.kashuo.common.base.domain.Page;
import com.kashuo.common.mybatis.helper.PageHelper;
import com.kashuo.kcp.dao.AmmeterUserMapper;
import com.kashuo.kcp.dao.condition.AmmeterUserCondition;
import com.kashuo.kcp.domain.AmmeterUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户服务类
 * Created by Mr.ZHAO on 2016/5/12.
 */
@Service
public class AmmeterUserService {

    private static Logger logger = LoggerFactory.getLogger(AmmeterUserService.class);


    @Autowired
    private AmmeterUserMapper userMapper;
//    @Autowired
//    private RoleMapper roleMapper;
//    @Autowired
//    private DepartmentMapper departmentMapper;
//    @Autowired
//    private ChannelMapper channelMapper;

//    @Value("${app.constant.menuUrlAccessDeny}")
//    private String menuUrlAccessDeny;
//    @Value("${app.constant.agentAccessDeny}")
//    private String agentAccessDeny;
    /**
     * 根据用户角色获取权限代码
     */
//    @Cacheable(value = "UserService.loadPriCode")
//    public List<String> loadPriCode(Integer roleid,Integer channelId) {
//        logger.info("---------start load priCode -----------");
//        List<String> priCode = userMapper.loadPriCode(roleid);
//        Channel channel = channelMapper.selectByPrimaryKey(channelId);
//        if(agentAccessDeny != null){
//            String[] agentTypes = agentAccessDeny.split(",");
//            for (String agentType:agentTypes){
//                if(agentType.equals(channel.getChannelType().toString())){
//                    if(menuUrlAccessDeny != null){
//                        List<String> urlList = new ArrayList<>();
//                        String[] urls = menuUrlAccessDeny.split(",");
//                        for (String url:urls){
//                            urlList.add(url);
//                        }
//                        List<String> removeCode = userMapper.removePriCode(urlList);
//                        if(removeCode != null){
//                            priCode.removeAll(removeCode);
//                        }
//                    }
//                    break;
//                }
//            }
//
//        }
//
//        return priCode;
//    }
//
    /***
     *
     */
    @CacheEvict(value = "UserService.loadPriCode", allEntries = true)
    public void evictAll(){
        logger.info("delete priCode cache ....");
    }

    /**
     * 渠道商用户列表
     *
     * @return 用户管理列表分页数据
     */
    public Page<?> userManagerList(AmmeterUserCondition condition) {
        PageHelper.startPage(condition.getPageIndex(), condition.getPageSize());
        return userMapper.selectUserMangerList(condition);
    }

    /**
     * 渠道商用户编辑
     *
     * @return int
     */
    public int editUser(AmmeterUserCondition condition) {
        int success = 0;
        AmmeterUser user = new AmmeterUser();
        user.setId(condition.getUserId());
        user.setLoginName(condition.getLoginName());
        user.setRealname(condition.getRealName());
        user.setMobilePhone(condition.getMobilePhone());
        user.setEmail(condition.getEmail());
        //更新用户信息
        success += userMapper.updateByPrimaryKeySelective(user);
//        Role role = new Role();
//        role.setId(condition.getRoleId());
//        role.setRoleName(condition.getRoleName());
//        //更新角色
//        success += roleMapper.updateByPrimaryKeySelective(role);
//        Department depart = new Department();
//        depart.setId(condition.getDepartmentId());
//        depart.setDepartmentName(condition.getDepartMentName());
//        //更新部门
//        success += departmentMapper.updateByPrimaryKeySelective(depart);
        return success == 3 ? 0 : -1;
    }

    @CacheEvict(value = "UserService.checkToken", allEntries = true)
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public int insert(AmmeterUser record) {
        return userMapper.insert(record);
    }

    public int insertSelective(AmmeterUser record) {
        return userMapper.insertSelective(record);
    }

    public int updateByPrimaryKeySelective(AmmeterUser record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    public AmmeterUser selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @CacheEvict(value = "UserService.checkToken", allEntries = true)
    public int updateByPrimaryKey(AmmeterUser record) {
        return userMapper.updateByPrimaryKey(record);
    }

    public AmmeterUser login(String username) {
        return userMapper.login(username);
    }

    @Cacheable(value = "UserService.checkToken")
    public AmmeterUser checkToken(String token) {
        return userMapper.checkToken(token);
    }

    public AmmeterUser checkLoginName(String loginName) {
        return userMapper.checkUserName(loginName);
    }

    public AmmeterUser checkOtherUserName(String loginName,Integer id) {
        return userMapper.checkOtherUserName(loginName,id);
    }

    public AmmeterUser checkOtherMobile(String loginName,Integer id) {
        return userMapper.checkOtherMobile(loginName,id);
    }

    public AmmeterUser checkMobile(String mobilePhone) {
        return userMapper.checkMobile(mobilePhone);
    }

    public Page<?> selectListSelective(AmmeterUserCondition condition) {
        if (condition.getRealName() != null) {
            condition.setRealName(condition.getRealName().trim());
        }
        if(condition.getMobilePhone()!=null) {
            condition.setMobilePhone(condition.getMobilePhone().trim());
        }
        PageHelper.startPage(condition.getPageIndex(), condition.getPageSize());
        return userMapper.selectListSelective(condition);
    }

    public AmmeterUser selectUserByPhone(String phone){
        return userMapper.selectUserByMobilePhone(phone);
    }

    public List<AmmeterUser> selectUserByChannelList(List<Integer> channelIdList){
        return userMapper.selectUserByChannelList(channelIdList,0);
    }

    public List<AmmeterUser> selectUserByChannelList(List<Integer> channelIdList,Integer userId){
        return userMapper.selectUserByChannelList(channelIdList,userId);
    }
}
