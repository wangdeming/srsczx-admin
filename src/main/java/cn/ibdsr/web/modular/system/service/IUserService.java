package cn.ibdsr.web.modular.system.service;

import cn.ibdsr.web.modular.system.transfer.UserDto;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;
import java.util.Map;

/**
 * 用户业务层
 *
 * @author lvyou
 * @Date 2017年1月11日 下午1:08:17
 */
public interface IUserService {

    /**
     * 查询除当前用户以外的用户列表
     *
     * @param page 分页参数
     * @param keyword 搜索关键字
     * @param id 当前用户id
     * @return
     */
    List<Map<String, Object>> list(Page<Map<String, Object>> page, String keyword, Long id);

    /**
     * 添加用户账号
     *
     * @param user
     */
    void addUser(UserDto user);
}
