package cn.ibdsr.web.core.template.engine;

import cn.ibdsr.core.util.ToolUtil;
import cn.ibdsr.web.core.template.engine.base.GunsTemplateEngine;
import cn.ibdsr.web.core.util.FileUtil;

/**
 * 通用的模板生成引擎
 *
 * @author fengshuonan
 * @date 2017-05-09 20:32
 */
public class SimpleTemplateEngine extends GunsTemplateEngine {

    @Override
    protected void generatePageEditHtml() {
        String path = ToolUtil.format(super.getContextConfig().getProjectPath() + getPageConfig().getPageEditPathTemplate(),
                super.getContextConfig().getBizEnName(),super.getContextConfig().getBizEnName());
        Boolean isGenerate = super.getContextConfig().getFileOverride()?true:!FileUtil.isFileExist(path);
        if (isGenerate) {
            generateFile("gunsTemplate/page_edit.html.btl", path);
            System.out.println("生成编辑页面成功!");
        } else {
            System.out.println("编辑页面已经存在！");
        }

    }

    @Override
    protected void generatePageAddHtml() {
        String path = ToolUtil.format(super.getContextConfig().getProjectPath() + getPageConfig().getPageAddPathTemplate(),
                super.getContextConfig().getBizEnName(),super.getContextConfig().getBizEnName());
        Boolean isGenerate = super.getContextConfig().getFileOverride()?true:!FileUtil.isFileExist(path);
        if (isGenerate) {
            generateFile("gunsTemplate/page_add.html.btl", path);
            System.out.println("生成添加页面成功!");
        } else {
            System.out.println("添加页面已经存在！");
        }
    }

    @Override
    protected void generatePageInfoJs() {
        String path = ToolUtil.format(super.getContextConfig().getProjectPath() + getPageConfig().getPageInfoJsPathTemplate(),
                super.getContextConfig().getBizEnName(),super.getContextConfig().getBizEnName());
        Boolean isGenerate = super.getContextConfig().getFileOverride()?true:!FileUtil.isFileExist(path);
        if (isGenerate) {
            generateFile("gunsTemplate/page_info.js.btl", path);
            System.out.println("生成页面详情js成功!");
        } else {
            System.out.println("页面详情js已经存在！");
        }

    }

    @Override
    protected void generatePageJs() {
        String path = ToolUtil.format(super.getContextConfig().getProjectPath() + getPageConfig().getPageJsPathTemplate(),
                super.getContextConfig().getBizEnName(),super.getContextConfig().getBizEnName());
        Boolean isGenerate = super.getContextConfig().getFileOverride()?true:!FileUtil.isFileExist(path);
        if (isGenerate) {
            generateFile("gunsTemplate/page.js.btl", path);
            System.out.println("生成页面js成功!");
        } else {
            System.out.println("页面js已经存在！");
        }

    }

    @Override
    protected void generatePageHtml() {
        String path = ToolUtil.format(super.getContextConfig().getProjectPath() + getPageConfig().getPagePathTemplate(),
                super.getContextConfig().getBizEnName(),super.getContextConfig().getBizEnName());
        Boolean isGenerate = super.getContextConfig().getFileOverride()?true:!FileUtil.isFileExist(path);
        if (isGenerate) {
            generateFile("gunsTemplate/page.html.btl", path);
            System.out.println("生成页面成功!");
        } else {
            System.out.println("页面已经存在！");
        }

    }

    @Override
    protected void generateController() {

        String controllerPath = ToolUtil.format(super.getContextConfig().getProjectPath() + super.getControllerConfig().getControllerPathTemplate(),
                ToolUtil.firstLetterToUpper(super.getContextConfig().getBizEnName()));
        Boolean isGenerate = super.getContextConfig().getFileOverride()?true:!FileUtil.isFileExist(controllerPath);
        if (isGenerate) {
            generateFile("gunsTemplate/Controller.java.btl", controllerPath);
            System.out.println("生成控制器成功!");
        } else {
            System.out.println("控制器已经存在！");
        }
    }

    @Override
    protected void generateDao() {
        String daoPath = ToolUtil.format(super.getContextConfig().getProjectPath() + super.getDaoConfig().getDaoPathTemplate(),
                ToolUtil.firstLetterToUpper(super.getContextConfig().getBizEnName()));
        Boolean isGenerate = super.getContextConfig().getFileOverride()?true:!FileUtil.isFileExist(daoPath);
        if (isGenerate) {
            generateFile("gunsTemplate/Dao.java.btl", daoPath);
            System.out.println("生成Dao成功!");
        } else {
            System.out.println("Dao已经存在！");
        }


        String mappingPath = ToolUtil.format(super.getContextConfig().getProjectPath() + super.getDaoConfig().getXmlPathTemplate(),
                ToolUtil.firstLetterToUpper(super.getContextConfig().getBizEnName()));
        Boolean isGenerate2 = super.getContextConfig().getFileOverride()?true:!FileUtil.isFileExist(mappingPath);
        if (isGenerate2) {
            generateFile("gunsTemplate/Mapping.xml.btl", mappingPath);
            System.out.println("生成Dao Mapping xml成功!");
        } else {
            System.out.println("Dao Mapping xml已经存在！");
        }

    }

    @Override
    protected void generateService() {
        String servicePath = ToolUtil.format(super.getContextConfig().getProjectPath() + super.getServiceConfig().getServicePathTemplate(),
                ToolUtil.firstLetterToUpper(super.getContextConfig().getBizEnName()));
        Boolean isGenerate = super.getContextConfig().getFileOverride()?true:!FileUtil.isFileExist(servicePath);
        if (isGenerate) {
            generateFile("gunsTemplate/Service.java.btl", servicePath);
            System.out.println("生成Service成功!");
        } else {
            System.out.println("Service已经存在！");
        }


        String serviceImplPath = ToolUtil.format(super.getContextConfig().getProjectPath() + super.getServiceConfig().getServiceImplPathTemplate(),
                ToolUtil.firstLetterToUpper(super.getContextConfig().getBizEnName()));
        Boolean isGenerate2 = super.getContextConfig().getFileOverride()?true:!FileUtil.isFileExist(serviceImplPath);
        if (isGenerate2) {
            generateFile("gunsTemplate/ServiceImpl.java.btl", serviceImplPath);
            System.out.println("生成ServiceImpl成功!");
        } else {
            System.out.println("ServiceImpl已经存在！");
        }

    }
}
