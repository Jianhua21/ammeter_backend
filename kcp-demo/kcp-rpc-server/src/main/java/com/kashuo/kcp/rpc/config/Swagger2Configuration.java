package com.kashuo.kcp.rpc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Sets.newHashSet;

/**
 * create by zhuenjun on 2017/09/12 02:26
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    @Bean
    Docket UiApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .forCodeGeneration(false)
                .groupName("front-ui-api")
                .useDefaultResponseMessages(false)
                .produces(newHashSet("application/json"))
                .genericModelSubstitutes(DeferredResult.class)
                .select().apis(RequestHandlerSelectors.basePackage("com.kashuo.kcp")).build()
                .globalOperationParameters(globalOperationParameters(true))
                .globalResponseMessage(RequestMethod.GET, globalResponseMessage())
                .globalResponseMessage(RequestMethod.POST, globalResponseMessage())
                .apiInfo(UiApiInfo());
    }

    private ApiInfo UiApiInfo() {
        return new ApiInfo(
                "智能电表平台后台API文档 - (后端UI篇)",
                "超越传奇的地方：First Blood",
                "v1.0", null, new Contact("", "", ""), null, null);
    }

    /**
     * 全局请求头参数
     */
    private List<Parameter> globalOperationParameters(boolean needUserLogin) {
        List<Parameter> parameters = new ArrayList<>();
        if (needUserLogin)
            parameters.add(new ParameterBuilder().name("X-USER-TOKEN-ID").description("用户登录令牌-机构 Id").parameterType("header").modelRef(new ModelRef("string")).required(false).build());
        return parameters;
    }
    /**
     * 请求返回的HTTP全局状态码说明(非业务状态码)
     */
    private List<ResponseMessage> globalResponseMessage() {
        List<ResponseMessage> messageList = new ArrayList<>();
        messageList.add(new ResponseMessageBuilder().code(400).message("请求无效").build());
        messageList.add(new ResponseMessageBuilder().code(403).message("资源不可用").build());
        messageList.add(new ResponseMessageBuilder().code(404).message("服务器未能找到相关信息").build());
        messageList.add(new ResponseMessageBuilder().code(500).message("系统内部错误").responseModel(new ModelRef("Error")).build());
        return messageList;
    }


    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration(null, "none", "alpha", "model", UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, false);
    }

}
