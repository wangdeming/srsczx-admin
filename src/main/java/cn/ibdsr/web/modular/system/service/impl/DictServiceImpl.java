package cn.ibdsr.web.modular.system.service.impl;

import cn.ibdsr.web.common.constant.factory.MutiStrFactory;
import cn.ibdsr.web.common.exception.BizExceptionEnum;
import cn.ibdsr.web.common.exception.BussinessException;
import cn.ibdsr.web.common.persistence.dao.DictMapper;
import cn.ibdsr.web.common.persistence.model.Dict;
import cn.ibdsr.web.modular.system.dao.DictDao;
import cn.ibdsr.web.modular.system.service.IDictService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DictServiceImpl implements IDictService {

    @Resource
    DictDao dictDao;

    @Resource
    DictMapper dictMapper;

    @Override
    public void addDict(String dictName, String dictValues) {
        //判断有没有该字典
        List<Dict> dicts = dictMapper.selectList(new EntityWrapper<Dict>().eq("name", dictName).and().eq("pid", 0));
        if(dicts != null && dicts.size() > 0){
            throw new BussinessException(BizExceptionEnum.DICT_EXISTED);
        }

        //解析dictValues
        List<Map<String, String>> items = MutiStrFactory.parseKeyValue(dictValues);

        //添加字典
        Dict dict = new Dict();
        dict.setName(dictName);
        dict.setNum(0);
        dict.setPid(0L);
        this.dictMapper.insert(dict);

        //添加字典条目
        for (Map<String, String> item : items) {
            String num = item.get(MutiStrFactory.MUTI_STR_KEY);
            String name = item.get(MutiStrFactory.MUTI_STR_VALUE);
            Dict itemDict = new Dict();
            itemDict.setPid(dict.getId());
            itemDict.setName(name);
            try {
                itemDict.setNum(Integer.valueOf(num));
            }catch (NumberFormatException e){
                throw new BussinessException(BizExceptionEnum.DICT_MUST_BE_NUMBER);
            }
            this.dictMapper.insert(itemDict);
        }
    }

    @Override
    public void editDict(Long dictId, String dictName, String dicts) {
        //删除之前的字典
        this.delteDict(dictId);

        //重新添加新的字典
        this.addDict(dictName,dicts);
    }

    @Override
    public void delteDict(Long dictId) {
        //删除这个字典的子词典
        Wrapper<Dict> dictEntityWrapper = new EntityWrapper<>();
        dictEntityWrapper = dictEntityWrapper.eq("pid", dictId);
        dictMapper.delete(dictEntityWrapper);

        //删除这个词典
        dictMapper.deleteById(dictId);
    }
}
