package net.zerotodev.api.producer.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import net.zerotodev.api.producer.config.KafkaConfiguration;
import net.zerotodev.api.producer.domain.User;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserController{
    //예제
    private final KafkaConfiguration kafkaConfiguration;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "buySell";

    @GetMapping("/publish")
    public String postMessage() throws JsonProcessingException {
        String abc = "{" +
                "  leaderId : 1," +
                "  before : {" +
                "    coins : [{name: btc, coinQuantity: 2.2, avgPrice : 10000 }, {name: eth, coinQuantity : 4.4 , avgPrice : 15000}]," +
                "    totalKRW : 37000" +
                "  }," +
                "  after : {" +
                "    coins : [{name: btc, coinQuantity: 3.0, avgPrice : 7500 }, {name: eth, coinQuantity : 4.4 , avgPrice : 15000}]," +
                "    totalKRW : 20000" +
                "  }" +
                "}";

        //System.out.println(rjson);
        //ObjectMapper objectMapper = new ObjectMapper();




//        JSONObject beforeCoin1 = new JSONObject();
//        beforeCoin1.put("name", "BTC");
//        beforeCoin1.put("coinQuantity", 2.2);
//        beforeCoin1.put("avgPrice", 10000);

        JSONObject beforeCoin2 = new JSONObject();
        beforeCoin2.put("name", "ETH");
        beforeCoin2.put("coinQuantity", 4.4);
        beforeCoin2.put("avgPrice", 15000);

        JSONArray beforeCoins = new JSONArray();
        //beforeCoins.add(beforeCoin1);
        beforeCoins.add(beforeCoin2);

        JSONObject before = new JSONObject();
        before.put("coins",beforeCoins);
        before.put("totalKRW",37000);

        ////////////////////////////////////

//        JSONObject afterCoin1 = new JSONObject();
//        afterCoin1.put("name", "BTC");
//        afterCoin1.put("coinQuantity", 3.0);
//        afterCoin1.put("avgPrice", 7500);

        JSONObject afterCoin2 = new JSONObject();
        afterCoin2.put("name", "ETH");
        afterCoin2.put("coinQuantity", 4.6);
        afterCoin2.put("avgPrice", 15000);

        JSONArray afterCoins = new JSONArray();
        //afterCoins.add(afterCoin1);
        afterCoins.add(afterCoin2);

        JSONObject after = new JSONObject();
        after.put("coins",afterCoins);
        after.put("totalKRW",24000);

        //////////////////////////////////////

        JSONObject rjson = new JSONObject();
        rjson.put("leaderId",2);
        rjson.put("before", before);
        rjson.put("after", after);

        System.out.println(rjson);
        System.out.println(rjson.toJSONString());
        //kafkaTemplate.send(TOPIC, rjson);
        kafkaConfiguration.kafkaTemplate().send(TOPIC, rjson);
        //System.out.println(0.1 + 0.2);
        return "Message Published Successfully";

    }


}
