package com.demo.scheme;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import com.demo.common.model.Scheme;
import com.google.gson.Gson;
import com.jfinal.aop.Before;
import com.jfinal.aop.Inject;
import com.jfinal.core.Controller;
import com.jfinal.json.Json;

import java.util.List;

import static cn.jpush.api.push.model.notification.PlatformNotification.ALERT;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * <p>
 * 所有 sql 与业务逻辑写在 Service 中，不要放在 Model 中，更不
 * 要放在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */
public class SchemeController extends Controller {

    @Inject
    SchemeService service;

    public void index() {
        setAttr("schemePage", service.paginate(getParaToInt(0, 1), 10));
        render("scheme.html");
    }

    public void getList(){
        List<Scheme> schemes = service.getList();
        renderJson(schemes);
    }

    public void add() {
    }

    /**
     * save 与 update 的业务逻辑在实际应用中也应该放在 serivce 之中，
     * 并要对数据进正确性进行验证，在此仅为了偷懒
     */
    @Before(SchemeValidator.class)
    public void save() {
        JPushClient jpushClient = new JPushClient("553d1ab3674cefbaa483cc34", "0c76fdebe1504dd8e37f1cca", null, ClientConfig.getInstance());
        Scheme scheme = getBean(Scheme.class);
        scheme.se

        String jsonMsg = Json.getJson().toJson(scheme);
        System.out.print("---"+ jsonMsg);
        try {
            PushResult result = jpushClient.sendMessageAll(jsonMsg);
            System.out.print("推送状态:"+ result);
        } catch (APIConnectionException | APIRequestException e) {
            System.out.print("---"+ e.getLocalizedMessage());
            // Connection error, should retry later
        }
        scheme.save();
        redirect("/scheme");
    }

    public void edit() {
        setAttr("scheme", service.findById(getParaToInt()));
    }

    /**
     * save 与 update 的业务逻辑在实际应用中也应该放在 serivce 之中，
     * 并要对数据进正确性进行验证，在此仅为了偷懒
     */
    @Before(SchemeValidator.class)
    public void update() {
        getBean(Scheme.class).update();
        redirect("/scheme");
    }

    public void delete() {
        service.deleteById(getParaToInt());
        redirect("/scheme");
    }

}


