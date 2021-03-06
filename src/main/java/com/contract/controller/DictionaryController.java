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
                    throw new AddException("?????????????????????????????????????????????ID????????????");
                }
                DictionaryType dictionaryType1 = dictionaryService.getDictionaryTypeByName(dictionaryType);
                if (dictionaryType1 != null) {
                    throw new AddException("?????????????????????????????????????????????????????????????????????");
                }
                dictionaryType.setTypeEdit(true);
                dictionaryService.addDictionaryType(dictionaryType);
                result.put("data", "??????????????????????????????");
                result.put("code", 200);
            } else if (dictionaryType.getSubmitState().equals("Upd")) {
                DictionaryType dictionaryTypeSel = dictionaryService.getDictionaryTypeById(dictionaryType.getTypeId());
                if (dictionaryTypeSel == null) {
                    throw new AddException("???????????????????????????");
                }
                if (dictionaryTypeSel.getTypeEdit() == false) {
                    throw new AddException("??????????????????????????????");
                }
                DictionaryType dictionaryType2 = dictionaryService.getDictionaryTypeByName(dictionaryType);
                if (dictionaryType2 != null) {
                    throw new AddException("?????????????????????????????????????????????????????????????????????");
                }
                dictionaryService.updDictionaryType(dictionaryType);
                result.put("data", "??????????????????????????????");
                result.put("code", 200);
            } else if (dictionaryType.getSubmitState().equals("Del")) {
                DictionaryType dictionaryTypeSel = dictionaryService.getDictionaryTypeById(dictionaryType.getTypeId());
                if (dictionaryTypeSel == null) {
                    throw new AddException("???????????????????????????");
                }
                if (dictionaryTypeSel.getTypeEdit() == false) {
                    throw new AddException("??????????????????????????????");
                }
                dictionaryService.delDictionaryType(dictionaryType);
                result.put("data", "??????????????????????????????");
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
                    throw new AddException("??????????????????????????????????????????ID????????????");
                }
                DictionaryType dictionaryTypeSel = dictionaryService.getDictionaryTypeById(dictionary.getDictionaryType());
                if (dictionaryTypeSel == null) {
                    throw new AddException("??????????????????????????????????????????????????????????????????");
                }
                if(dictionaryTypeSel.getTypeEdit()==false){
                    throw new AddException("???????????????????????????");
                }
//                dictionarySel = dictionaryService.getDictionaryByName(dictionary);
//                if (dictionarySel != null) {
//                    throw new AddException("??????????????????????????????????????????????????????????????????");
//                }

                dictionaryService.addDictionary(dictionary);
                result.put("data", "???????????????????????????");
                result.put("code", 200);
            } else if (dictionary.getSubmitState().equals("Upd")) {
                Dictionary dictionarySel = dictionaryService.getDictionaryById(dictionary.getDictionaryId());
                if (dictionarySel == null) {
                    throw new AddException("???????????????????????????????????????????????????????????????ID???");
                }
                DictionaryType dictionaryTypeSel = dictionaryService.getDictionaryTypeById(dictionary.getDictionaryType());
                if (dictionaryTypeSel == null) {
                    throw new AddException("??????????????????????????????????????????????????????????????????");
                }
                if(dictionaryTypeSel.getTypeEdit()==false){
                    throw new AddException("???????????????????????????");
                }
//                dictionarySel = dictionaryService.getDictionaryByName(dictionary);
//                if (dictionarySel != null) {
//                    throw new AddException("??????????????????????????????????????????????????????????????????");
//                }
                dictionaryService.updDictionary(dictionary);
                result.put("data", "???????????????????????????");
                result.put("code", 200);
            } else if (dictionary.getSubmitState().equals("Del")) {
                Dictionary dictionarySel = dictionaryService.getDictionaryById(dictionary.getDictionaryId());
                if (dictionarySel == null) {
                    throw new AddException("????????????????????????");// ???????????????????????????
                }
                DictionaryType dictionaryTypeSel = dictionaryService.getDictionaryTypeById(dictionary.getDictionaryType());
                if(dictionaryTypeSel!=null){
                    if (dictionaryTypeSel.getTypeEdit() == false) {
                        throw new AddException("???????????????????????????");
                    }
                }
                dictionaryService.delDictionary(dictionary);
                result.put("data", "???????????????????????????");
                result.put("code", 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);

        }
        return result;
    }

    //??????Dictionary???dictionaryType????????????
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
    @RequestMapping(value = "/getAllDictionaryItems", method = RequestMethod.POST)
//    @UserLoginToken
    public JSONObject getAllDictionaryItems(@RequestBody String params) {
        JSONObject result = new JSONObject();
        try {
            JSONObject paramsJson = JSONObject.parseObject(JSONObject.parseObject(params).getString("params"));

            List<Dictionary> dictionaryList;
            dictionaryList = dictionaryService.getAllDictionary();
            result.put("object", dictionaryList);
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("data", e.getMessage());
            result.put("code", 500);
        }
        return result;
    }



    //??????Dictionary???dictionaryType????????????
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

    //??????Dictionary???dictionaryType????????????
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

        // ????????????
        List<Sheet> sheetList = new ArrayList<>();
        sheetList.add(new Sheet("????????????","???????????????",new ArrayList<DictionaryType>(), DictionaryType.class));
        sheetList.add(new Sheet("??????","?????????",new ArrayList<Dictionary>(), Dictionary.class));

        ExcelUtils.multSheetExport(sheetList, "????????????????????????.xlsx", response);
    }



    @GetMapping("/exportDictionaryExcel")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public void exportDictionaryExcel(HttpServletResponse response) {
        // ????????????
        List<Sheet> sheetList = new ArrayList<>();
        sheetList.add(new Sheet("????????????","???????????????",dictionaryService.getDictionaryTypeExcel(), DictionaryType.class));
        sheetList.add(new Sheet("??????","?????????",dictionaryService.getDictionaryExcel(), Dictionary.class));
        ExcelUtils.multSheetExport(sheetList, "????????????.xlsx", response);
    }

    @GetMapping("/exportDictionaryEditExcel")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public void exportDictionaryEditExcel(HttpServletResponse response) {
        // ????????????
        List<Sheet> sheetList = new ArrayList<>();
        sheetList.add(new Sheet("????????????","???????????????",dictionaryService.getDictionaryTypeEditExcel(), DictionaryType.class));
        sheetList.add(new Sheet("??????","?????????",dictionaryService.getDictionaryEditExcel(), Dictionary.class));
        ExcelUtils.multSheetExport(sheetList, "?????????????????????.xlsx", response);
    }

    @PostMapping("/importDictionaryExcel")
    @UserLoginToken
    @UserRoleToken(passRoleList = {"admin_role_master"})
    public JSONObject importDictionaryExcel(@RequestParam("file") MultipartFile file) {
        JSONObject result = new JSONObject();
        //????????????
        try {
            List<DictionaryType> dictionaryTypeList = ExcelUtils.multSheetImport(file,1,1,0,DictionaryType.class);
            List<Dictionary> dictionaryList = ExcelUtils.multSheetImport(file,1,1,1,Dictionary.class);
            dictionaryService.importDictionaryExcel(dictionaryList,dictionaryTypeList);
            result.put("data","???????????????????????????Excel????????????");
            result.put("code", 200);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("data",e.getMessage());
        }
        return result;
    }


}
