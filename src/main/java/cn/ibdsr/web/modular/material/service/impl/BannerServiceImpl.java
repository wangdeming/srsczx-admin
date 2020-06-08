package cn.ibdsr.web.modular.material.service.impl;

import cn.ibdsr.fastdfs.base.service.FdfsClientService;
import cn.ibdsr.web.common.constant.state.DeleteStatus;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.model.Banner;
import cn.ibdsr.web.core.shiro.ShiroKit;
import cn.ibdsr.web.core.util.FdfsFileUtil;
import cn.ibdsr.web.modular.material.dao.BannerDao;
import cn.ibdsr.web.modular.material.service.IBannerService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description 轮播图管理Service
 * @Version V1.0
 * @CreateDate 2019/8/19 16:38
 * <p>
 * Date           Author               Description
 * ------------------------------------------------------
 * 2019/8/19      lvyou                轮播图管理Service
 */
@Service
public class BannerServiceImpl implements IBannerService {

    @Resource
    private BannerDao bannerDao;
    @Resource
    private FdfsClientService fdfsClientService;

    /**
     * 获取轮播图列表
     *
     * @return
     */
    @Override
    public List<Banner> list() {
        Wrapper<Banner> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("id", "path");
        wrapper.eq("is_deleted", DeleteStatus.UN_DELETED.getCode());
        wrapper.orderBy("sort", true);
        return bannerDao.selectList(wrapper);
    }

    /**
     * 保存轮播图信息
     *
     * @param bannerParam 轮播图数组
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBannerList(List<String> bannerParam) {
//        if (bannerParam == null || bannerParam.size() <= 0) {
//            return;
//        }
        if (bannerParam.size() > 5) {
            throw new BussinessException(BizExceptionEnum.BANNER_IMAGE_CANT_LARGER);
        }
        for (int i = 0; i < bannerParam.size(); i++) {
            String paramPath = bannerParam.get(i);
            if (StringUtils.isNotEmpty(paramPath) && paramPath.indexOf(FdfsFileUtil.PREFIX_IMAGE_URL) != -1) {
                bannerParam.set(i, FdfsFileUtil.cutFileURL(paramPath));
            }
        }

        //处理大事记关联图片
        List<Banner> banners = this.list();
        List<String> oldBanners = new ArrayList<>();
        for (Banner banner : banners) {
            String bannerPath = banner.getPath();
            if (bannerPath != null) {
                oldBanners.add(bannerPath);
            }
        }

        Date operateTime = new Date();
        Long operateUser = ShiroKit.getUser().getId();

        //需要删除的图片
        List<String> deleteBanners = new ArrayList<>();
        for (String bannerPath : oldBanners) {
            if (!bannerParam.contains(bannerPath)) {
                deleteBanners.add(bannerPath);
            }
        }
        if (deleteBanners.size() > 0) {
            Banner banner = new Banner();
            banner.setIsDeleted(DeleteStatus.DELETED.getCode());
            banner.setModifiedTime(operateTime);
            banner.setModifiedUser(operateUser);
            bannerDao.update(banner, new EntityWrapper<Banner>().eq("is_deleted", DeleteStatus.UN_DELETED.getCode()).in("path", deleteBanners));
        }
        deleteImageFile(deleteBanners);

        //需要新增的图片
        for (int i = 0; i < bannerParam.size(); i++) {
            String bannerPath = bannerParam.get(i);
            Banner banner = new Banner();
            if (!oldBanners.contains(bannerPath)) { //添加新轮播图
                banner.setPath(bannerPath);
                banner.setCreatedUser(operateUser);
                banner.setCreatedTime(operateTime);
                banner.setIsDeleted(DeleteStatus.UN_DELETED.getCode());
                banner.setSort(i + 1);
                bannerDao.insert(banner);
            } else { //修改原轮播图排序值
                banner.setSort(i + 1);
                banner.setModifiedUser(operateUser);
                banner.setModifiedTime(operateTime);
                bannerDao.update(banner, new EntityWrapper<Banner>().eq("is_deleted", DeleteStatus.UN_DELETED.getCode()).eq("path", bannerPath));
            }
        }
    }

    /**
     * 删除轮播图文件
     *
     * @param images
     */
    private void deleteImageFile(List<String> images) {
        for (String imagePath : images) {
            if (StringUtils.isNotEmpty(imagePath)) {
                if (imagePath.indexOf(FdfsFileUtil.PREFIX_IMAGE_URL) != -1) {
                    imagePath = FdfsFileUtil.cutFileURL(imagePath);
                }
                fdfsClientService.deleteFile(imagePath);
            }
        }
    }
}
