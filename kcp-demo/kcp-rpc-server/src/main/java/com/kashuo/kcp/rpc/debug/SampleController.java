package com.kashuo.kcp.rpc.debug;

/**
 * Created by dell-pc on 2017/8/31.
 */

import com.alibaba.fastjson.JSONObject;
import com.kashuo.kcp.constant.AppConstant;
import com.kashuo.kcp.core.SysDictionaryService;
import com.kashuo.kcp.dao.AmmeterPositionMapper;
import com.kashuo.kcp.domain.AmmeterPosition;
import com.kashuo.kcp.manage.DeviceConfigService;
import com.kashuo.kcp.redis.RedisService;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
//@EnableAutoConfiguration
public class SampleController {

    private final static Logger logger = LoggerFactory.getLogger(SampleController.class);

    @Autowired
    private SysDictionaryService sysDictionaryService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private AmmeterPositionMapper ammeterPositionMapper;
    @Autowired
    private DeviceConfigService deviceConfigService;
//
    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
//        return "Hello World";
    }

    @GetMapping(value = "/hello")
    @ResponseBody
    public String home2(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
//        return mav;
        return "Hello World";
    }
    @GetMapping(value = "/hello2")
    @ResponseBody
    public String getDictionary(){
        logger.info("============================");
        return JSONObject.toJSON(sysDictionaryService.getDictionartLists()).toString();
    }
    @GetMapping(value = "redisTest")
    @ResponseBody
    public String getIotNb(){
        AmmeterPosition p = ammeterPositionMapper.selectByDeviceId("504902032");
//                boolean messageFlag = MessageUtils.sendMessage(p.getImei(),"未上电",p.getContactInfo());
        deviceConfigService.sendMsgInfoBySMS(p,"未上电",p.getDeviceType());
        return JSONObject.toJSONString(redisService.get(AppConstant.REDIS_KEY_AUTH_IOM_WELLCOVER)) ;
    }

    public static void main(String[] args) {

        int score[] = {67, 69, 75, 87, 89, 90, 99, 100};
                for (int i = 0; i < score.length -1; i++){    //最多做n-1趟排序
                         for(int j = 0 ;j < score.length - i - 1; j++){    //对当前无序区间score[0......length-i-1]进行排序(j的范围很关键，这个范围是在逐步缩小的)
                                if(score[j] < score[j + 1]){    //把小的值交换到后面
                                        int temp = score[j];
                                        score[j] = score[j + 1];
                                    score[j + 1] = temp;
                                    }
                           }
                       System.out.print("第" + (i + 1) + "次排序结果：");
                      for(int a = 0; a < score.length; a++){
                               System.out.print(score[a] + "\t");
                           }
                        System.out.println("");
                   }
                   System.out.print("最终排序结果：");
                  for(int a = 0; a < score.length; a++){
                          System.out.print(score[a] + "\t");
                }
    }
}
