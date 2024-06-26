package com.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.QuexiangongzuoEntity;
import com.entity.view.QuexiangongzuoView;

import com.service.QuexiangongzuoService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MD5Util;
import com.utils.MPUtil;
import com.utils.CommonUtil;

/**
 * 缺陷工作
 * 后端接口
 * @author 
 * @email 
 * @date 2022-02-14 10:43:33
 */
@RestController
@RequestMapping("/quexiangongzuo")
public class QuexiangongzuoController {
    @Autowired
    private QuexiangongzuoService quexiangongzuoService;



    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,QuexiangongzuoEntity quexiangongzuo, 
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date gengxinriqistart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date gengxinriqiend,
		HttpServletRequest request){

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			quexiangongzuo.setYonghuming((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<QuexiangongzuoEntity> ew = new EntityWrapper<QuexiangongzuoEntity>();
                if(gengxinriqistart!=null) ew.ge("gengxinriqi", gengxinriqistart);
                if(gengxinriqiend!=null) ew.le("gengxinriqi", gengxinriqiend);
		PageUtils page = quexiangongzuoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, quexiangongzuo), params), params));
        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,QuexiangongzuoEntity quexiangongzuo, 
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date gengxinriqistart,
                @RequestParam(required = false) @DateTimeFormat(pattern="yyyy-MM-dd") Date gengxinriqiend,
		HttpServletRequest request){

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			quexiangongzuo.setYonghuming((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<QuexiangongzuoEntity> ew = new EntityWrapper<QuexiangongzuoEntity>();
                if(gengxinriqistart!=null) ew.ge("gengxinriqi", gengxinriqistart);
                if(gengxinriqiend!=null) ew.le("gengxinriqi", gengxinriqiend);
		PageUtils page = quexiangongzuoService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, quexiangongzuo), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( QuexiangongzuoEntity quexiangongzuo){
       	EntityWrapper<QuexiangongzuoEntity> ew = new EntityWrapper<QuexiangongzuoEntity>();
      	ew.allEq(MPUtil.allEQMapPre( quexiangongzuo, "quexiangongzuo")); 
        return R.ok().put("data", quexiangongzuoService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(QuexiangongzuoEntity quexiangongzuo){
        EntityWrapper< QuexiangongzuoEntity> ew = new EntityWrapper< QuexiangongzuoEntity>();
 		ew.allEq(MPUtil.allEQMapPre( quexiangongzuo, "quexiangongzuo")); 
		QuexiangongzuoView quexiangongzuoView =  quexiangongzuoService.selectView(ew);
		return R.ok("查询缺陷工作成功").put("data", quexiangongzuoView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        QuexiangongzuoEntity quexiangongzuo = quexiangongzuoService.selectById(id);
        return R.ok().put("data", quexiangongzuo);
    }

    /**
     * 前端详情
     */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        QuexiangongzuoEntity quexiangongzuo = quexiangongzuoService.selectById(id);
        return R.ok().put("data", quexiangongzuo);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody QuexiangongzuoEntity quexiangongzuo, HttpServletRequest request){
    	quexiangongzuo.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(quexiangongzuo);

        quexiangongzuoService.insert(quexiangongzuo);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody QuexiangongzuoEntity quexiangongzuo, HttpServletRequest request){
    	quexiangongzuo.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(quexiangongzuo);
    	quexiangongzuo.setUserid((Long)request.getSession().getAttribute("userId"));

        quexiangongzuoService.insert(quexiangongzuo);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody QuexiangongzuoEntity quexiangongzuo, HttpServletRequest request){
        //ValidatorUtils.validateEntity(quexiangongzuo);
        quexiangongzuoService.updateById(quexiangongzuo);//全部更新
        return R.ok();
    }
    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        quexiangongzuoService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
    /**
     * 提醒接口
     */
	@RequestMapping("/remind/{columnName}/{type}")
	public R remindCount(@PathVariable("columnName") String columnName, HttpServletRequest request, 
						 @PathVariable("type") String type,@RequestParam Map<String, Object> map) {
		map.put("column", columnName);
		map.put("type", type);
		
		if(type.equals("2")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			Date remindStartDate = null;
			Date remindEndDate = null;
			if(map.get("remindstart")!=null) {
				Integer remindStart = Integer.parseInt(map.get("remindstart").toString());
				c.setTime(new Date()); 
				c.add(Calendar.DAY_OF_MONTH,remindStart);
				remindStartDate = c.getTime();
				map.put("remindstart", sdf.format(remindStartDate));
			}
			if(map.get("remindend")!=null) {
				Integer remindEnd = Integer.parseInt(map.get("remindend").toString());
				c.setTime(new Date());
				c.add(Calendar.DAY_OF_MONTH,remindEnd);
				remindEndDate = c.getTime();
				map.put("remindend", sdf.format(remindEndDate));
			}
		}
		
		Wrapper<QuexiangongzuoEntity> wrapper = new EntityWrapper<QuexiangongzuoEntity>();
		if(map.get("remindstart")!=null) {
			wrapper.ge(columnName, map.get("remindstart"));
		}
		if(map.get("remindend")!=null) {
			wrapper.le(columnName, map.get("remindend"));
		}

		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			wrapper.eq("yonghuming", (String)request.getSession().getAttribute("username"));
		}

		int count = quexiangongzuoService.selectCount(wrapper);
		return R.ok().put("count", count);
	}
	







    /**
     * （按值统计）
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}")
    public R value(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("yColumn", yColumnName);
        EntityWrapper<QuexiangongzuoEntity> ew = new EntityWrapper<QuexiangongzuoEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("yonghu")) {
            ew.eq("yonghuming", (String)request.getSession().getAttribute("username"));
        }
        List<Map<String, Object>> result = quexiangongzuoService.selectValue(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }

    /**
     * （按值统计）时间统计类型
     */
    @RequestMapping("/value/{xColumnName}/{yColumnName}/{timeStatType}")
    public R valueDay(@PathVariable("yColumnName") String yColumnName, @PathVariable("xColumnName") String xColumnName, @PathVariable("timeStatType") String timeStatType,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("xColumn", xColumnName);
        params.put("yColumn", yColumnName);
        params.put("timeStatType", timeStatType);
        EntityWrapper<QuexiangongzuoEntity> ew = new EntityWrapper<QuexiangongzuoEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("yonghu")) {
            ew.eq("yonghuming", (String)request.getSession().getAttribute("username"));
        }
        List<Map<String, Object>> result = quexiangongzuoService.selectTimeStatValue(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }

    /**
     * 分组统计
     */
    @RequestMapping("/group/{columnName}")
    public R group(@PathVariable("columnName") String columnName,HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("column", columnName);
        EntityWrapper<QuexiangongzuoEntity> ew = new EntityWrapper<QuexiangongzuoEntity>();
        String tableName = request.getSession().getAttribute("tableName").toString();
        if(tableName.equals("yonghu")) {
            ew.eq("yonghuming", (String)request.getSession().getAttribute("username"));
        }
        List<Map<String, Object>> result = quexiangongzuoService.selectGroup(params, ew);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Map<String, Object> m : result) {
            for(String k : m.keySet()) {
                if(m.get(k) instanceof Date) {
                    m.put(k, sdf.format((Date)m.get(k)));
                }
            }
        }
        return R.ok().put("data", result);
    }
}
