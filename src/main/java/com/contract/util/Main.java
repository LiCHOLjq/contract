package com.contract.util;

import java.util.HashMap;
import java.util.Map;

public class Main {
    private int[] fun(int[] nums, int target){
        Map<Integer,Integer> map = new HashMap<>();
        for(int i = 0 ;i<nums.length;i++){
            Integer index = map.get(target - nums[i]);
            if(index==null){
                map.put(nums[i],i);
            }else{
                int[] result = new int[2];
                result[0] = index;
                result[1] = i;
                return result;
            }
        }
        int[] result = new int[2];
        result[0] = -1;
        result[1] = -1;
        return result;
    }
}
