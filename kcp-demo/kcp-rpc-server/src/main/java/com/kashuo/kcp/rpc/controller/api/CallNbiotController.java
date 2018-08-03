package com.kashuo.kcp.rpc.controller.api;

import com.kashuo.kcp.utils.NbiotUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
/**
 * Created by dell-pc on 2018/8/3.
 */
@RestController
@RequestMapping("/nbiot")
public class CallNbiotController {
    private Logger logger = LoggerFactory.getLogger(CallNbiotController.class);
    private static String token = "abcdefghijkmlnopqrstuvwxyzlegend";//用户自定义token和OneNet第三方平台配置里的token一致
    private static String aeskey = "whBx2ZwAU5LOHVimPj1MPx56QRe3OsGGWRe4dr17crV";//aeskey和OneNet第三方平台配置里的token一致

    @RequestMapping(value = "/receive", method = RequestMethod.GET)
    @ResponseBody
    public String check(@RequestParam(value = "msg") String msg,
                        @RequestParam(value = "nonce") String nonce,
                        @RequestParam(value = "signature") String signature) throws UnsupportedEncodingException {

        logger.info("url&token check: msg:{} nonce{} signature:{}",msg,nonce,signature);
        if (NbiotUtils.checkToken(msg, nonce, signature, token)) {
            return msg;
        } else {
            return "error";
        }

    }

    @RequestMapping(value = "/receive",method = RequestMethod.POST)
    @ResponseBody
    public String receive(@RequestBody String body) throws NoSuchPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {

        logger.info("data receive:  body String --- " +body);
        /************************************************
         *  解析数据推送请求，非加密模式。
         *  如果是明文模式使用以下代码
         **************************************************/
        /*************明文模式  start****************/
        NbiotUtils.BodyObj obj = NbiotUtils.resolveBody(body, false);
        logger.info("data receive:  body Object --- " +obj);
        if (obj != null){
            boolean dataRight = NbiotUtils.checkSignature(obj, token);
            if (dataRight){
                logger.info("data receive: content" + obj.toString());
            }else {
                logger.info("data receive: signature error");
            }

        }else {
            logger.info("data receive: body empty error");
        }
        /*************明文模式  end****************/


        /********************************************************
         *  解析数据推送请求，加密模式
         *
         *  如果是加密模式使用以下代码
         ********************************************************/
        /*************加密模式  start****************/
//        Util.BodyObj obj1 = Util.resolveBody(body, true);
//        logger.info("data receive:  body Object--- " +obj1);
//        if (obj1 != null){
//            boolean dataRight1 = Util.checkSignature(obj1, token);
//            if (dataRight1){
//                String msg = Util.decryptMsg(obj1, aeskey);
//                logger.info("data receive: content" + msg);
//            }else {
//                logger.info("data receive:  signature error " );
//            }
//        }else {
//            logger.info("data receive: body empty error" );
//        }
        /*************加密模式  end****************/
        return "ok";
    }
}
