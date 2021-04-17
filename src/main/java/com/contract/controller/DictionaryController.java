package com.contract.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.contract.annotation.UserLoginToken;
import com.contract.annotation.UserRoleToken;
import com.contract.domain.Dictionary;
import com.contract.domain.DictionaryType;
import com.contract.domain.Product;
import com.contract.exception.AddException;
import com.contract.service.DictionaryService;
import com.contract.util.ExcelUtils;
import com.contract.util.PageBean;
import com.contract.util.Sheet;
import com.contract.util.UUIDUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dictionary")
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    @RequestMapping(value = "/getDictionaryTypeBySearch", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject getDictionaryTypeBySearch(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Integer currentPage = 1;
            String currentPageStr = paramsJson.getString("currentPage");
            if (currentPageStr != null && !"".equals(currentPageStr)) {
                currentPage = Integer.parseInt(currentPageStr);
            }
            Integer showCount = 10;
            String showCountStr = paramsJson.getString("showCount");
            if (showCountStr != null && !"".equals(showCountStr)) {
                showCount = Integer.parseInt(showCountStr);
            }
            PageBean<DictionaryType> pageData;
            pageData = dictionaryService.getAllDictionaryType(currentPage, showCount);
            result.put("object", pageData);
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }

        return result;
    }

    @RequestMapping(value = "/altherDictionaryType", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject altherDictionaryType(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            DictionaryType dictionaryType = JSONObject.parseObject(paramsJson.getString("dictionaryType"), DictionaryType.class);
            if (dictionaryType.getSubmitState().equals("Add")) {
                if (dictionaryType.getTypeId() == null || "".equals(dictionaryType.getTypeId())) {
                    dictionaryType.setTypeId(UUIDUtil.getUUID());
                }
                if (dictionaryService.getDictionaryTypeById(dictionaryType.getTypeId()) != null) {
                    throw new AddException("【字典类别】添加失败，设置的【ID】已存在");
                }
                DictionaryType dictionaryType1 = dictionaryService.getDictionaryTypeByName(dictionaryType);
                if (dictionaryType1 != null) {
                    throw new AddException("【字典类别】添加失败，设置的【类别名称】已存在");
                }
                dictionaryType.setTypeEdit(true);
                dictionaryService.addDictionaryType(dictionaryType);
                result.put("data", "【字典类别】添加成功");
                result.put("code", 200);
            } else if (dictionaryType.getSubmitState().equals("Upd")) {
                DictionaryType dictionaryTypeSel = dictionaryService.getDictionaryTypeById(dictionaryType.getTypeId());
                if (dictionaryTypeSel == null) {
                    throw new AddException("【字典类别】不存在");
                }
                if (dictionaryTypeSel.getTypeEdit() == false) {
                    throw new AddException("【字典类别】禁止修改");
                }
                DictionaryType dictionaryType2 = dictionaryService.getDictionaryTypeByName(dictionaryType);
                if (dictionaryType2 != null) {
                    throw new AddException("【字典类别】修改失败，设置的【类别名称】已存在");
                }
                dictionaryService.updDictionaryType(dictionaryType);
                result.put("data", "【字典类别】修改成功");
                result.put("code", 200);
            } else if (dictionaryType.getSubmitState().equals("Del")) {
                DictionaryType dictionaryTypeSel = dictionaryService.getDictionaryTypeById(dictionaryType.getTypeId());
                if (dictionaryTypeSel == null) {
                    throw new AddException("【字典类别】不存在");
                }
                if (dictionaryTypeSel.getTypeEdit() == false) {
                    throw new AddException("【字典类别】禁止删除");
                }
                dictionaryService.delDictionaryType(dictionaryType);
                result.put("data", "【字典类别】删除成功");
                result.put("code", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }


    @RequestMapping(value = "/getDictionaryBySearch", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject getDictionaryBySearch(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Dictionary dictionary = JSONObject.parseObject(paramsJson.getString("dictionary"), Dictionary.class);
            Integer currentPage = 1;
            String currentPageStr = paramsJson.getString("currentPage");
            if (currentPageStr != null && !"".equals(currentPageStr)) {
                currentPage = Integer.parseInt(currentPageStr);
            }
            Integer showCount = 10;
            String showCountStr = paramsJson.getString("showCount");
            if (showCountStr != null && !"".equals(showCountStr)) {
                showCount = Integer.parseInt(showCountStr);
            }
            PageBean<Dictionary> pageData;
            pageData = dictionaryService.getDictionaryBySearch(dictionary, currentPage, showCount);
            result.put("object", pageData);
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }

        return result;
    }

    @RequestMapping(value = "/altherDictionary", method = RequestMethod.POST)
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject altherDictionary(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            Dictionary dictionary = JSONObject.parseObject(paramsJson.getString("dictionary"), Dictionary.class);

            if (dictionary.getSubmitState().equals("Add")) {
                if (dictionary.getDictionaryId() == null || "".equals(dictionary.getDictionaryId())) {
                    dictionary.setDictionaryId(UUIDUtil.getUUID());
                }
                Dictionary dictionarySel = dictionaryService.getDictionaryById(dictionary.getDictionaryId());
                if (dictionarySel != null) {
                    throw new AddException("【字典项】添加失败，设置的【ID】已存在");
                }
                DictionaryType dictionaryTypeSel = dictionaryService.getDictionaryTypeById(dictionary.getDictionaryType());
                if (dictionaryTypeSel == null) {
                    throw new AddException("【字典项】添加失败，设置的【字典类别】不存在");
                }
                if(dictionaryTypeSel.getTypeEdit()==false){
                    throw new AddException("【字典项】禁止编辑");
                }
//                dictionarySel = dictionaryService.getDictionaryByName(dictionary);
//                if (dictionarySel != null) {
//                    throw new AddException("【字典项】添加失败，设置的【字典名称】已存在");
//                }

                dictionaryService.addDictionary(dictionary);
                result.put("data", "【字典项】添加成功");
                result.put("code", 200);
            } else if (dictionary.getSubmitState().equals("Upd")) {
                Dictionary dictionarySel = dictionaryService.getDictionaryById(dictionary.getDictionaryId());
                if (dictionarySel == null) {
                    throw new AddException("【字典项】修改失败，未找到要修改的【字典项ID】");
                }
                DictionaryType dictionaryTypeSel = dictionaryService.getDictionaryTypeById(dictionary.getDictionaryType());
                if (dictionaryTypeSel == null) {
                    throw new AddException("【字典项】修改失败，设置的【字典类别】不存在");
                }
                if(dictionaryTypeSel.getTypeEdit()==false){
                    throw new AddException("【字典项】禁止编辑");
                }
//                dictionarySel = dictionaryService.getDictionaryByName(dictionary);
//                if (dictionarySel != null) {
//                    throw new AddException("【字典项】修改失败，设置的【字典名称】已存在");
//                }
                dictionaryService.updDictionary(dictionary);
                result.put("data", "【字典项】修改成功");
                result.put("code", 200);
            } else if (dictionary.getSubmitState().equals("Del")) {
                Dictionary dictionarySel = dictionaryService.getDictionaryById(dictionary.getDictionaryId());
                if (dictionarySel == null) {
                    throw new AddException("【字典项】不存在");// 当前逻辑不可能出现
                }
                DictionaryType dictionaryTypeSel = dictionaryService.getDictionaryTypeById(dictionary.getDictionaryType());
                if(dictionaryTypeSel!=null){
                    if (dictionaryTypeSel.getTypeEdit() == false) {
                        throw new AddException("【字典项】禁止删除");
                    }
                }
                dictionaryService.delDictionary(dictionary);
                result.put("data", "【字典项】删除成功");
                result.put("code", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }

    //根据Dictionary表dictionaryType属性查询
    @RequestMapping(value = "/getDictionaryItems", method = RequestMethod.POST)
//    @UserLoginToken
    public JSONObject getDictionaryItems(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            String dictionaryType = paramsJson.getString("dictionaryType");
            List<Dictionary> dictionaryList;
            dictionaryList = dictionaryService.getDictionaryByType(dictionaryType);
            result.put("object", dictionaryList);
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);
        }
        return result;
    }
    //根据Dictionary表dictionaryType属性查询
    @RequestMapping(value = "/getDictionaryItemsByFather", method = RequestMethod.POST)
//    @UserLoginToken
    public JSONObject getDictionaryItemsByFather(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            String dictionaryType = paramsJson.getString("dictionaryType");
            String dictionaryFather = paramsJson.getString("dictionaryFather");
            List<Dictionary> dictionaryList;
            dictionaryList = dictionaryService.getDictionaryByTypeAndFather(dictionaryType,dictionaryFather);
            result.put("object", dictionaryList);
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);
        }
        return result;
    }

    //根据Dictionary表dictionaryType属性查询
    @RequestMapping(value = "/getDictionaryItemsTree", method = RequestMethod.POST)
    public JSONObject getDictionaryItemsTree(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));
            String dictionaryType = paramsJson.getString("dictionaryType");
            List<String> productTypeList = JSONArray.parseArray(paramsJson.getString("fatherList"), String.class);
            List<Dictionary> dictionaryList = dictionaryService.getDictionaryByTypeAndFatherToTree(dictionaryType,productTypeList);
            result.put("object", dictionaryList);
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);
        }
        return result;
    }

    @GetMapping("/exportDictionaryNullExcel")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public void exportDictionaryNullExcel(HttpServletResponse response) {

        // 导出操作
        List<Sheet> sheetList = new ArrayList<>();
        sheetList.add(new Sheet("字典类别","字典类别表",new ArrayList<DictionaryType>(), DictionaryType.class));
        sheetList.add(new Sheet("字典","字典表",new ArrayList<Dictionary>(), Dictionary.class));

        ExcelUtils.multSheetExport(sheetList, "字典数据导入模板.xlsx", response);
    }



    @GetMapping("/exportDictionaryExcel")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public void exportDictionaryExcel(HttpServletResponse response) {
        // 导出操作
        List<Sheet> sheetList = new ArrayList<>();
        sheetList.add(new Sheet("字典类别","字典类别表",dictionaryService.getDictionaryTypeExcel(), DictionaryType.class));
        sheetList.add(new Sheet("字典","字典表",dictionaryService.getDictionaryExcel(), Dictionary.class));
        ExcelUtils.multSheetExport(sheetList, "字典数据.xlsx", response);
    }

    @GetMapping("/exportDictionaryEditExcel")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public void exportDictionaryEditExcel(HttpServletResponse response) {
        // 导出操作
        List<Sheet> sheetList = new ArrayList<>();
        sheetList.add(new Sheet("字典类别","字典类别表",dictionaryService.getDictionaryTypeEditExcel(), DictionaryType.class));
        sheetList.add(new Sheet("字典","字典表",dictionaryService.getDictionaryEditExcel(), Dictionary.class));
        ExcelUtils.multSheetExport(sheetList, "字典可编辑数据.xlsx", response);
    }

    @PostMapping("/importDictionaryExcel")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject importDictionaryExcel(@RequestParam("file") MultipartFile file) {
        JSONObject result = new JSONObject();
        //导入操作
        try {
            List<DictionaryType> dictionaryTypeList = ExcelUtils.multSheetImport(file,1,1,0,DictionaryType.class);
            List<Dictionary> dictionaryList = ExcelUtils.multSheetImport(file,1,1,1,Dictionary.class);
            dictionaryService.importDictionaryExcel(dictionaryList,dictionaryTypeList);
            result.put("data","【字典类别、字典】Excel导入成功");
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("data",e.getMessage());
        }
        return result;
    }


}
