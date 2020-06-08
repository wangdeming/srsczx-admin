package cn.ibdsr.web.modular.system.service.impl;

import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.common.constant.state.MenuStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.MenuMapper;
import cn.ibdsr.web.common.persistence.model.Menu;
import cn.ibdsr.web.modular.system.dao.MenuDao;
import cn.ibdsr.web.modular.system.service.IMenuService;
import cn.ibdsr.web.modular.system.transfer.MenuDTO;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单服务
 *
 * @author fengshuonan
 * @date 2017-05-05 22:20
 */
@Service
public class MenuServiceImpl implements IMenuService {

    @Resource
    MenuMapper menuMapper;

    @Resource
    MenuDao menuDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delMenu(Long menuId) {

        //删除菜单
        this.menuMapper.deleteById(menuId);

        //删除关联的relation
        this.menuDao.deleteRelationByMenu(menuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delMenuContainSubMenus(Long menuId) {

        Menu menu = menuMapper.selectById(menuId);

        //删除当前菜单
        delMenu(menuId);

        //删除所有子菜单
        Wrapper<Menu> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("pcodes", "%[" + menu.getCode() + "]%");
        List<Menu> menus = menuMapper.selectList(wrapper);
        for (Menu temp : menus) {
            delMenu(temp.getId());
        }
    }

    /**
     * 添加菜单
     *
     * @param menu
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMenu(MenuDTO menu) {
        Integer menuCount = this.menuMapper.selectCount(new EntityWrapper<Menu>().eq("code", menu.getCode()));
        if (menuCount >= 1) {
            throw new BussinessException(BizExceptionEnum.MENU_CODE_IS_EXIT);
        }
        //设置父级菜单编号
        menuSetPcode(menu);
        menu.setStatus(MenuStatus.ENABLE.getCode());
        Menu insertMenu = new Menu();
        BeanUtils.copyProperties(menu,insertMenu);
        this.menuMapper.insert(insertMenu);
    }

    /**
     * 修改菜单
     *
     * @param menu
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editMenu(MenuDTO menu) {
        if (menu == null || menu.getId() == null) {
            throw new BussinessException(BizExceptionEnum.REQUEST_INVALIDATE);
        }
        Menu flag = new Menu();
        flag.setCode(menu.getCode());
        flag = this.menuMapper.selectOne(flag);
        if (flag != null && !flag.getId().equals(menu.getId())) {
            throw new BussinessException(BizExceptionEnum.MENU_CODE_IS_EXIT);
        }
        if (this.menuMapper.selectById(menu.getId()) == null) {
            throw new BussinessException(BizExceptionEnum.MENU_NOT_EXIT);
        }
        //设置父级菜单编号
        menuSetPcode(menu);
        Menu updateMenu = new Menu();
        BeanUtils.copyProperties(menu,updateMenu);
        this.menuMapper.updateById(updateMenu);
    }

    /**
     * 根据请求的父级菜单编号设置pcode和层级
     */
    private void menuSetPcode(MenuDTO menu) {
        if (ToolUtil.isEmpty(menu.getPcode()) || "0".equals(menu.getPcode())) {
            menu.setPcode("0");
            menu.setPcodes("[0],");
            menu.setLevels(1);
        } else {
            int code = Integer.parseInt(menu.getPcode());
            Menu pMenu = menuMapper.selectById(code);
            Integer pLevels = pMenu.getLevels();
            menu.setPcode(pMenu.getCode());

            //如果编号和父编号一致会导致无限递归
            if (menu.getCode().equals(menu.getPcode())) {
                throw new BussinessException(BizExceptionEnum.MENU_PCODE_COINCIDENCE);
            }

            menu.setLevels(pLevels + 1);
            menu.setPcodes(pMenu.getPcodes() + "[" + pMenu.getCode() + "],");
        }
    }
}
